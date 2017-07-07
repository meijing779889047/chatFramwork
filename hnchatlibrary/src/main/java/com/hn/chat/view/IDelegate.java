package com.hn.chat.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：view层 视图代理接口
 * 创建人：Administrator
 * 创建时间：2017/6/26 11:46
 * 修改人：Administrator
 * 修改时间：2017/6/26 11:46
 * 修改备注：
 * Version:  1.0.0
 */
public interface IDelegate {

    void     create(LayoutInflater inflater, ViewGroup viewGroup, Bundle  savaInstance);

    View      getRootView();

    Bundle    getSavedInstanceState();

    void      initWidget();

    Toolbar   getToolBar();

    int       getOptionMenuId();

}
