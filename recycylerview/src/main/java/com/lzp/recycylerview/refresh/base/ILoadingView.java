package com.lzp.recycylerview.refresh.base;

/**
 * Created by Li Xiaopeng on 18/8/6.
 */
public interface ILoadingView {
    void onLoading();
    void onLoadingFinish();
    int getLoadViewHeight();
}
