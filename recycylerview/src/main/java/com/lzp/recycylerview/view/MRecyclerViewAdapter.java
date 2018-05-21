package com.lzp.recycylerview.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义adapter实现item点击和长按
 * Created by Li Xiaopeng on 18/5/21.
 */

public abstract class MRecyclerViewAdapter extends RecyclerView.Adapter{

    private MRecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = (MRecyclerView) recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public int getItemCount() {
        return getMItemCount();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onMCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        onMBindViewHolder(holder,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getOnItemClickListener()!=null){
                    recyclerView.getOnItemClickListener().onItemClick(recyclerView,holder.itemView,position,holder.getItemId());
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (recyclerView.getOnItemLongClickListener()!=null){
                    return recyclerView.getOnItemLongClickListener().onItemLongClick(recyclerView,holder.itemView,position,holder.getItemId());
                }else{
                    return true;
                }
            }
        });
    }


    public abstract int getMItemCount();
    public abstract RecyclerView.ViewHolder onMCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onMBindViewHolder(RecyclerView.ViewHolder holder, int position);

}
