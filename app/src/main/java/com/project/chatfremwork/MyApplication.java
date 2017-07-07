package com.project.chatfremwork;

import android.app.Application;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hn.chat.config.BaseConfig;
import com.hn.chat.widget.boxing.BoxingFrescoLoader;
import com.hn.chat.widget.boxing.BoxingUcrop;

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

        /**
         * 初始化图片选择器框架
         */
        //初始化图片加载（必选）
        IBoxingMediaLoader loader = new BoxingFrescoLoader(this);
        BoxingMediaLoader.getInstance().init(loader);
        //初始化图片裁剪（可选）
       BoxingCrop.getInstance().init(new BoxingUcrop());
        BaseConfig.init(this);
        //初始化图片加载框架
       ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }
}
