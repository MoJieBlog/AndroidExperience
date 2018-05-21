package com.lzp.recycylerview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义实现item点击事件和长按事件
 * Created by Li Xiaopeng on 18/5/21.
 */

public class MRecyclerView extends RecyclerView {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MRecyclerView(Context context) {
        super(context);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMAdapter(MRecyclerViewAdapter adapter) {
        if (adapter!=null){
            setAdapter(adapter);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener){
        this.onItemLongClickListener = itemLongClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerView recyclerView, View view, int position, long id);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id);
    }
}
