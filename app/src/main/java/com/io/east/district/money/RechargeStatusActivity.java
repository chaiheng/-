package com.io.east.district.money;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

/**
 *    保证金充值 成功 或者失败
 */
public class RechargeStatusActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_status;
    }


}
