package com.io.east.district.money;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.zjun.widget.RuleView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarginDepositActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_recharge_amount)
    TextView tvRechargeAmount;

    @BindView(R.id.tv_actual_amount)
    TextView tvActualAmount;
    @BindView(R.id.bt_go_prepaid)
    Button btGoPrepaid;
    @BindView(R.id.horizontalScale)
    RuleView horizontalScale;
    private Vibrator vibrator;

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
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        horizontalScale.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent horizontalScale) {
                if (MotionEvent.ACTION_MOVE==horizontalScale.getAction()){
                    vibrator.vibrate(500);
                }else {
                    vibrator.cancel();
                }
                return true;
            }
        });
        horizontalScale.setOnValueChangedListener(new RuleView.OnValueChangedListener() {
            @Override
            public void onValueChanged(float value) {
                BigDecimal bigDecimal  = new BigDecimal(value) ;

                String string = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toPlainString();


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
