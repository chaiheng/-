package com.io.east.district.money;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.PrepaidBean;
import com.io.east.district.event.ConnectionEvent;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 充值保证金确认
 */
public class ConfirmPrepaidActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.cv_count_down)
    CountdownView cvCountDown;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_convert)
    TextView tvConvert;

    @BindView(R.id.tv_give)
    TextView tvGive;
    @BindView(R.id.srl_deposit)
    SwipeRefreshLayout srlDeposit;
    private int countdownTime;
    private Disposable interval;
    private int is_partner;
    private int pay_status;
    private String recharge_id;
    private String money;
    private String trans_sn;
    private String time;
    private int num;
    private String amount;
    private int total_money;
    private int gift;
    private int status;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_prepaid;
    }

    @Override
    public void initView() {
        super.initView();
        interval = interval();

        if (countdownTime == 0) {
            cvCountDown.start(1800000);
        } else {
            cvCountDown.start(countdownTime * 10000);
        }
        for (int i = 0; i < 1000; i++) {
            cvCountDown.updateShow(i);
        }
        cvCountDown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {

                getData();
            }
        });
        srlDeposit.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络请求需要3000毫秒，请求完成，设置setRefreshing 为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srlDeposit.setRefreshing(false);
                        ToastUtils.show("刷新成功");
                        getData();
                    }
                }, 3000);

            }
        });
//        srlDeposit.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getData();
//            }
//        });
    }

    @Override
    public void initData() {
        super.initData();
        getData();
        recharge_id = getIntent().getStringExtra("recharge_id");

    }

    private void getData() {
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.cancelRecharge + recharge_id)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")

                .params("is_address", "")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        PrepaidBean prepaidBean = JSON.parseObject(s, PrepaidBean.class);

                        int code = prepaidBean.getCode();
                        if (code == 1) {

                            status = prepaidBean.getData().getStatus();
                            pay_status = prepaidBean.getData().getPay_status();
                            is_partner = prepaidBean.getData().getIs_partner();
                            countdownTime = prepaidBean.getData().getCountdown();
                            money = prepaidBean.getData().getMoney();
                            trans_sn = prepaidBean.getData().getTrans_sn();
                            tvMoney.setText(money);
                            amount = prepaidBean.getData().getAmount();


                            time = prepaidBean.getData().getCreate_time_text();
                            num = prepaidBean.getData().getNum();
                            tvNum.setText("x" + num);
                            total_money = prepaidBean.getData().getTotal_money();
                            tvConvert.setText("¥" + total_money);
                            gift = prepaidBean.getData().getGift();
                            tvGive.setText("¥" + money);
                            tvTotal.setText("合计：" + amount + " " + "BTA");
                            if (status == 1 && pay_status == 0) {
//                                    交易确认中
                                tvStatus.setText("确认中");
                            } else if (status == 0 && pay_status == 0) {
//                                  交易取消
                                tvStatus.setText("取消交易");
                                startActivity(new Intent(ConfirmPrepaidActivity.this, RechargeStatusActivity.class)
                                        .putExtra("succeed", false)
                                        .putExtra("money", money)
                                        .putExtra("Trans_sn", trans_sn)
                                        .putExtra("total_money", total_money)
                                        .putExtra("amount", amount)
                                        .putExtra("num", "x" + num)
                                        .putExtra("gift", gift)
                                        .putExtra("time", time)
                                        .putExtra("is_partner", is_partner));
                            } else if (status == 1 && pay_status == 1) {
                                tvStatus.setText("交易成功");
//                                    交易成功
                                startActivity(new Intent(ConfirmPrepaidActivity.
                                        this, RechargeStatusActivity.class)
                                        .putExtra("succeed", true)
                                        .putExtra("money", money)
                                        .putExtra("Trans_sn", trans_sn)
                                        .putExtra("total_money", total_money)
                                        .putExtra("num", "x" + num)
                                        .putExtra("gift", gift)
                                        .putExtra("amount", amount)
                                        .putExtra("time", time)
                                        .putExtra("is_partner", is_partner)

                                );
                            }


                        }
                    }
                });
    }


    private Disposable interval() {
        return Flowable.interval(3, 3, TimeUnit.MINUTES)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getData();
                    }
                }).subscribe();
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
//        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (interval != null) {
            interval.dispose();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
