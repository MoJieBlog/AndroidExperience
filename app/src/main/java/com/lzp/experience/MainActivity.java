package com.lzp.experience;

import android.os.Bundle;
import android.view.View;

import com.lzp.base.component.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title:
                break;
            case R.id.tv_right:
                break;
            case R.id.iv_left_icon:
                break;
        }
    }
}