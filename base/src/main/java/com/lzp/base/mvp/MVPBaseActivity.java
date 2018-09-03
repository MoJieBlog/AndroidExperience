package com.lzp.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzp.base.component.BaseActivity;

/**
 * Created by Li Xiaopeng on 18/7/10.
 */
public abstract class MVPBaseActivity<T extends BasePresenter> extends BaseActivity {

    protected T iBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iBasePresenter = initPresenter();
    }

    @NonNull
    public abstract T initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iBasePresenter!=null)iBasePresenter.onViewDestroy(this);
    }
}
