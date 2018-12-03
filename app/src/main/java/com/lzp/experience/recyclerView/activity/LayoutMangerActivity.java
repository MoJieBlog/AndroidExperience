package com.lzp.experience.recyclerView.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.recyclerView.layoutManger.TestLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/10/17
 */
public class LayoutMangerActivity extends BaseActivity implements IBasePage{

    @BindView(R.id.rcv)
    RecyclerView rcv;
    private int[] imags = {R.mipmap.yinsuwan, R.mipmap.yinsuwan_1, R.mipmap.yinsuwan_2,
            R.mipmap.yinsuwan_3, R.mipmap.yinsuwan_4,R.mipmap.yinsuwan, R.mipmap.yinsuwan_1, R.mipmap.yinsuwan_2,
            R.mipmap.yinsuwan_3, R.mipmap.yinsuwan_4,R.mipmap.yinsuwan, R.mipmap.yinsuwan_1, R.mipmap.yinsuwan_2,
            R.mipmap.yinsuwan_3, R.mipmap.yinsuwan_4,R.mipmap.yinsuwan, R.mipmap.yinsuwan_1, R.mipmap.yinsuwan_2,
            R.mipmap.yinsuwan_3, R.mipmap.yinsuwan_4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_layout_manager);
        ButterKnife.bind(this);


    }

    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {
        initRcv();
    }

    private void initRcv() {
        rcv.setLayoutManager(new TestLayoutManager(this));
        rcv.setAdapter(new MAdapter());
       /* SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(rcv);*/
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {

    }

    class MAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_manager, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.iv.setImageResource(imags[i]);
        }

        @Override
        public int getItemCount() {
            return imags.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.iv)
            ImageView iv;

            MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
