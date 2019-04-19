package com.io.east.district.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.east.district.R;
import com.io.east.district.adapter.StatisticsAdapter;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.view.CircleImageView;

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
    private List<String>  mData  =  new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_management;
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        LinearLayoutManager  layoutManager  = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvStatistics.setLayoutManager(layoutManager);
        StatisticsAdapter  statisticsAdapter  = new StatisticsAdapter(R.layout.item_team,mData);
        rvStatistics.setAdapter(statisticsAdapter);
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
    }
}
