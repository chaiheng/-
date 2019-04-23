package com.io.east.district.me;


import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.adapter.NoticeAdapter;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BaseEmptyView;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.NoticeBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 公告
 *
 * @author Administrator
 */
@SuppressLint("Registered")
public class NoticeActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rv_proclamation)
    RecyclerView rvProclamation;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private NoticeAdapter noticeAdapter;
    private List<NoticeBean.DataBean> mData = new ArrayList<>();

    private int page = 1;

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }


    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        super.initView();
        noticeAdapter = new NoticeAdapter(R.layout.item_notice, mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProclamation.setLayoutManager(layoutManager);
        rvProclamation.setHasFixedSize(true);
        rvProclamation.setAdapter(noticeAdapter);

        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               getData();
                mSmartRefreshLayout.finishRefresh();
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    public void initData() {
        super.initData();


    }

    private void getData() {
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.notice)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .params("list_rows", "10")
                .params("page", ""+page)
                .execute(new SimpleCallBack<String>() {

                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            NoticeBean noticeBean = JSON.parseObject(s, NoticeBean.class);
                            int code = noticeBean.getCode();
                            if (code == 1) {
                                List<NoticeBean.DataBean> data = noticeBean.getData();


                                    noticeAdapter.setNewData(data);


                                if (data.isEmpty()) {
                                    noticeAdapter.setEmptyView(new BaseEmptyView(NoticeActivity.this));
                                }
                                noticeAdapter.notifyDataSetChanged();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
