package com.lzp.experience.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxp.utils.LogUtils;
import com.lxp.utils.ToastUtils;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.main.adapter.MainTwoAdapter;
import com.lzp.recycylerview.view.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class TwoFragment extends MainBaseFragment implements IBasePage {
    @BindView(R.id.rcv)
    MRecyclerView rcv;
    Unbinder unbinder;

    private Context context;

    private MainTwoAdapter mainTwoAdapter;

    public static TwoFragment getFragment() {
        TwoFragment twoFragment = new TwoFragment();
        return twoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.fragment_two);
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
        rcv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        mainTwoAdapter = new MainTwoAdapter(context);
        rcv.setAdapter(mainTwoAdapter);
        rcv.setOnItemClickListener(new MRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
                ToastUtils.setToast(context,"点击了"+position);
                LogUtils.logE("点击了", "onItemClick: "+(position+1));
            }
        });

        rcv.setOnItemLongClickListener(new MRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id) {
                ToastUtils.setToast(context,"长按了"+position);
                LogUtils.logE("长按了", "onItemLongClick: "+(position+1));
                return true;
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {
        List<String> list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add(String.valueOf(i+1));
        }

        mainTwoAdapter.refreshData(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
