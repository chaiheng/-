package com.io.east.district.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.bean.PartnerBean;
import com.io.east.district.me.MyPerformanceActivity;
import com.io.east.district.me.TeamManagementActivity;
import com.io.east.district.money.MarginDepositActivity;
import com.io.east.district.money.MyDepositActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 人脉
 */
public class ConnectionFragment extends BaseFragment {

    @BindView(R.id.tv_figure)
    TextView tvFigure;
    @BindView(R.id.bt_percent)
    Button btPercent;
    @BindView(R.id.clp_progress)
    ContentLoadingProgressBar clpProgress;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.bt_renew)
    Button btRenew;
    @BindView(R.id.rl_my_performance)
    RelativeLayout rlMyPerformance;
    @BindView(R.id.tv_day_earnings)
    TextView tvDayEarnings;
    @BindView(R.id.tv_week_earnings)
    TextView tvWeekEarnings;
    @BindView(R.id.tv_month_earnings)
    TextView tvMonthEarnings;
    @BindView(R.id.rl_team_management)
    RelativeLayout rlTeamManagement;
    @BindView(R.id.tv_group_number)
    TextView tvGroupNumber;
    @BindView(R.id.tv_total_sales)
    TextView tvTotalSales;
    @BindView(R.id.cl_my_deposit)
    ConstraintLayout clMyDeposit;
    @BindView(R.id.rl_renew)
    RelativeLayout rlRenew;
    @BindView(R.id.tv_beat)
    TextView tvBeat;
    private int is_partner;

    public static ConnectionFragment newInstance() {
        ConnectionFragment fragment = new ConnectionFragment();
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        is_partner = ((MainActivity) context).is_partner;
    }

    @Override
    protected void init() {

        if (is_partner == 1) {
//               续费
            rlRenew.setVisibility(View.GONE);
        } else {
//               没有续费
            rlRenew.setVisibility(View.VISIBLE);
        }

        String token = SPUtils.getInstance("login").getString("token");


       /* EasyHttp.get(UrlDeploy.mydeposit)
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
//                            int release_money = myDepositBean.getData().getRelease_money();
                            int dilution_progress = myDepositBean.getData().getDilution_progress();
                            tvFigure.setText("" + deposit_money);
                            btPercent.setText(dilution_progress + "%");
                            clpProgress.setProgress(dilution_progress);

                        }
                    }
                });*/


        EasyHttp.get(UrlDeploy.people)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Log.d("ssss", "...." + e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("renmai", "...." + s);

                        PartnerBean partnerBean = JSON.parseObject(s, PartnerBean.class);
                        if (partnerBean.getData() != null) {
//                            合伙人
                            int renewal_partner = partnerBean.getData().getRenewal_partner();
                            if (renewal_partner == 1) {
//               续费
                                rlRenew.setVisibility(View.GONE);
                            } else {
//               没有续费
                                rlRenew.setVisibility(View.VISIBLE);
                            }
                            int group_count = partnerBean.getData().getGroup_count();
                            int beat = partnerBean.getData().getBeat();

                            int today_sum = partnerBean.getData().getToday_sum();
                            int total_sum = partnerBean.getData().getTotal_sum();
                            int month_sum = partnerBean.getData().getMonth_sum();
                            int week_sum = partnerBean.getData().getWeek_sum();
                            int deposit_money = partnerBean.getData().getDeposit_money();
//                            int release_money = myDepositBean.getData().getRelease_money();
                            int dilution_progress = partnerBean.getData().getDilution_progress();
                            tvDayEarnings.setText("" + today_sum);
                            tvMonthEarnings.setText("" + month_sum);
                            tvWeekEarnings.setText("" + week_sum);
                            tvFigure.setText("" + deposit_money);
                            btPercent.setText(dilution_progress + "%");
                            clpProgress.setProgress(dilution_progress);
                            tvGroupNumber.setText("" + group_count);
                            tvBeat.setText("击败"+beat+"%合伙人");
                            tvTotalSales.setText(""+total_sum);

                        }

                    }
                });


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_dynamic;
    }

    @OnClick({R.id.bt_percent, R.id.bt_renew, R.id.cl_my_deposit,
            R.id.rl_my_performance, R.id.rl_team_management})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_renew:
                startActivity(new Intent(getActivity(), MarginDepositActivity.class));
                break;
            case R.id.rl_my_performance:
                startActivity(new Intent(getActivity(), MyPerformanceActivity.class));
                break;
            case R.id.rl_team_management:
                startActivity(new Intent(getActivity(), TeamManagementActivity.class));
                break;
            case R.id.cl_my_deposit:
                startActivity(new Intent(getActivity(), MyDepositActivity.class));
                break;
        }
    }


}
