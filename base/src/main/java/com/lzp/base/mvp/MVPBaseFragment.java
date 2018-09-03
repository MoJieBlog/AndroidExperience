package com.lzp.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lzp.base.component.BaseFragment;

/**
 * Created by Li Xiaopeng on 18/7/10.
 */
public abstract class MVPBaseFragment<T extends IBasePresenter>  extends BaseFragment{

    protected T iBasePresenter;

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        iBasePresenter = initPresenter();
    }

    @NonNull
    public abstract T initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iBasePresenter!=null)iBasePresenter.onViewDestroy(this);
    }

}
