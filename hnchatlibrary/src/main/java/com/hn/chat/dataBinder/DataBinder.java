package com.hn.chat.dataBinder;

import com.hn.chat.model.IModel;
import com.hn.chat.view.IDelegate;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：ViewModel接口实现
 * 创建人：Administrator
 * 创建时间：2017/6/26 14:14
 * 修改人：Administrator
 * 修改时间：2017/6/26 14:14
 * 修改备注：
 * Version:  1.0.0
 */
public interface DataBinder<T  extends IDelegate,D extends IModel> {


    /**
     * 将数据与view进行绑定，当数据发生改变时，框架将知道该数据与那个view进行绑定，并进行数据更新
     * @param viewDeleagte   视图代理
     * @param data           数据
     */
    void  viewBindModel(T viewDeleagte,D data);

}
