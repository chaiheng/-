package com.io.east.district.money;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.adapter.RecordAdapter;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BaseEmptyView;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.RechargeRecordBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeRecordActivity extends BaseActivity {

    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.sr_recharge)
    SmartRefreshLayout srRecharge;
    private List<String> mData = new ArrayList<>();
    private RecordAdapter recordAdapter;
    private List<RechargeRecordBean.DataBean> data;
    private  int page=1;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_record;
    }





    @Override
    public void initData() {
        super.initData();
        getData();

    }
    private   void getData(){
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.rechargeRecord)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .params("list_rows","10")
                .params("page",""+page)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        RechargeRecordBean rechargeRecordBean = JSON.parseObject(s, RechargeRecordBean.class);
                        srRecharge.finishRefresh();
                        srRecharge.finishLoadMore();

                        if (rechargeRecordBean.getCode()==1){

                            if (page==1){
                                data = rechargeRecordBean.getData();
                                recordAdapter.setNewData(data);
                                recordAdapter.notifyDataSetChanged();
                            }else {
                                recordAdapter.addData(data);
                                recordAdapter.notifyDataSetChanged();
                            }

                            ++page;

                        }


                    }
                });
    }
    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecord.setLayoutManager(layoutManager);
        recordAdapter = new RecordAdapter(R.layout.item_record, data);
        rvRecord.setAdapter(recordAdapter);
        srRecharge.autoRefresh();
        recordAdapter.setEmptyView(new BaseEmptyView(this));
        srRecharge.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                getData();
            }
        });
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }
}
