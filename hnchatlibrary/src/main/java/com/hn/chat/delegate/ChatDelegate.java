package com.hn.chat.delegate;

import android.os.Bundle;

import com.hn.chat.util.LogUtils;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/6/28 8:52
 * 修改人：Administrator
 * 修改时间：2017/6/28 8:52
 * 修改备注：
 * Version:  1.0.0
 */
public class ChatDelegate extends BaseChatDelegate {

    private  String  TAG="ChatDelegate";
    private  String title;
    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null) {
            title = bundle.getString("title");
            setChatTitleText(title);
            LogUtils.i(TAG, "初始化数据：" + title);
        }
    }
}
