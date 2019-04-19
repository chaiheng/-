package com.io.east.district.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;

import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.me.MyPerformanceActivity;
import com.io.east.district.me.TeamManagementActivity;
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

    public static ConnectionFragment newInstance() {
        ConnectionFragment fragment = new ConnectionFragment();
        return fragment;
    }

    @Override
    protected void init() {
        EasyHttp.get(UrlDeploy.people)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Log.d("ssss", "...." + e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("ssss", "...." + s);
                    }
                });
                /*.execute(new SimpleCallBack<BaseApiResult<PeopleBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(BaseApiResult<PeopleBean> peopleBeanBaseApiResult) {
                       PeopleBean data = peopleBeanBaseApiResult.getData();
                        String msg = peopleBeanBaseApiResult.getMsg();
                        Log.d("sss","..."+msg);
                    }
                });*/
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_dynamic;
    }

    @OnClick({R.id.bt_percent, R.id.bt_renew, R.id.cl_my_deposit,
            R.id.rl_my_performance, R.id.rl_team_management})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_percent:
                break;
            case R.id.bt_renew:
                break;
            case R.id.rl_my_performance:
                startActivity(new Intent(getActivity(), MyPerformanceActivity.class));
                break;
            case R.id.rl_team_management:
                startActivity(new Intent(getActivity(), TeamManagementActivity.class));
                break;
            case  R.id.cl_my_deposit:
                startActivity(new Intent(getActivity(), MyDepositActivity.class));
                break;
        }
    }


}
