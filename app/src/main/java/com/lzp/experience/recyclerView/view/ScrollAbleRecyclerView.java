package com.lzp.experience.recyclerView.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lzp.recycylerview.view.MRecyclerView;
import com.lzp.recycylerview.view.MRecyclerViewAdapter;

/**
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public class ScrollAbleRecyclerView extends DampFrameView {
    private static final String TAG = "ScrollAbleRecyclerView";


    MRecyclerView recyclerView;

    public ScrollAbleRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ScrollAbleRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        recyclerView = new MRecyclerView(context);
        addView(recyclerView);
    }

    @Override
    protected boolean canScrollUp() {
        return recyclerView.canScrollVertically(1);
    }

    @Override
    protected boolean canScrollDown() {
        return recyclerView.canScrollVertically(-1);
    }

    @NonNull
    @Override
    protected View getTargetView() {
        return recyclerView;
    }

    /***************************以下为recyclerView本身有的方法*************************/
    public void setOnItemClickListener(MRecyclerView.OnItemClickListener onItemClicListener){
        recyclerView.setOnItemClickListener(onItemClicListener);
    }
    public void setLayoutManager(RecyclerView.LayoutManager linearLayoutManager) {
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setAdapter(MRecyclerViewAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
