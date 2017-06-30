package com.hn.chat.dataBinder;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hn.chat.model.IModel;
import com.hn.chat.presenter.ActivityPresenter;
import com.hn.chat.view.IDelegate;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：集成数据-视图绑定的Activity基类(Presenter层)
 * 创建人：Administrator
 * 创建时间：2017/6/26 14:19
 * 修改人：Administrator
 * 修改时间：2017/6/26 14:19
 * 修改备注：
 * Version:  1.0.0
 */
public abstract  class ActivityDataBinder<T  extends IDelegate>  extends ActivityPresenter<T> {

   private  DataBinder  mDataBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinder= getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (mDataBinder != null)
            mDataBinder.viewBindModel(mViewDelegate, data);
    }


}
