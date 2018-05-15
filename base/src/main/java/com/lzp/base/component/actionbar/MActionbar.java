package com.lzp.base.component.actionbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lzp.base.R;
import com.lzp.base.utils.UIUtils;


/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public class MActionbar implements IMActionbar {

    private Context context;
    private Resources resources;
    private LayoutInflater inflater;

    private TextView title, rightText, leftText;
    private ImageButton rightIcon, leftIcon;
    private View rootView, bottomLine;

    public MActionbar(Context context) {
        this.context = context;
        resources = context.getResources();
        inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.title_layout, null);
        ViewGroup.LayoutParams par = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UIUtils.getActionBarHeight(context));
        rootView.setLayoutParams(par);
        title = rootView.findViewById(R.id.tv_title);
        rightText = rootView.findViewById(R.id.tv_right);
        leftText = rootView.findViewById(R.id.tv_left);
        rightIcon = rootView.findViewById(R.id.iv_right_icon);
        leftIcon = rootView.findViewById(R.id.iv_left_icon);
        bottomLine = rootView.findViewById(R.id.bottom_line);
    }

    public void setClickListener(View.OnClickListener listener) {
        if (title != null) title.setOnClickListener(listener);
        if (rightText != null) rightText.setOnClickListener(listener);
        if (rightIcon != null) rightIcon.setOnClickListener(listener);
        if (leftText != null) leftText.setOnClickListener(listener);
        if (leftIcon != null) leftIcon.setOnClickListener(listener);
    }

    public TextView getTitleView() {
        return title;
    }

    public TextView getRightTextView() {
        return rightText;
    }

    private ImageButton getRightIconView() {
        return rightIcon;
    }

    public TextView getLeftTextView() {
        return leftText;
    }

    private ImageButton getLeftIconView() {
        return leftIcon;
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionbarBackgroundColor(int color) {
        if (rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }

    @Override
    public void setActionbarBackgroundColorRes(int colorRes) {
        setActionbarBackgroundColor(resources.getColor(colorRes));
    }

    @Override
    public void setRightIconDrawable(Drawable drawable) {
        if (rightIcon != null)
            rightIcon.setImageDrawable(drawable);
    }

    @Override
    public void setRightIconRes(int drawableRes) {
        setRightIconDrawable(resources.getDrawable(drawableRes));
    }

    @Override
    public void setRightIconVisible(boolean visible) {
        if (rightIcon != null) rightIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRightTextString(String rightTextStr) {
        if (rightText != null) rightText.setText(rightTextStr);
    }

    @Override
    public void setRightTextRes(int rightTextRes) {
        setRightTextString(resources.getString(rightTextRes));
    }

    @Override
    public void setRightTextVisible(boolean visible) {
        if (rightText != null) rightText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRightTextColor(int color) {
        if (rightText != null) rightText.setTextColor(color);
    }

    @Override
    public void setRightTextColorRes(int colorRes) {
        setRightTextColor(resources.getColor(colorRes));
    }

    @Override
    public void setLeftIconDrawable(Drawable drawable) {
        if (leftIcon != null) leftIcon.setImageDrawable(drawable);
    }

    @Override
    public void setLeftIconRes(int drawableRes) {
        setLeftIconDrawable(resources.getDrawable(drawableRes));
    }

    @Override
    public void setLeftIconVisible(boolean visible) {
        if (leftIcon != null) leftIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLeftTextString(String leftTextStr) {
        if (leftText != null) leftText.setText(leftTextStr);
    }

    @Override
    public void setLeftTextRes(int leftTextRes) {
        setLeftTextString(resources.getString(leftTextRes));
    }

    @Override
    public void setLeftTextVisible(boolean visible) {
        if (leftText != null) leftText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLeftTextColor(int color) {
        if (leftText != null) leftText.setTextColor(color);
    }

    @Override
    public void setLeftTextColorRes(int colorRes) {
        setLeftTextColor(resources.getColor(colorRes));
    }

    @Override
    public void setActionBarTitle(String titleStr) {
        if (title != null) title.setText(titleStr);
    }

    @Override
    public void setActionBarTitleRes(int titleRes) {
        setActionBarTitle(resources.getString(titleRes));
    }

    @Override
    public void setActionBarTitleColor(int color) {
        if (title != null) title.setTextColor(color);
    }

    @Override
    public void setActionBarTitleColorRes(int colorRes) {
        setActionBarTitleColor(resources.getColor(colorRes));
    }

    @Override
    public void setBottomLineVisible(boolean visible) {
        if (bottomLine != null) bottomLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setBottomLineColor(int color) {
        if (bottomLine != null) bottomLine.setBackgroundColor(color);
    }

    @Override
    public void setBottomLineColorRes(int colorRes) {
        setBottomLineColor(resources.getColor(colorRes));
    }

    @Override
    public void onRelease() {
        context = null;
        resources = null;
        inflater = null;
        bottomLine = null;
        title = null;
        rightText = null;
        leftText = null;
        rightIcon = null;
        leftIcon = null;
        rootView = null;
    }
}
