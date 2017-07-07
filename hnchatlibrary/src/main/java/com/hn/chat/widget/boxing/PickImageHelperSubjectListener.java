package com.hn.chat.widget.boxing;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：被观察接口
 * 创建人：Administrator
 * 创建时间：2017/7/6 19:35
 * 修改人：Administrator
 * 修改时间：2017/7/6 19:35
 * 修改备注：
 * Version:  1.0.0
 */
public interface PickImageHelperSubjectListener {
     void requestCallBack(PickImageHelperObserverListener observerListener, PickImageHelperClient.OperationType type);
     void canclePickImageHelperListener(PickImageHelperObserverListener observerListener,PickImageHelperClient.OperationType type);



}
