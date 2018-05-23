package com.lzp.recycylerview.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.lzp.recycylerview.refresh.extra.DefaultLoadingView;
import com.lzp.recycylerview.view.MRecyclerView;

/**
 * Created by Li Xiaopeng on 18/5/21.
 */

public class MRefreshRecyclerView extends SwipeRefreshLayout {

    public static final int LOADING_TYPE_DEFAULT = 0;
    public static final int REFRESH_TYPE_DEFAULT = 0;

    private Context mContext;
    private Resources mResources;

    private MRecyclerView recyclerView;
    private MRefreshRecyclerViewAdapter adapter;

    private boolean canLoadMore = true;//是否可以加载更多
    private ILoadListener loadListener;

    private DefaultLoadingView loadingView;

    private int loadingViewType = LOADING_TYPE_DEFAULT;
    private int refreshViewType = REFRESH_TYPE_DEFAULT;

    public MRefreshRecyclerView(Context context) {
        this(context,null);
    }

    public MRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mResources = context.getResources();
        init();
    }

    private void init() {
        recyclerView = new MRecyclerView(mContext);
        addView(recyclerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        createLoadMoreView();
    }

    private void createLoadMoreView() {
        switch (loadingViewType){
            case LOADING_TYPE_DEFAULT:
                loadingView = new DefaultLoadingView(mContext);
                break;
        }
    }

    public DefaultLoadingView getLoadingView() {
        return loadingView;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public void setOnLoadListener(ILoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public ILoadListener getLoadListener() {
        return loadListener;
    }

    public boolean canLoadMore(){
        return canLoadMore;
    }

    public void setAutoLoadCount(int autoLoadCount) {
        if (adapter!=null){
            adapter.setAutoLoadCount(autoLoadCount);
        }
    }

    public void stopLoadMore(){
        stopLoadMore(false,"");
    }
    public void stopLoadMore(boolean showTips,String desc){
        if (adapter!=null){
            adapter.stopLoadMore(showTips,desc);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(MRefreshRecyclerViewAdapter adapter){
        if (adapter!=null){
            this.adapter = adapter;
            adapter.setRecyclerView(this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setOnItemClickListener(MRecyclerView.OnItemClickListener clickListener){
        recyclerView.setOnItemClickListener(clickListener);
    }

    public void setOnItemLongClickListener(MRecyclerView.OnItemLongClickListener longClickListener){
        recyclerView.setOnItemLongClickListener(longClickListener);
    }
}
