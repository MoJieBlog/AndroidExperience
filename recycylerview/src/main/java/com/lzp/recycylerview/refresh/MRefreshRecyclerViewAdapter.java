package com.lzp.recycylerview.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.recycylerview.refresh.base.LoadingLayout;
import com.lzp.recycylerview.refresh.extra.DefaultLoadingView;
import com.lzp.recycylerview.view.MRecyclerViewAdapter;

/**
 * 自定义Adapter实现加载更多
 * Created by Li Xiaopeng on 18/5/21.
 */

public abstract class MRefreshRecyclerViewAdapter extends MRecyclerViewAdapter {

    public static final int ITEM_TYPE_LOAD_MORE = 500;

    private int autoLoadCount = 3;//提前多少个开始自动加载
    private boolean isLoading = false;
    protected Context context;
    protected MRefreshRecyclerView recyclerView;

    public MRefreshRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setRecyclerView(MRefreshRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (recyclerView.canLoadMore() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (recyclerView.canLoadMore() && position == getItemCount() - 1) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            LoadViewViewHolder holder = new LoadViewViewHolder(recyclerView.getLoadingView());
            return holder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (recyclerView.canLoadMore()) {
            if (getItemCount() - autoLoadCount <= position && !isLoading) {
                isLoading = true;
                recyclerView.getLoadingView().onRefreshingOrLoading();
                recyclerView.getLoadListener().onLoadMore();
            }
        }
        if (getItemViewType(position) == ITEM_TYPE_LOAD_MORE) {

        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void setAutoLoadCount(int autoLoadCount) {
        this.autoLoadCount = autoLoadCount;
    }

    public void stopLoadMore(boolean showTips, String desc) {
        if (recyclerView.getLoadingView() != null) {
            recyclerView.getLoadingView().onLoadFinish(showTips, desc);
        }
    }

    class LoadViewViewHolder extends RecyclerView.ViewHolder {
        public LoadViewViewHolder(View itemView) {
            super(itemView);

        }
    }
}
