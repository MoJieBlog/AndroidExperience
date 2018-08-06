package com.lzp.recycylerview.refresh.extra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzp.recycylerview.R;
import com.lzp.recycylerview.refresh.base.BaseRefreshLayout;
import com.lzp.recycylerview.refresh.base.IRefreshView;
import com.lzp.recycylerview.refresh.base.LoadingLayout;

/**
 * Created by Li Xiaopeng on 18/5/22.
 */

public class DefaultRefreshView extends BaseRefreshLayout {

    private View rootView;

    private int animViewWidth,animViewHeight;

    private int tagerViewHeight = 0;

    private ProgressBar pbFresh;
    private TextView tvFresh;
    private ImageView ivArrowFresh;

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
        pbFresh = rootView.findViewById(R.id.pb_fresh);
        tvFresh = rootView.findViewById(R.id.tv_fresh);
        ivArrowFresh = rootView.findViewById(R.id.iv_arrow_fresh);
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
        pbFresh.setVisibility(GONE);
        ivArrowFresh.setVisibility(VISIBLE);
        tvFresh.setText(getContext().getResources().getString(R.string.pull_to_refresh));
        ivArrowFresh.setImageResource(R.mipmap.ic_arrow_down);
    }

    /**
     * 第二种状态，箭头朝上
     */
    public void canRefresh(){
        pbFresh.setVisibility(GONE);
        ivArrowFresh.setVisibility(VISIBLE);
        tvFresh.setText(getContext().getResources().getString(R.string.to_refresh));
        ivArrowFresh.setImageResource(R.mipmap.ic_arrow_up);
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
        if (moveOffset<animViewHeight){
            startMove();
        }else{
            canRefresh();
        }
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

    @Override
    public void onRefreshing() {
        setVisibility(VISIBLE);
        ivArrowFresh.setVisibility(GONE);
        pbFresh.setVisibility(VISIBLE);
        tvFresh.setText(getContext().getResources().getString(R.string.refreshing));
    }

    @Override
    public void onRefreshFinish() {
        setVisibility(VISIBLE);
    }

    @Override
    public int getRefreshViewHeight() {
        return rootView.getHeight();
    }
}
