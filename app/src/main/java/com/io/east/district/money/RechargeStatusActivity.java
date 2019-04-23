package com.io.east.district.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.event.ConnectionEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 保证金充值 成功 或者失败
 */
public class RechargeStatusActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_top_up_state)
    TextView tvTopUpState;
    @BindView(R.id.tv_marked_words)
    TextView tvMarkedWords;
    @BindView(R.id.tv_partnership)
    TextView tvPartnership;
    @BindView(R.id.bt_experience)
    Button btExperience;
    @BindView(R.id.rl_partner)
    RelativeLayout rlPartner;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_CNY)
    TextView tvCNY;
    @BindView(R.id.tv_condition)
    TextView tvCondition;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_transaction_number)
    TextView tvTransactionNumber;
    private int is_partner;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_status;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        boolean succeed = intent.getBooleanExtra("succeed", false);
        String money = intent.getStringExtra("money");
        String Trans_sn = intent.getStringExtra("Trans_sn");
        String total_money = intent.getStringExtra("total_money");
        String num = intent.getStringExtra("num");
        String amount = intent.getStringExtra("amount");
        String gift = intent.getStringExtra("gift");
        String time = intent.getStringExtra("time");
        is_partner = intent.getIntExtra("is_partner", 0);
        if (succeed) {
            rlPartner.setVisibility(View.VISIBLE);
            tvTopUpState.setText("充值成功");
            ivState.setImageResource(R.mipmap.win);
            tvMarkedWords.setText("平台赠送" +gift +"元已放入保证金");
            tvMoney.setText(money);
            tvNum.setText(num);
            tvCNY.setText("≈"+amount+"BTA");
            tvOrderTime.setText(time);
            if (is_partner == 1) {
                tvPartnership.setText("恭喜你成功续费合伙人");
            } else {
                tvPartnership.setText("恭喜你成为合伙人");
            }
            tvTotal.setText(total_money);
            tvCondition.setText("充值成功");
            tvTransactionNumber.setText(Trans_sn);


        } else {
            rlPartner.setVisibility(View.GONE);
            tvTopUpState.setText("交易取消");
            ivState.setImageResource(R.mipmap.failed);
            tvMarkedWords.setText("充值金额不足");
            tvMoney.setText(money);
            tvNum.setText(num);
            tvCNY.setText("≈"+amount+"BTA");
            tvOrderTime.setText(time);
            if (is_partner == 1) {
                tvPartnership.setText("恭喜你成功续费合伙人");
            } else {
                tvPartnership.setText("恭喜你成为合伙人");
            }
            tvTotal.setText(total_money);
            tvCondition.setText("交易取消");
            tvTransactionNumber.setText(Trans_sn);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        startMain();

    }
    private void startMain() {
        if (is_partner == 1) {
            startActivity(new Intent(this, MainActivity.class));
            EventBus.getDefault().post(new ConnectionEvent(true));
        } else {
            startActivity(new Intent(this, MainActivity.class).putExtra("isNo", true));
        }
    }
}
