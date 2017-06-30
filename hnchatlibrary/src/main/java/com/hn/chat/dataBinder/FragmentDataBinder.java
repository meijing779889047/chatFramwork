package com.hn.chat.dataBinder;

import android.os.Bundle;
import android.view.View;

import com.hn.chat.model.IModel;
import com.hn.chat.presenter.FragmentPresenter;
import com.hn.chat.view.IDelegate;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：集成数据-视图绑定的Fragment基类(Presenter层)
 * 创建人：Administrator
 * 创建时间：2017/6/26 14:26
 * 修改人：Administrator
 * 修改时间：2017/6/26 14:26
 * 修改备注：
 * Version:  1.0.0
 */
public abstract class FragmentDataBinder<T  extends IDelegate>  extends FragmentPresenter<T> {


    protected DataBinder mDataBinder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (mDataBinder != null)
            mDataBinder.viewBindModel(mViewDelegate, data);
    }
}
