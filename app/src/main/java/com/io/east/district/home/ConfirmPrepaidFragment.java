package com.io.east.district.home;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.money.RechargeStatusActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 确认充值
 */
public class ConfirmPrepaidFragment extends BaseFragment {
    @BindView(R.id.srl_deposit)
    SmartRefreshLayout srlDeposit;
    @BindView(R.id.cv_count_down)
    CountdownView cvCountDown;
    @BindView(R.id.tv_status)
    TextView tvStatus;
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
    private int countdownTime;
    private Disposable interval;
    private int is_partner;
    private int pay_status;
    private int status;
    private String money;
    private String trans_sn;
    private String amount;
    private String time;
    private int num;
    private int total_money;
    private int gift;

    @Override
    protected void init() {
        interval = interval();
        srlDeposit.autoRefresh();
        cvCountDown.start(countdownTime * 1000);
        for (int i = 0; i < 1000; i++) {
            cvCountDown.updateShow(i);
        }
        cvCountDown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
//                   倒计时结束 如果状态成功跳转成功    如果失败取消跳转失败
                if (status == 1 && pay_status == 0) {
//                                    交易确认中
                    tvStatus.setText("确认中");
                } else if (status == 0 && pay_status == 0) {
//                                  交易取消
                    tvStatus.setText("取消交易");
                    startActivity(new Intent(getActivity(), RechargeStatusActivity.class)
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
                    startActivity(new Intent(getActivity(), RechargeStatusActivity.class)
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
        });
        srlDeposit.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
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

    private void getData() {
        String token = SPUtils.getInstance("login").getString("token");
        String recharge_id = SPUtils.getInstance("login").getString("recharge_id");
        Log.d("id","...."+recharge_id);
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
                        Log.d("cc1","...."+s);

                     /*   JSONObject jsonObject = JSON.parseObject(s);
                        int code = jsonObject.getIntValue("code");
                        if (code == 1 ) {
                            OrderInquiryBean prepaidBean = JSON.parseObject(s, OrderInquiryBean.class);
                            status = prepaidBean.getData().get(0).getStatus();
                            pay_status = prepaidBean.getData().get(0).getPay_status();
                            is_partner = prepaidBean.getData().get(0).getIs_partner();

                            countdownTime = prepaidBean.getData().getCountdown();
                            money = prepaidBean.getData().getMoney();
                            trans_sn = prepaidBean.getData().getTrans_sn();
                            tvMoney.setText(money);
                            amount = prepaidBean.getData().getAmount();
                            tvConvert.setText("≈" + amount + "BTA");

                            time = prepaidBean.getData().getCreate_time_text();
                            num = prepaidBean.getData().getNum();
                            tvNum.setText("x" + num);
                            total_money = prepaidBean.getData().getTotal_money();
                            gift = prepaidBean.getData().getGift();
                            tvGive.setText("¥" + money);
                            tvTotal.setText("合计：" + total_money);
                            if (status == 1 && pay_status == 0) {
//                                    交易确认中
                                tvStatus.setText("确认中");
                            } else if (status == 0 && pay_status == 0) {
//                                  交易取消
                                tvStatus.setText("取消交易");
                                startActivity(new Intent(getActivity(), RechargeStatusActivity.class)
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
                                startActivity(new Intent(getActivity(), RechargeStatusActivity.class)
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


                        }*/
                    }
                });


    }

    public static ConfirmPrepaidFragment newInstance() {
        ConfirmPrepaidFragment fragment = new ConfirmPrepaidFragment();
        return fragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.margin_deposit_fragment;
    }
}
