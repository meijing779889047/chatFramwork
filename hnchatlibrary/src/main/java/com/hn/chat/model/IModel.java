package com.hn.chat.model;


import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：model层 用作双向绑定使用
 * 创建人：mj
 * 创建时间：2017/6/26 11:43
 * 修改人：Administrator
 * 修改时间：2017/6/26 11:43
 * 修改备注：
 * Version:  1.0.0
 */
public interface IModel {

    MessageObject  getMessageObj();//用于获取消息是在左边/右边

    MsgType  getMessageType();//获取消息类型

    long  getShowTime();//获取发送的时间 毫秒

    String  getHeaderIcon();//获取用户头像地址

    String  getUserNick();//获取用户昵称

    String getTextData();//文本 emoji  图片地址

    String getVoiceVideoLocUrl();//音/视频本地地址

    String getVoiceVideoNetUrl();//音/视频网络地址

    long  getVoiceVideoTime();//音视频的时间长

    long  getVoiceVideoSize();//音视频的大小




}
