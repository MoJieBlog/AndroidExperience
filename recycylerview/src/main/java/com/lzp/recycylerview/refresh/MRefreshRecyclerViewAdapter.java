package com.lzp.recycylerview.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lzp.recycylerview.view.MRecyclerViewAdapter;

/**
 * Created by Li Xiaopeng on 18/5/21.
 */

public abstract class MRefreshRecyclerViewAdapter extends MRecyclerViewAdapter {

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        super.onBindViewHolder(holder, position);
    }
}
