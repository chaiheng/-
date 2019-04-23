package com.io.east.district.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.io.east.district.R;
import com.io.east.district.adapter.StatisticsAdapter;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.GroupManageBean;
import com.io.east.district.view.CircleImageView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 小组管理
 */
public class TeamManagementActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.iv_portrait)
    CircleImageView ivPortrait;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_group_number)
    TextView tvGroupNumber;
    @BindView(R.id.tv_partner_number)
    TextView tvPartnerNumber;
    @BindView(R.id.rv_statistics)
    RecyclerView rvStatistics;
    private List<GroupManageBean.DataBeanX.DataBean>  mData  =  new ArrayList<>();
    private StatisticsAdapter statisticsAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_management;
    }

    @Override
    public void initData() {
        super.initData();
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.groupManage)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        GroupManageBean groupManageBean = JSON.parseObject(s, GroupManageBean.class);
                        if (groupManageBean.getCode()==1){
                           mData = groupManageBean.getData().getData();
                            statisticsAdapter.setNewData(mData);
                            statisticsAdapter.notifyDataSetChanged();
                            GroupManageBean.DataBeanX data = groupManageBean.getData();
                            String avatar = data.getMygroup().getAvatar();
                            Glide.with(TeamManagementActivity.this).load(avatar).into(ivPortrait);
                            String nickname = data.getMygroup().getNickname();
                            double totalSum = data.getMygroup().getTotal_sum();
                            int total_group = data.getMygroup().getTotal_group();
                            int total_partner = data.getMygroup().getTotal_partner();
                            tvGroupNumber.setText(""+total_group);
                            tvSalesVolume.setText(""+totalSum);
                            tvPhone.setText(nickname);
                            tvPartnerNumber.setText(""+total_partner);

                        }
                    }
                });

    }
    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvStatistics.setLayoutManager(layoutManager);
        statisticsAdapter = new StatisticsAdapter(R.layout.item_team,mData);
        rvStatistics.setAdapter(statisticsAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }
}
