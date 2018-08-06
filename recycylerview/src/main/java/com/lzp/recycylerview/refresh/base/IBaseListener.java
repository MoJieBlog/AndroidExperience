package com.lzp.recycylerview.refresh.base;

import android.view.View;

/**
 * Created by Li Xiaopeng on 18/5/22.
 */

public interface IBaseListener {

    /**
     * 移动时
     * @param moveOffset
     * @param isRefreshing
     */
    void onMoving(float moveOffset,boolean isRefreshing);

    /**
     * 设置子View的高度（listView,RecyclerView...）
     * @param height
     */
    void setTargetViewHeight(int height);

    /**
     * 动画View的高度
     */
    int getAnimateViewHeight();

    /**
     * 刷新布局的索引
     * @param refreshLayout
     */
    void setRefreshLayoutInstance(View refreshLayout);
}
