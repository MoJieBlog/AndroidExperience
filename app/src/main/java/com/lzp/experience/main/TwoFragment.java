package com.lzp.experience.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.lxp.utils.LogUtils;
import com.lxp.utils.ToastUtils;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.main.adapter.MainTwoAdapter;
import com.lzp.recycylerview.refresh.ILoadListener;
import com.lzp.recycylerview.refresh.IRefreshListener;
import com.lzp.recycylerview.refresh.MRefreshRecyclerView;
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
    private static final String TAG = "TwoFragment";
    @BindView(R.id.rcv)
    MRefreshRecyclerView rcv;
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
        rcv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mainTwoAdapter = new MainTwoAdapter(context);
        rcv.setAdapter(mainTwoAdapter);

        MRecyclerView recyclerView = rcv.getRecyclerView();
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.item_botton_in);
        LayoutAnimationController lac = new LayoutAnimationController(animation,0.1f);
        lac.setInterpolator(new AccelerateDecelerateInterpolator());
        recyclerView.setLayoutAnimation(lac);

        rcv.setOnItemClickListener(new MRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
                ToastUtils.setToast(context, "点击了" + position);
                LogUtils.logE("点击了", "onItemClick: " + (position + 1));
            }
        });

        rcv.setOnItemLongClickListener(new MRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id) {
                ToastUtils.setToast(context, "长按了" + position);
                LogUtils.logE("长按了", "onItemLongClick: " + (position + 1));
                return true;
            }
        });

        //rcv.setCanLoadMore(false);
        rcv.setOnLoadListener(new ILoadListener() {
            @Override
            public void onLoadMore() {
                LogUtils.logE(TAG, "onLoadMore: ");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<>();

                        for (int i = mainTwoAdapter.getList().size(); i < mainTwoAdapter.getList().size() + 10; i++) {
                            list.add("加载了" + String.valueOf(i + 1));
                        }
                        mainTwoAdapter.loadMoreData(list);
                        rcv.stopLoadMore();
                        if (mainTwoAdapter.getList().size() >= 50) {

                            rcv.stopLoadMoreWithDesc(true, "已加载全部数据");
                            rcv.setCanLoadMore(false);
                        }
                    }
                }, 1000);
            }
        });
        rcv.setRefresh(false);
        rcv.setOnRefreshListener(new IRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<>();

                        for (int i =0; i <15; i++) {
                            list.add("刷新了" + String.valueOf(i + 1));
                        }
                        mainTwoAdapter.refreshData(list);
                        rcv.stopRefresh();
                        rcv.setCanLoadMore(true);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(String.valueOf(i + 1));
        }

        mainTwoAdapter.refreshData(list);
        rcv.setCanLoadMore(true);
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
