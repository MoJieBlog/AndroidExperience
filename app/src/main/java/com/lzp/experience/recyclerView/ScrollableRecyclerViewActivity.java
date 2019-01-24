package com.lzp.experience.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.BaseActivity;
import com.lzp.experience.R;
import com.lzp.experience.recyclerView.activity.StickerTitleActivity;
import com.lzp.experience.recyclerView.adapter.ScrollableAdapter;
import com.lzp.experience.recyclerView.activity.LayoutMangerActivity;
import com.lzp.experience.recyclerView.view.ScrollAbleRecyclerView;
import com.lzp.recycylerview.view.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public class ScrollableRecyclerViewActivity extends BaseActivity implements MRecyclerView.OnItemClickListener {

    @BindView(R.id.rcv)
    ScrollAbleRecyclerView rcv;
    private List<String> list;
    private ScrollableAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_recyclerview);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        list.add("layoutManager");
        list.add("ScrollView嵌套recyclerView");
        list.add("title悬停");
        for (int i = 1; i < 21; i++) {
            list.add(String.valueOf(i));
        }

        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ScrollableAdapter(this);
        rcv.setAdapter(adapter);
        adapter.refreshData(list);

        rcv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
        Intent intent = null;
        if (list.get(position).equals("layoutManager")){
            intent = new Intent(this,LayoutMangerActivity.class);
            startActivity(intent);
        }else if(list.get(position).equals("ScrollView嵌套recyclerView")){

        }else if(list.get(position).equals("title悬停")){
            intent = new Intent(this, StickerTitleActivity.class);
            startActivity(intent);
        }else{

        }
    }
}
