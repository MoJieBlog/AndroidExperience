package com.lzp.experience.recyclerView.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.recyclerView.itemDecoration.StickerModelHelper;
import com.lzp.experience.recyclerView.itemDecoration.StickerTitleItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 2019/1/24
 */
public class StickerTitleActivity extends BaseActivity implements IBasePage {

    @BindView(R.id.rcv)
    RecyclerView rcv;

    private Adapter adapter;
    private StickerTitleItemDecoration itemDecoration;
    List<TestModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setContentView(R.layout.activity_sticker_title);
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
        rcv.setLayoutManager(new LinearLayoutManager(this));
        itemDecoration = new StickerTitleItemDecoration<TestModel>(this);
        itemDecoration.setList(list);
        rcv.addItemDecoration(itemDecoration);
        adapter = new Adapter();
        rcv.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {
        for (int i = 0; i < 100; i++) {
            TestModel model = new TestModel();
            model.setTitle(String.valueOf(i / 10));
            model.setContent(String.valueOf(i));
            LogUtils.logE("======", model.toString());
            list.add(model);
        }
        adapter.refreshData(list);
    }


    class Adapter extends RecyclerView.Adapter {

        private List<TestModel> list = new ArrayList<>();

        public void refreshData(List<TestModel> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_two, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.tv.setText(list.get(i).getContent());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


         class MyViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.tv)
            TextView tv;

            MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }


    public static class TestModel implements StickerModelHelper {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String getCompareContent() {
            return title;
        }

        @Override
        public String toString() {
            return "TestModel{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
