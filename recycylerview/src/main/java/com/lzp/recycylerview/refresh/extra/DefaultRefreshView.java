package com.lzp.recycylerview.refresh.extra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.recycylerview.R;
import com.lzp.recycylerview.refresh.base.LoadingLayout;

/**
 * Created by Li Xiaopeng on 18/5/22.
 */

public class DefaultRefreshView extends LoadingLayout {

    private View rootView;

    private int animViewWidth,animViewHeight;

    private int tagerViewHeight = 0;

    public DefaultRefreshView(@NonNull Context context) {
        this(context,null);
    }

    public DefaultRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.fresh_view
                , this, false);
        animViewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        animViewHeight =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                        mResources.getDisplayMetrics());
        addView(rootView);
    }

    /**
     * 第一种状态，箭头朝下
     */
    public void startMove(){

    }

    /**
     * 第二种状态，箭头朝下
     */
    public void canRefresh(){

    }

    @Override
    public int getViewWidth() {
        return rootView.getMeasuredWidth();
    }

    @Override
    public int getViewHeight() {
        return rootView.getMeasuredHeight();
    }


    @Override
    public void onMoving(float moveOffset, boolean isRefreshing) {
        setVisibility(VISIBLE);
    }

    @Override
    public void onRefreshingOrLoading() {
        setVisibility(VISIBLE);
    }

    @Override
    public void onRefreshOrLoadFinish() {
        setVisibility(VISIBLE);
    }

    @Override
    public int getRefreshOrLoadViewHeight() {
        return rootView.getHeight();
    }

    @Override
    public void setTargetViewHeight(int height) {
        tagerViewHeight = height;
    }

    @Override
    public int getAnimateViewHeight() {
        return animViewHeight;
    }

    @Override
    public void setRefreshLayoutInstance(View refreshLayout) {

    }

}
