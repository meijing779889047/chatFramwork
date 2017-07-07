package com.hn.chat.widget.boxing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.CameraPickerHelper;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.hn.chat.R;
import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;
import com.hn.chat.util.LogUtils;
import com.hn.chat.util.Utils;
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
     private List<PickImageHelperObserverListener> albunmList = new ArrayList<>();
     private List<PickImageHelperObserverListener> cameraList = new ArrayList<>();

    //是否进行裁剪
    private boolean  isCorp=false;//默认不进行裁剪
    private  File    outputFile;//拍照文件
    private  File    inputFile;//裁剪文件
    private float mWidth=160.0f;
    private float mHeight=160.0f;

    private static PickImageHelperClient  mPickImageHelperClient;
    private CameraPickerHelper mCameraPicker;

    private PickImageHelperClient(){
    }




    private static class PickImageHelperViewHolder{
       private static PickImageHelperClient instance=new PickImageHelperClient();
   }
    public  static PickImageHelperClient newInstance(){
        mPickImageHelperClient=PickImageHelperViewHolder.instance;
        return mPickImageHelperClient;
    }

    /********************************相册处理***************************************************

    /**
     * 打开相册
     * @param context
     */
    public PickImageHelperClient OpenAlbum(Activity  context) {
        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withMediaPlaceHolderRes(R.drawable.ic_boxing_camera_white);
        Boxing.of(singleImgConfig).withIntent(context, BoxingActivity.class).start(context, PHOTO_REQUEST_CODE);
        return  mPickImageHelperClient;
    }

    /**
     * 处理相册数据
     * @param activity
     * @param data
     */
    public void dealAlbumDataFromActivityResult(final Handler handler, final Activity activity, final Intent data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<BaseMedia> medias = Boxing.getResult(data);
                final List<BaseMedia> imageMedias = new ArrayList<>(1);
                BaseMedia baseMedia = medias.get(0);
                if (!(baseMedia instanceof ImageMedia)) {
                    LogUtils.e(TAG,"没有获取到图片");
                    for (PickImageHelperObserverListener observerListener : albunmList){
                        observerListener.onFail(activity.getString(R.string.not_find_photo));
                    }
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
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (PickImageHelperObserverListener observerListener : albunmList){
                                observerListener.onSuccess(MessageObject.rightTyoe, MsgType.PICTURE,path, Utils.getNowDate());
                            }
                        }
                    },500);
                }
            }
        }).start();
    }


   /******************************拍照处理****************************************************************

    /**
     * 请求获取carmear权限
     * @param context
     */
    public PickImageHelperClient requestCameraPermission(Activity  context) {
        MPermission.with(context)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(Manifest.permission.CAMERA)
                .request();
        return mPickImageHelperClient;
    }

    /**
     * 打开摄像头拍照
     * @param activity
     * @param savedInstanceState
     */
    public void openCamera(final Activity activity, Bundle savedInstanceState, final Handler handler) {
        if(mCameraPicker==null) {
            mCameraPicker = new CameraPickerHelper(savedInstanceState);
            mCameraPicker.setPickCallback(new CameraPickerHelper.Callback() {
                @Override
                public void onFinish(@NonNull CameraPickerHelper helper) {
                    File file = new File(helper.getSourceFilePath());
                    if (!file.exists()) {
                        onError(helper);
                        return;
                    }
                    final ImageMedia cameraMedia = new ImageMedia(file);
                    if (handler != null) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (PickImageHelperObserverListener observerListener : cameraList) {
                                    observerListener.onSuccess(MessageObject.rightTyoe, MsgType.PICTURE, cameraMedia.getPath(), Utils.getNowDate());
                                }
                            }
                        }, 500);
                        LogUtils.i(TAG, "图片路径:" + cameraMedia.getPath());
                    }
                }

                @Override
                public void onError(@NonNull CameraPickerHelper helper) {
                    LogUtils.i(TAG, "拍照失败");
                    for (PickImageHelperObserverListener observerListener : cameraList){
                        observerListener.onFail(activity.getString(R.string.not_find_photo));
                    }
                }
            });
        }
        mCameraPicker.startCamera(activity,null,null);
        LogUtils.i(TAG,"初始化mCameraPicker："+mCameraPicker);

    }

    /**
     * 拍照请求返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data,Activity activity,Handler handler) {
        if(PHOTO_REQUEST_CODE==requestCode){//相册回调
            dealAlbumDataFromActivityResult(handler,activity,data);
        }else if(CAMEAR_REQUEST_CODE==requestCode){//拍照回调
            if (mCameraPicker != null) {
                mCameraPicker.onActivityResult(requestCode, resultCode);
            }
        }
    }



    @Override
    public void requestCallBack(PickImageHelperObserverListener observerListener,OperationType type) {
            if(observerListener==null)  return;
            if(OperationType.Albunm==type){
                if(!albunmList.contains(observerListener)) {
                    albunmList.add(observerListener);
                 }
            }else if(OperationType.Camera==type) {
                if(!cameraList.contains(observerListener)) {
                    cameraList.add(observerListener);
                }
            }
        LogUtils.i(TAG,"观察者列表----》albunmList："+albunmList.size()+"--->cameraList:"+cameraList.size());
    }

    @Override
    public void canclePickImageHelperListener(PickImageHelperObserverListener observerListener,OperationType type) {
        if(observerListener==null)  return;
        if(OperationType.Albunm==type){
            if(albunmList.contains(observerListener)) {
                albunmList.remove(observerListener);
            }
        }else if(OperationType.Camera==type) {
            if(cameraList.contains(observerListener)) {
                cameraList.add(observerListener);
            }
        }
    }


    public  enum  OperationType{
        Albunm,
        Camera,
    }

}
