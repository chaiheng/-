package com.h.cheng.chain100.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.base.BasePresenter;
import com.h.cheng.chain100.statusbar.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAndRegsterActivity extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loginandregistration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        UtilsStyle.setStatusBarMode(this, false);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
