package com.hn.chat.ui;

import com.hn.chat.delegate.ChatDelegate;
import com.hn.chat.impl.ChatRoomListener;
import com.hn.chat.presenter.ActivityPresenter;

/**
 * 聊天消息界面
 */
public class ChatActivity extends ActivityPresenter<ChatDelegate>   implements ChatRoomListener {
    @Override
    public Class<ChatDelegate> getDelegateClass() {
        return ChatDelegate.class;
    }

    @Override
    public void onBack() {
    }

    @Override
    public void onRefreshListeer() {

    }

}
