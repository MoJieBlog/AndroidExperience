package com.lzp.experience.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lzp.base.component.IBasePage;
import com.lzp.experience.MNotificationManager;
import com.lzp.experience.MPermissionTestActivity;
import com.lzp.experience.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class OneFragment extends MainBaseFragment implements IBasePage, View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_permission)
    Button btnPermission;


    public static OneFragment getFragment() {
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);



    }


    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {
        initActionBar();
    }

    private void initActionBar() {
        setStatusBarColorRes(R.color.theme);
        setActionBarBackgroundColorRes(R.color.theme);
        setActionBarTitleRes(R.string.app_name);
        setActionBarTitleColorRes(R.color.ffffff);
        setActionBarBottomLineVisible(false);
    }

    @Override
    public void setListener() {
        btnNotification.setOnClickListener(this);
        btnPermission.setOnClickListener(this);
    }

    @Override
    public void getData() {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_notification:
                Snackbar.make(btnPermission
                        ,"66666",Snackbar.LENGTH_LONG).show();
                MNotificationManager.getInstance().showAdNotification(getActivity());
                MNotificationManager.getInstance().showAPPMSGNotification(getActivity());
                break;

            case R.id.btn_permission:
                Intent intent = new Intent(getActivity(), MPermissionTestActivity.class);
                startActivity(intent);
                break;
        }
    }
}
