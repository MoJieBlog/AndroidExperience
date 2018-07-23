package com.lzp.experience.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzp.experience.R;
import com.lzp.experience.main.adapter.MainTwoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Li Xiaopeng on 18/6/26.
 */
public class SheetAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> list = new ArrayList<>();

    public SheetAdapter(Context context) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_two, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }





    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
