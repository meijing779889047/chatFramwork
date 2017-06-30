package com.hn.chat.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hn.chat.view.IDelegate;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：presneter层  用于处理view层与数据层的数据交互
 * 创建人：Administrator
 * 创建时间：2017/6/26 13:47
 * 修改人：Administrator
 * 修改时间：2017/6/26 13:47
 * 修改备注：
 * Version:  1.0.0
 */
public abstract class ActivityPresenter<T extends IDelegate> extends AppCompatActivity {

    public   T  mViewDelegate;

    public ActivityPresenter() {
        try {
            mViewDelegate=getDelegateClass().newInstance();
        } catch (InstantiationException e) {
           throw  new  RuntimeException("create delegaate  error");
        } catch (IllegalAccessException e) {
            throw  new  RuntimeException("create delegaate  error");
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDelegate.create(getLayoutInflater(),null,savedInstanceState);
        setContentView(mViewDelegate.getRootView());
        initToolBar();
        mViewDelegate.initWidget();

        bindEventListener();
    }


    /**
     * 时间绑定可重写
     */
    protected  void bindEventListener(){

    }


    protected   void initToolBar(){
        Toolbar  toolBar=mViewDelegate.getToolBar();
        if(toolBar!=null){
            setSupportActionBar(toolBar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mViewDelegate.getOptionMenuId()!=0){
            getMenuInflater().inflate(mViewDelegate.getOptionMenuId(),menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(mViewDelegate==null) {
            try {
                mViewDelegate = getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                throw new RuntimeException("create delegaate  error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create delegaate  error");

            }
           }
        }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewDelegate=null;
    }

    /**
     * 获取视图代理类
     * @return
     */
    public abstract  Class<T>  getDelegateClass();


}
