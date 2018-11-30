package com.lzp.experience;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 2018/11/29
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.rcv)
    RecyclerView rcv;

    MAdapter adapter;


    private List<Model> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        list = new ArrayList<>();
        for (int i=0;i<500;i++){
            Random random = new Random();
            int i1 = random.nextInt(300);
            Model model = new Model();
            int i2 = UIUtils.dpToPx(this, 50);
            model.setHeight(i2+i1);

            list.add(model);
        }
        if (adapter==null){
            adapter = new MAdapter();
        }
        int dp_15 = UIUtils.dpToPx(TestActivity.this, 30);
        rcv.addItemDecoration(new StaggerDiver(dp_15,dp_15));
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcv.setAdapter(adapter);
    }

    class MAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test, viewGroup, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            Model model = list.get(i);
            MyViewHolder holder = (MyViewHolder) viewHolder;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.height = model.getHeight();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

            /*int spanIndex = layoutParams.getSpanIndex();

            int dp_15 = UIUtils.dpToPx(TestActivity.this, 15);

            if (spanIndex/2==0){
                layoutParams.leftMargin = dp_15;
                layoutParams.rightMargin = 0;
            }else{
                layoutParams.rightMargin = dp_15;
                layoutParams.leftMargin = 0;
            }*/
            holder.itemView.setLayoutParams(layoutParams);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    class Model{
        private int height;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
