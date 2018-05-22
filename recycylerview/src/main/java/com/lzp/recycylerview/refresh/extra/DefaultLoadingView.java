package com.lzp.recycylerview.refresh.extra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzp.recycylerview.R;
import com.lzp.recycylerview.refresh.base.LoadingLayout;

/**
 * Created by Li Xiaopeng on 18/5/22.
 */

public class DefaultLoadingView extends LoadingLayout {

    private View rootView;

    private int animViewWidth,animViewHeight;

    private int tagerViewHeight = 0;

    private ProgressBar pbLoad;
    private TextView tvLoad;

    public DefaultLoadingView(@NonNull Context context) {
        this(context,null);
    }

    public DefaultLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.loading_view
                , this, false);
        pbLoad = rootView.findViewById(R.id.pb_load);
        tvLoad = rootView.findViewById(R.id.tv_load);
        animViewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        animViewHeight =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                        mResources.getDisplayMetrics());
        addView(rootView);
    }

    public void onLoadAll(String loadAllDesc){
        tvLoad.setText(loadAllDesc);
        pbLoad.setVisibility(GONE);
    }

    public void loadFail(String loadFial){
        tvLoad.setText(loadFial);
        pbLoad.setVisibility(GONE);
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
