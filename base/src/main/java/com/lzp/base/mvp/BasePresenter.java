package com.lzp.base.mvp;

/**
 * Created by Li Xiaopeng on 18/7/10.
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter{
    private static final String TAG = "BasePresenter";

    protected V iBaseView;

    public BasePresenter(V iBaseView){
        this.iBaseView = iBaseView;
    }

    @Override
    public void onViewDestroy(Object tag) {
        iBaseView = null;
    }
}
