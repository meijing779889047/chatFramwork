package com.hn.chat.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：view层  视图代理层
 * 创建人：Administrator
 * 创建时间：2017/6/26 11:50
 * 修改人：Administrator
 * 修改时间：2017/6/26 11:50
 * 修改备注：
 * Version:  1.0.0
 */
public  abstract   class AppDeleagte implements IDelegate {

    protected   View rootView;
    private     SparseArray<View>   mViews=new SparseArray<>();
    private     Bundle savaInstance;
    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle savaInstance) {
         int layoutId=getRootLayoutId();
         rootView=inflater.inflate(layoutId,viewGroup,false);
         this.savaInstance=savaInstance;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Bundle getSavedInstanceState() {
        return savaInstance;
    }

    @Override
    public void initWidget() {


    }


    @Override
    public Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getOptionMenuId() {
        return 0;
    }

    /**
     * 获取根布局id
     * @return
     */
    public  abstract  int getRootLayoutId();






    public   <T extends   View>   T get(int id){
        return  bindView(id);
    }

    protected   <T extends   View>  T  bindView(int id){
       T view= (T) mViews.get(id);
        if(view==null){
            view= (T) rootView.findViewById(id);
            mViews.put(id,view);
        }
        return  view;
    }

    public   void  setOnClickListener(View.OnClickListener listener,int... ids){
       if(ids==null){
           return;
       }
        for (int id:ids){
            get(id).setOnClickListener(listener);
        }
    }

    public  void  showToast(String msg){
        Toast.makeText(this.getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public  <T  extends Activity>  T getActivity(){
        return (T) rootView.getContext();
    }





}
