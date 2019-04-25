package com.io.east.district.money;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.adapter.AssetAdapter;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BaseEmptyView;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.AssetsListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.widget.WheelView;


/**
 * 资产管理
 */
public class AssetsManagementActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_general_assets)
    TextView tvGeneralAssets;
    @BindView(R.id.bt_withdraw_deposit)
    Button btWithdrawDeposit;
    @BindView(R.id.tv_usable)
    TextView tvUsable;
    @BindView(R.id.tv_route)
    TextView tvRoute;
    @BindView(R.id.bt_select_time)
    Button btSelectTime;

    @BindView(R.id.rv_asset)
    RecyclerView rvAsset;
    @BindView(R.id.srl_asset)
    SmartRefreshLayout srlAsset;
    private List<AssetsListBean.DataBeanX.DataBean> mData = new ArrayList<>();
    private AssetAdapter assetAdapter;
    private int page = 1;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_management;
    }

    @Override
    public void initData() {
        super.initData();

        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        String freeze_money = intent.getStringExtra("freeze_money");
        String total_assets = intent.getStringExtra("total_assets");
        tvGeneralAssets.setText(total_assets);
        tvUsable.setText(money);
        tvRoute.setText(freeze_money);
        getData("2019-4");

    }


    private void getData(String time) {
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.assetflow)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .params("list_rows", "10")
                .params("page", "" + page)
                .params("time", time)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        srlAsset.finishRefresh();
                        srlAsset.finishLoadMore();
                    }

                    @Override
                    public void onSuccess(String s) {
                        srlAsset.finishRefresh();
                        srlAsset.finishLoadMore();
                        AssetsListBean assetsBean = JSON.parseObject(s, AssetsListBean.class);
                        if (assetsBean.getCode() == 1) {
                            if (page == 1) {
                                List<AssetsListBean.DataBeanX.DataBean> data = assetsBean.getData().getData();
                                assetAdapter.setNewData(data);
                            } else {
                                List<AssetsListBean.DataBeanX.DataBean> data = assetsBean.getData().getData();
                                assetAdapter.addData(data);
                            }
                            page++;
                        }
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvAsset.setLayoutManager(layoutManager);
        assetAdapter = new AssetAdapter(R.layout.item_asset, mData);
        assetAdapter.setEmptyView(new BaseEmptyView(this));
        rvAsset.setAdapter(assetAdapter);
        srlAsset.autoRefresh();
        srlAsset.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Calendar now = Calendar.getInstance();
                String y = String.valueOf(now.get(Calendar.YEAR));
                String m = String.valueOf(now.get((Calendar.MONTH) + 1));

                getData(y + "-" + m);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Calendar now = Calendar.getInstance();
                String y = String.valueOf(now.get(Calendar.YEAR));
                String m = String.valueOf(now.get((Calendar.MONTH) + 1));
                page = 1;
                getData(y + "-" + m);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.iv_go_back, R.id.bt_withdraw_deposit, R.id.bt_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_withdraw_deposit:
                ToastUtils.show("敬请期待");
                break;
            case R.id.bt_select_time:

                int screenWidth = ScreenUtils.getScreenWidth();

                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
                picker.setGravity(Gravity.BOTTOM);
                picker.setRangeStart(2019, 3, 14);
                picker.setLabel("", "", "");
                picker.setWidth(screenWidth);
                picker.setUseWeight(true);

                picker.setBackgroundColor(getResources().getColor(R.color.white));
                picker.setCancelText("取消");
                picker.setCancelTextColor(getResources().getColor(R.color.hint_color));
                picker.setCancelTextSize(16);
                picker.setTopPadding(30);
                picker.setSubmitText("确定");
                picker.setTextSize(20);
                picker.setSubmitTextSize(16);
                picker.setCancelTextColor(getResources().getColor(R.color.hint_color));
                picker.setSubmitTextColor(getResources().getColor(R.color.color_333));
                picker.setDividerColor(getResources().getColor(R.color.line));
                picker.setTextColor(getResources().getColor(R.color.black));
                picker.setTopLineVisible(true);

                picker.setTopHeight(55);
                picker.setTopLineColor(getResources().getColor(R.color.line));
                picker.setCycleDisable(false);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
                picker.setRangeEnd(2030, 11, 11);
                picker.setSelectedItem(2019, 6);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
//                        showToast(year + "-" + month);
                        btSelectTime.setText(year + "-" + month);

                        getData(year + "-" + month);
                    }
                });
                picker.show();
                break;
        }
    }


}
