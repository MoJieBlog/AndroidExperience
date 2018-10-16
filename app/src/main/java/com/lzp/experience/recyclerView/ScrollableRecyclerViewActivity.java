package com.lzp.experience.recyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.lzp.base.component.BaseActivity;
import com.lzp.experience.R;
import com.lzp.experience.recyclerView.adapter.ScrollableAdapter;
import com.lzp.experience.recyclerView.view.ScrollAbleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public class ScrollableRecyclerViewActivity extends BaseActivity {

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
        for (int i = 1; i < 21; i++) {
            list.add(String.valueOf(i));
        }

        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ScrollableAdapter(this);
        rcv.setAdapter(adapter);
        adapter.refreshData(list);
    }
}
