package com.lzp.base;

import android.os.Bundle;

/**
 * Created by Li Xiaopeng on 18/5/14.
 */

public interface IBasePage {
    /**
     * 读取参数
     * @param bundle
     */
    public void readArguments(Bundle bundle);

    /**
     * 写入参数
     * @param bundle
     */
    public void writeArguments(Bundle bundle);

    /**
     * 初始化view
     */
    public void initView();

    /**
     * 设置监听
     */
    public void setListener();

    /**
     * 获取数据
     */
    public void getData();
}
