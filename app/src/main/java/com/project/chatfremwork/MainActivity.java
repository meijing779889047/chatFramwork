package com.project.chatfremwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.chat.model.MsgModel;
import com.hn.chat.ui.ChatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        //获取class对象
        //1.通过forName(String)  加入类路径
        try {
            Class<MsgModel> mainClass1 = (Class<MsgModel>) Class.forName("com.project.chatfremwork.MainActivity");
            Log.i("mm",mainClass1.getName());
        } catch (ClassNotFoundException e) {
            Log.i("mm","该类名没有找到");
        }
        //2.通过调用某个类的属性来获取该类的class对象
        Class<MsgModel> mainClass2 = MsgModel.class;
        Log.i("mm",mainClass2.getName());
        //3.通过类的getClass（）获取class对象 默认是object
        Class<? extends Class> mainClass3= MsgModel.class.getClass();
        Log.i("mm",mainClass3.getName());
        //获取class对应的属性
        //1。获取class对应的所有属性
        Field[] mDeclaredFields = mainClass2.getDeclaredFields();
        for (Field mDeclaredField:mDeclaredFields){
            Log.i("mm","mDeclaredField:"+mDeclaredField.getName());
        }
        //2.获取class对应的public属性
        Field[] fields = mainClass2.getFields();
        for (Field field:fields){
            Log.i("mm","fields:"+field.getName());
        }

        Method[] methods = mainClass2.getDeclaredMethods();
        for (Method method:methods){
            Log.i("mm","methods:"+method.getName());
        }

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
