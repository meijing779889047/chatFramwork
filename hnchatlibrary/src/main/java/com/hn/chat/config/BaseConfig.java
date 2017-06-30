package com.hn.chat.config;

import android.content.Context;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：基本数据配置
 * 创建人：Administrator
 * 创建时间：2017/6/28 15:39
 * 修改人：Administrator
 * 修改时间：2017/6/28 15:39
 * 修改备注：
 * Version:  1.0.0
 */
public class BaseConfig {

    // context
    private static Context context;
    /**
     * 初始化BaseConfig，须传入context以及用户信息提供者
     * @param context          上下文
     */
    public static void init(Context context) {
        BaseConfig.context = context.getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }

}
