package com.hn.chat.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hn.chat.view.IDelegate;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：presenter层 用于处理view层与model层的数据交互
 * 创建人：Administrator
 * 创建时间：2017/6/26 14:02
 * 修改人：Administrator
 * 修改时间：2017/6/26 14:02
 * 修改备注：
 * Version:  1.0.0
 */
public abstract class FragmentPresenter<T extends IDelegate>  extends Fragment {

    public   T   mViewDelegate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mViewDelegate=getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            throw  new  RuntimeException("create delegaate  error");
        } catch (IllegalAccessException e) {
            throw  new  RuntimeException("create delegaate  error");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViewDelegate.create(inflater,container,savedInstanceState);
        return mViewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         mViewDelegate.initWidget();
         bindEventListener();
    }

    public    void bindEventListener(){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(mViewDelegate.getOptionMenuId()!=0){
            inflater.inflate(mViewDelegate.getOptionMenuId(),menu);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(mViewDelegate==null){
            try {
                mViewDelegate=getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                throw  new  RuntimeException("create delegaate  error");
            } catch (IllegalAccessException e) {
                throw  new  RuntimeException("create delegaate  error");
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
