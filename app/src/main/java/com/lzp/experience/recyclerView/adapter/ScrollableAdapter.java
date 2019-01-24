package com.lzp.experience.recyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxp.utils.LogUtils;
import com.lzp.experience.R;
import com.lzp.recycylerview.view.MRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public class ScrollableAdapter extends MRecyclerViewAdapter {

    private Context context;
    private List<String> list = new ArrayList<>();

    public ScrollableAdapter(Context context) {
        this.context = context;
    }

    public List<String> getList() {
        return list;
    }

    public void refreshData(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMoreData(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getMItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onMCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_two, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onMBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tv.setText(list.get(position));
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
