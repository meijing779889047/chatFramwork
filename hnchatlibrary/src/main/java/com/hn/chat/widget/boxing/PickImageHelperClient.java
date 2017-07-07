package com.hn.chat.widget.boxing;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.hn.chat.R;
import com.hn.chat.util.BitmapDecoder;
import com.hn.chat.util.LogUtils;
import com.hn.chat.util.StringUtil;
import com.hn.chat.util.permission.MPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hn.chat.delegate.BaseChatDelegate.BASIC_PERMISSION_REQUEST_CODE;
import static com.hn.chat.delegate.BaseChatDelegate.CAMEAR_REQUEST_CODE;
import static com.hn.chat.delegate.BaseChatDelegate.PHOTO_REQUEST_CODE;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：打开相册、拍照功能辅助类
 * 创建人：Administrator
 * 创建时间：2017/7/6 17:03
 * 修改人：Administrator
 * 修改时间：2017/7/6 17:03
 * 修改备注：
 * Version:  1.0.0
 */
public class PickImageHelperClient implements   PickImageHelperSubjectListener{

      private String TAG="PickImageHelper";
     //观察者接口集合
     private List<PickImageHelperObserverListener> list = new ArrayList<>();

    //是否进行裁剪
    private boolean  isCorp=true;

    private PickImageHelperClient(){
    }

    private static class PickImageHelperViewHolder{
       private static PickImageHelperClient instance=new PickImageHelperClient();
   }
    public  static PickImageHelperClient newInstance(){
        return PickImageHelperViewHolder.instance;
    }



    /**
     * 打开相册
     * @param context
     */
    public PickImageHelperClient OpenAlbum(Activity  context) {
        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withMediaPlaceHolderRes(R.drawable.ic_boxing_camera_white);
        Boxing.of(singleImgConfig).withIntent(context, BoxingActivity.class).start(context, PHOTO_REQUEST_CODE);
        return  newInstance();
    }

    /**
     * 请求获取carmear权限
     * @param context
     */
    public PickImageHelperClient requestCameraPermission(Activity  context) {
        MPermission.with(context)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(Manifest.permission.CAMERA)
                .request();
        return newInstance();
    }
    /**
     * 打开摄像头
     * @param context
     * @param isCrop
     */
    public void pickFromCamera(Activity context, boolean isCrop) {
        try {
            this.isCorp=isCrop;
            String outPath =StringUtil.tempFile();
            LogUtils.i(TAG,"文件路径:"+outPath);
            if (TextUtils.isEmpty(outPath)) {
                Toast.makeText(context, R.string.sdcard_not_enough_error, Toast.LENGTH_LONG).show();
                return;
            }
            File outputFile = new File(outPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
            context.startActivityForResult(intent, CAMEAR_REQUEST_CODE);
        }catch (Exception e) {
            Toast.makeText(context, R.string.sdcard_not_enough_head_error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 处理从拍照数据
     * @param context
     * @param data
     */
    public void dealCameraDataFromActivityResult(Context context, Intent data) {
        try {
            String photoPath = pathFromResult(data,context);
            LogUtils.i(TAG,"获取到拍照的图片1:"+photoPath);
            isCorp=false;
            if(isCorp) {
//                crop(potoPath,context);
            }else{//不裁剪直接压缩
                String  path=  BitmapDecoder.compressBitmap(photoPath,context);
                LogUtils.i(TAG,"获取到拍照的图片2:"+path);
                for (PickImageHelperObserverListener observerListener : list){
                       observerListener.onSuccess(path);

                }
            }

        } catch (Exception e) {
            Toast.makeText(context, R.string.picker_image_error, Toast.LENGTH_LONG).show();
        }
    }

    private String pathFromResult(Intent data, Context context) {
        String outPath =StringUtil.tempFile();
        if (data == null || data.getData() == null) {
            return outPath;
        }

        Uri uri = data.getData();
        Cursor cursor = context.getContentResolver()
                .query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor == null) {
            // miui 2.3 有可能为null
            return uri.getPath();
        } else {
            if (uri.toString().contains("content://com.android.providers.media.documents/document/image")) { // htc 某些手机
                // 获取图片地址
                String _id = null;
                String uridecode = uri.decode(uri.toString());
                int id_index = uridecode.lastIndexOf(":");
                _id = uridecode.substring(id_index + 1);
                Cursor mcursor =context. getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, " _id = " + _id,
                        null, null);
                mcursor.moveToFirst();
                int column_index = mcursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                outPath = mcursor.getString(column_index);
                if (!mcursor.isClosed()) {
                    mcursor.close();
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return outPath;

            } else {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                outPath = cursor.getString(column_index);
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return outPath;
            }
        }
    }

    /**
     * 处理相册数据
     * @param activity
     * @param data
     */
    public void dealAlbumDataFromActivityResult(final Activity activity, final Intent data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<BaseMedia> medias = Boxing.getResult(data);
                final List<BaseMedia> imageMedias = new ArrayList<>(1);
                BaseMedia baseMedia = medias.get(0);
                if (!(baseMedia instanceof ImageMedia)) {
                    LogUtils.e(TAG,"没有获取到图片");
                    return;
                }
                final ImageMedia imageMedia = (ImageMedia) baseMedia;
                // the compress task may need time
                if (imageMedia.compress(new ImageCompressor(activity))) {
                    imageMedia.removeExif();
                    imageMedias.add(imageMedia);
                    final String path = medias.get(0).getPath();
                    long size = medias.get(0).getSize();
                    LogUtils.i(TAG, "图片的路径:" + path + "--->" + size);
//                    if(listener!=null){
//                        uiHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                listener.sendMsg(MessageObject.rightTyoe, MsgType.PICTURE,"file://"+path, Utils.getNowDate());
//                            }
//                        },500);
//
//                    }
                         for (PickImageHelperObserverListener observerListener : list){
                                 observerListener.onSuccess(path);

                         }
                }
            }
        }).start();
    }



    @Override
    public void add(PickImageHelperObserverListener observerListener) {
            list.add(observerListener);
    }

    @Override
    public void notifyObserver(String content) {

    }

    @Override
    public void remove(PickImageHelperObserverListener observerListener) {
           if (list.contains(observerListener)){
                   list.remove(observerListener);
            }
    }

}
