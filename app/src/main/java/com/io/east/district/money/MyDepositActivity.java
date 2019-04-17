package com.io.east.district.money;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.io.east.district.R;
import com.io.east.district.view.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的保证金
 */
public class MyDepositActivity extends AppCompatActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.pv_progress)
    ProgressView pvProgress;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_recharge_record)
    TextView tvRechargeRecord;
    @BindView(R.id.tv_consumption)
    TextView tvConsumption;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.bt_renewal_partner)
    Button btRenewalPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deposit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_go_back, R.id.tv_detail, R.id.bt_renewal_partner,R.id.tv_recharge_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                break;
            case R.id.tv_detail:
                break;
            case R.id.bt_renewal_partner:

                break;
            case  R.id.tv_recharge_record:
                break;
        }
    }
}
