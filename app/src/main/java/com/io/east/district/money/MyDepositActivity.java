package com.io.east.district.money;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.MyDepositBean;
import com.io.east.district.view.ProgressView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的保证金
 */
public class MyDepositActivity extends BaseActivity {

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
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_deposit;
    }


    @Override
    public void initData() {
        super.initData();

        String token = SPUtils.getInstance("login").getString("token");


        EasyHttp.get(UrlDeploy.mydeposit)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("bao", "...." + s);
                        MyDepositBean myDepositBean = JSON.parseObject(s, MyDepositBean.class);
                        if (myDepositBean.getCode() == 1) {
                            int deposit_money = myDepositBean.getData().getDeposit_money();
                            int total_money = myDepositBean.getData().getTotal_money();
                           int release_money = myDepositBean.getData().getRelease_money();
                            int dilution_progress = myDepositBean.getData().getDilution_progress();
                            tvAmount.setText("" + deposit_money);
                            tvConsumption.setText(""+release_money);
                            tvTotalMoney.setText(""+total_money);
                            pvProgress.setProgress(dilution_progress);

                        }
                    }
                });

    }

    @Override
    public void initView() {
        super.initView();
    }

    @OnClick({R.id.iv_go_back, R.id.tv_detail, R.id.bt_renewal_partner,R.id.tv_recharge_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.tv_detail:
                break;
            case R.id.bt_renewal_partner:
                startActivity(new Intent(this,MyDepositActivity.class));
                break;
            case  R.id.tv_recharge_record:
                startActivity(new Intent(this,RechargeRecordActivity.class));
                break;
        }
    }
}
