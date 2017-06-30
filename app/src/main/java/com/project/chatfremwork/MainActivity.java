package com.project.chatfremwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.chat.ui.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_open_chat)
    TextView tvOpenChat;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_open_chat)
    public void onClick() {
        Bundle bundle=new Bundle();
        Intent  intent=new Intent(MainActivity.this, ChatActivity.class);
        bundle.putString("title","聊天室");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
