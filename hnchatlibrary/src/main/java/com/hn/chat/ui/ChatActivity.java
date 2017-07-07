package com.hn.chat.ui;

import android.content.Intent;
import android.widget.Toast;

import com.hn.chat.delegate.ChatDelegate;
import com.hn.chat.presenter.ActivityPresenter;
import com.hn.chat.util.LogUtils;
import com.hn.chat.util.permission.MPermission;
import com.hn.chat.util.permission.annotation.OnMPermissionDenied;
import com.hn.chat.util.permission.annotation.OnMPermissionGranted;

import static com.hn.chat.delegate.BaseChatDelegate.BASIC_PERMISSION_REQUEST_CODE;

/**
 * 聊天消息界面
 */

public class ChatActivity extends ActivityPresenter<ChatDelegate>       {

    private String TAG="ChatActivity";


    @Override
    public Class<ChatDelegate> getDelegateClass() {
        return ChatDelegate.class;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG,"1111111111"+resultCode+"--->"+requestCode);
        mViewDelegate.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess(){
       mViewDelegate.takePhoto();

    }
    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed(){
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
        finish();
    }

}
