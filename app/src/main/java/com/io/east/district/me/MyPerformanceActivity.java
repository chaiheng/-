package com.io.east.district.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.PerformanceBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的业绩
 */
public class MyPerformanceActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_subsidiary)
    TextView tvSubsidiary;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_monthly_income)
    TextView tvMonthlyIncome;
    @BindView(R.id.tv_have_income)
    TextView tvHaveIncome;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_daily_earnings)
    TextView tvDailyEarnings;
    @BindView(R.id.tv_week_earnings)
    TextView tvWeekEarnings;
    @BindView(R.id.tv_monthly_earnings)
    TextView tvMonthlyEarnings;
    @BindView(R.id.rl_total)
    RelativeLayout rlTotal;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_performance;
    }

    @Override
    public void initData() {
        super.initData();
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.performance)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        PerformanceBean performanceBean = JSON.parseObject(s, PerformanceBean.class);
                        String msg = performanceBean.getMsg();
                        int code = performanceBean.getCode();
                        if (code == 1) {
                            int month_received_sum = performanceBean.getData().getMonth_received_sum();
//                            tvMonthlyIncome.setText(""+month_received_sum);
                            int Total_sum = performanceBean.getData().getTotal_sum();
                            tvSum.setText("" + Total_sum);
                            int month_sum = performanceBean.getData().getMonth_sum();
                            int month_wait_sum = performanceBean.getData().getMonth_wait_sum();
                            int week_sum = performanceBean.getData().getWeek_sum();
                            int today_sum = performanceBean.getData().getToday_sum();
                            tvHaveIncome.setText("" + month_received_sum);
                            tvWeekEarnings.setText("" + week_sum);
                            tvDailyEarnings.setText("" + today_sum);
                            tvIncome.setText("" + month_wait_sum);
                            tvMonthlyIncome.setText("" + month_sum);
                            tvMonthlyEarnings.setText("" + month_sum);
                        }
                    }
                });
    }


    @OnClick({R.id.iv_go_back,R.id.rl_total})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.rl_total:

                startActivity(new Intent(this,TotalPerformanceActivity.class));
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
