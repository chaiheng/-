package com.h.cheng.chain100.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.activity.MainActivity;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.utils.CheckUtils;
import com.h.cheng.chain100.utils.CountDownTimerUtils;
import com.h.cheng.chain100.utils.EmojiInputFilter;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;
import com.h.cheng.chain100.view.ClearEditText;
import com.h.cheng.chain100.view.PasswdEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.header_back)
    ImageView headerBack;
    @BindView(R.id.header_tv_text)
    TextView headerTvText;
    @BindView(R.id.header_right_img)
    ImageView headerRightImg;
    @BindView(R.id.header_right_text)
    TextView headerRightText;
    @BindView(R.id.rl_header_50)
    RelativeLayout rlHeader50;
    @BindView(R.id.fr_header)
    FrameLayout frHeader;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.authcode)
    EditText authcode;
    @BindView(R.id.tv_sendauth)
    TextView tvSendauth;
    @BindView(R.id.password)
    PasswdEditText password;
    @BindView(R.id.Invitation_code)
    EditText InvitationCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private boolean flag = false;
    private boolean flag2 = false;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        headerTvText.setText("注册");
        etPhone.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(11)});
        password.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
    }


    @Override
    public void onRegisterSucc() {
        showtoast("注册成功");
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, true);
        hideLoading();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onSendSucc() {
        showtoast("验证码发送成功");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public boolean Check() {
        if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
            showtoast("手机号应是11位数");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            showtoast("请输入6位验证码");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        } else if (InvitationCode.getText().toString().length() == 0) {
            showtoast("请输入邀请码");
            return false;
        }
        return true;
    }


    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.tv_sendauth:
                if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
                    showtoast("手机号码不存在");
                } else {
                    CountDownTimerUtils.startCount(tvSendauth);
                    presenter.SendMsg(etPhone.getText().toString());
                }
                break;
            case R.id.tv_register:
                if (Check()) {
                    showLoading();
                    presenter.regex(etPhone.getText().toString(), password.getText().toString(), authcode.getText().toString(), InvitationCode.getText().toString(), "");
                }
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

}

