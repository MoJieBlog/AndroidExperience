package com.lzp.experience.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.ConstraintLayoutTestActivity;
import com.lzp.experience.MNotificationManager;
import com.lzp.experience.MPermissionTestActivity;
import com.lzp.experience.OnceClickListener;
import com.lzp.experience.R;
import com.lzp.experience.desiginstudy.TBIndexActivity;
import com.lzp.experience.image.ColorMatrixStudyActivity;
import com.lzp.experience.recyclerView.ScrollableRecyclerViewActivity;
import com.lzp.experience.viewpager.ViewPagerListActivity;
import com.mocai.umeng.share.ShareManager;
import com.mocai.umeng.share.SharePlatForm;
import com.mocai.umeng.share.ShareType;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class OneFragment extends MainBaseFragment implements IBasePage, View.OnClickListener {

    private static final String TAG = "OneFragment";
    Unbinder unbinder;
    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_permission)
    Button btnPermission;
    @BindView(R.id.btn_img)
    Button btnImg;
    @BindView(R.id.btn_sign)
    Button btnSign;
    @BindView(R.id.btn_vp)
    Button btnVp;
    @BindView(R.id.btn_rcv)
    Button btnRcv;
    @BindView(R.id.btn_tb)
    Button btnTb;


    public static OneFragment getFragment() {
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        LogUtils.logE(TAG, "onMCreate: " + this.getClass().getSimpleName());
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

        ShareManager.getInstance().withActivity(getActivity()).
                shareType(ShareType.TEXT)
                .withContent("666666666")
                .withPlatForm(SharePlatForm.SHARE_WX_CHART)
                .build().share();
    }
    class MUMShareListener implements UMShareListener{

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
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
        btnImg.setOnClickListener(this);
        btnSign.setOnClickListener(this);
        btnVp.setOnClickListener(this);
        btnTb.setOnClickListener(this);

        btnVp.setOnClickListener(new mOnceClickListener());
        btnPermission.setOnClickListener(new mOnceClickListener());
        btnRcv.setOnClickListener(new mOnceClickListener());
    }

    class mOnceClickListener extends OnceClickListener {
        @Override
        public void onOnceClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_permission:
                    intent = new Intent(getActivity(), MPermissionTestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_vp:
                    intent = new Intent(getActivity(), ViewPagerListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_rcv:
                    intent = new Intent(getActivity(), ScrollableRecyclerViewActivity.class);
                    startActivity(intent);
                    break;
            }
        }
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
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_notification:
                Snackbar.make(btnPermission
                        , "66666", Snackbar.LENGTH_LONG).show();
                MNotificationManager.getInstance().showAdNotification(getActivity());
                MNotificationManager.getInstance().showAPPMSGNotification(getActivity());
                break;

            case R.id.btn_permission:
                intent = new Intent(getActivity(), MPermissionTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_img:
                intent = new Intent(getActivity(), ColorMatrixStudyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign:
                intent = new Intent(getActivity(), ConstraintLayoutTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_vp:
                intent = new Intent(getActivity(), ViewPagerListActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_tb:
                intent = new Intent(getActivity(), TBIndexActivity.class);
                startActivity(intent);
                break;
        }
    }
}
