package com.project.chatfremwork;

import android.app.Application;

import com.hn.chat.config.BaseConfig;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/6/28 15:48
 * 修改人：Administrator
 * 修改时间：2017/6/28 15:48
 * 修改备注：
 * Version:  1.0.0
 */
public class MyApplication  extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        BaseConfig.init(this);
    }
}
