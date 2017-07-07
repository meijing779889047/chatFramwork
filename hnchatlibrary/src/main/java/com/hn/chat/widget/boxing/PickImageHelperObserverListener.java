package com.hn.chat.widget.boxing;

import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：观察者接口
 * 创建人：Administrator
 * 创建时间：2017/7/6 19:33
 * 修改人：Administrator
 * 修改时间：2017/7/6 19:33
 * 修改备注：
 * Version:  1.0.0
 */
public interface PickImageHelperObserverListener {

    /**
     * 获取图片成功
     * @param
     *
     */
    void onSuccess(MessageObject mMessageObject, MsgType msgType, Object obj, long time);

    /**
     * 获取图片失败
     * @param reease
     */
    void onFail(String reease);//失败

    /**
     * 获取图片异常
     * @param e
     */
    void onException(Exception e);
}
