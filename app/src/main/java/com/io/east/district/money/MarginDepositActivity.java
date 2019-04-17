package com.io.east.district.money;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.zhzh.rulerlib.RulerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarginDepositActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_recharge_amount)
    TextView tvRechargeAmount;
    @BindView(R.id.rulerView)
    RulerView rulerView;
    @BindView(R.id.tv_actual_amount)
    TextView tvActualAmount;
    @BindView(R.id.bt_go_prepaid)
    Button btGoPrepaid;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_margin_deposit;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        rulerView.scrollToValue(1);
        rulerView.setOnValueChangedListener(new RulerView.OnValueChangedListener() {
            @Override
            public void onValueChanged(int i) {
                tvRechargeAmount.setText(""+i*1000);
                tvActualAmount.setText(""+i*1000+100);
                rulerView.scrollToValue(i);
            }
        });
    }

    @OnClick({R.id.iv_go_back, R.id.bt_go_prepaid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_go_prepaid:
                break;
        }
    }
}
