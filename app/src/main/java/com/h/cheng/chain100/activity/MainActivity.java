package com.h.cheng.chain100.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.TextView;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.downfile.FilePresenter;
import com.h.cheng.chain100.login.LoginActivity;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.messageView)
    TextView messageView;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;


    @Override
    protected FilePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    public void initView() {
        MyApplication.getInstance().exit();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
            }
        });

        BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);
        nearby.setBadgeCount(5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.messageView)
    public void onViewClicked() {
        //退出登录
        startActivity(new Intent(this, LoginActivity.class));
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_SETUSER, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.API_TOKEN, "");
        finish();
    }
}
