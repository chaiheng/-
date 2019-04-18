package com.io.east.district.me;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;

import com.io.east.district.R;
import com.io.east.district.adapter.NoticeAdapter;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
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
    private  List<String> mData  =new ArrayList<>();



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
                initData();
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
       /* String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.post(URLConfig.affiche)
                .params("local", "1")
                .params("token", token)
                .execute(new SimpleCallBack<String>() {

                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                       int code = proclamationBean.getCode();
                            if (code == 200) {
                                List<ProclamationBean.DataBean.ArticlesBean> articles = proclamationBean.getData().getArticles();
                                for (int i = 0; i < articles.size(); i++) {
                                    valueBeanList = articles.get(i).getValue();
                                    noticeAdapter.setNewData(valueBeanList);
                                }

                                if (valueBeanList.isEmpty()) {
                                    noticeAdapter.setEmptyView(new BaseEmptyView(NoticeActivity.this));
                                }
                                noticeAdapter.notifyDataSetChanged();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/


    }



}
