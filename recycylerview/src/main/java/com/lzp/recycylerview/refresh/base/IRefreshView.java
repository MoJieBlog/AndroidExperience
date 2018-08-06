package com.lzp.recycylerview.refresh.base;

/**
 * Created by Li Xiaopeng on 18/8/6.
 */
public interface IRefreshView {
    void onRefreshing();
    void onRefreshFinish();
    int getRefreshViewHeight();
}
