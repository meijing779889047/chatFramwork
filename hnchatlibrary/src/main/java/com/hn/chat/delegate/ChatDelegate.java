package com.hn.chat.delegate;

import android.os.Bundle;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing_impl.ui.BoxingViewActivity;
import com.hn.chat.adapter.ChatMessageListAdapter;
import com.hn.chat.impl.ChatRoomListener;
import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;
import com.hn.chat.model.MsgModel;
import com.hn.chat.util.LogUtils;
import com.hn.chat.widget.boxing.PickImageHelperClient;
import com.hn.chat.widget.boxing.PickImageHelperObserverListener;
import com.hn.chat.widget.more.BaseAction;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/6/28 8:52
 * 修改人：Administrator
 * 修改时间：2017/6/28 8:52
 * 修改备注：
 * Version:  1.0.0
 */

public class ChatDelegate extends BaseChatDelegate  implements ChatRoomListener,ChatMessageListAdapter.ChatMessageClickLListener {

    private  String  TAG="ChatDelegate";
    private  String title;
    private  List<MsgModel>  list;
    private boolean  isRightSend=true;

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        setChatRoomListener(this);
        Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null) {
            title = bundle.getString("title");
            setChatTitleText(title);
            LogUtils.i(TAG, "初始化数据：" + title);
        }
        list=new ArrayList<>();
        initAdapter(list,this);

    }


    @Override
    public void onBack() {
        LogUtils.i(TAG,"返回");
        getPickImageHelper().canclePickImageHelperListener(listener, PickImageHelperClient.OperationType.Camera);
        getPickImageHelper().canclePickImageHelperListener(listener, PickImageHelperClient.OperationType.Albunm);
    }

    @Override
    public void onRefreshListeer() {
          getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void sendMsg(MessageObject mMessageObject,MsgType msgType, Object obj, long time) {
        updateUi(mMessageObject,msgType,obj,time);
    }


    @Override
    public void recevierMsg() {

    }


    /**
     * 点击
     * @param pos
     * @param data
     */
    @Override
    public void onClickHeaderLogo(int pos, List data) {
        Toast.makeText(this.getActivity(),"点击了头像"+pos,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickPicture(int pos, List data) {
        Toast.makeText(this.getActivity(),"点击了图片"+pos,Toast.LENGTH_SHORT).show();
        ArrayList<BaseMedia> medias = new ArrayList<>(1);
        String path=list.get(pos).getMsgData();
        LogUtils.i(TAG,"图片路径:"+path);
        ImageMedia  mMedia=new ImageMedia("1",path);
        medias.add(mMedia);
        Boxing.get().withIntent(this.getActivity(), BoxingViewActivity.class, medias).start(this.getActivity(), BoxingConfig.ViewMode.PREVIEW);
   }


    @Override
    public void onMenuClick(int pos, BaseAction baseAction) {
        if(pos==0){//相册
            getPickImageHelper().OpenAlbum(this.getActivity()).requestCallBack(listener,PickImageHelperClient.OperationType.Albunm);
        }else if(pos==1){//拍照
            getPickImageHelper().requestCameraPermission(this.getActivity()).requestCallBack(listener, PickImageHelperClient.OperationType.Camera);

         }
     }

    PickImageHelperObserverListener listener=new PickImageHelperObserverListener() {

        @Override
        public void onSuccess(MessageObject mMessageObject, MsgType msgType, Object obj, long time) {
            updateUi(mMessageObject,msgType,obj,time);
        }
        @Override
        public void onFail(String reease) {
              Toast.makeText(getActivity(),reease,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onException(Exception e) {
             LogUtils.i(TAG,"获取图片出现异常："+e.getMessage());
        }
    };

    private void updateUi(MessageObject mMessageObject, MsgType msgType, Object obj, long time) {
        MsgModel  model= new MsgModel();
        model.setMsgType(msgType);
        model.setTime(time);
        if(MsgType.PICTURE==msgType){
            obj="file://"+obj;
        }
        model.setMsgData((String) obj);
        if(isRightSend) {
            isRightSend=false;
            model.setmMessageObject(MessageObject.rightTyoe);
        }else{
            isRightSend=true;
            model.setmMessageObject(MessageObject.leftType);
        }
        list.add(model);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                doScrollToBottom(list.size(),1);
            }
        });
    }


}
