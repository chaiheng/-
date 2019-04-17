package com.io.east.district.me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

/**
 *   我的业绩
 */
public class MyPerformanceActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_performance;
    }


}
