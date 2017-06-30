package com.hn.chat.impl;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：聊天消息界面的接口回调
 * 创建人：mj
 * 创建时间：2017/6/27 18:49
 * 修改人：Administrator
 * 修改时间：2017/6/27 18:49
 * 修改备注：
 * Version:  1.0.0
 */
public interface ChatRoomListener   {

    void   onBack();//返回按钮监听

    void   onRefreshListeer();//下拉刷新监听


    void   sendMsg();//发送数据

    void   recevierMsg();//接收数据

}
