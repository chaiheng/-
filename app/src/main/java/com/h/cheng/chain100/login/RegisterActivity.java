package com.h.cheng.chain100.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.utils.CheckUtils;
import com.h.cheng.chain100.utils.CountDownTimerUtils;
import com.h.cheng.chain100.utils.EmojiInputFilter;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView, TextWatcher {

    @BindView(R.id.header_back)
    ImageView headerBack;
    @BindView(R.id.header_tv_text)
    TextView headerTvText;
    @BindView(R.id.et_phone)
    AutoCompleteTextView etPhone;
    @BindView(R.id.authcode)
    EditText authcode;
    @BindView(R.id.tv_sendauth)
    TextView tvSendauth;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.iv_eye)
    ImageView ivEye;
    @BindView(R.id.affirm_password)
    EditText affirmPassword;
    @BindView(R.id.iv_affirm_eye)
    ImageView ivAffirmEye;
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
        affirmPassword.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
        String str = "<font color='#999999'>已有账号？</font>&nbsp<font color='#222222'>立即登录</font>";
        tvLogin.setText(Html.fromHtml(str));
        tvRegister.getBackground().setAlpha(LoginActivity.Alpha_120);
        tvRegister.setEnabled(false);
        etPhone.addTextChangedListener(this);
        password.addTextChangedListener(this);
        authcode.addTextChangedListener(this);
        affirmPassword.addTextChangedListener(this);
        InvitationCode.addTextChangedListener(this);
    }


    @Override
    public void onRegisterSucc() {
        showtoast("注册成功");
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, true);
        hideLoading();
        startActivity(new Intent(this, PersonalDataActivity.class));
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
            showtoast("请输入正确的手机号码");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            showtoast("请输入6位验证码");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        } else if (!CheckUtils.validatePhonePass(affirmPassword.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        } else if (!affirmPassword.getText().toString().trim().equals(password.getText().toString())) {
            showtoast("两次输入密码不一致");
            return false;
        } else if (InvitationCode.getText().toString().length() == 0) {
            showtoast("请输入邀请码");
            return false;
        }
        return true;
    }

    public void CheckBtn() {
        if (etPhone.getText().toString().length() > 0 && password.getText().toString().length() > 0 && affirmPassword.getText().toString().length() > 0 && authcode.getText().toString().trim().length() > 0 && InvitationCode.getText().toString().length() > 0) {
            tvRegister.getBackground().setAlpha(LoginActivity.Alpha_255);
            tvRegister.setEnabled(true);
        } else {
            tvRegister.getBackground().setAlpha(LoginActivity.Alpha_120);
            tvRegister.setEnabled(false);
        }
    }


    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.iv_eye, R.id.iv_affirm_eye, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.tv_sendauth:
                if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
                    showtoast("请输入正确的手机号码");
                } else {
                    CountDownTimerUtils.startCount(tvSendauth);
                    presenter.SendMsg(etPhone.getText().toString());
                }
                break;
            case R.id.iv_eye:
                if (!flag) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                flag = !flag;
                password.postInvalidate();
                CharSequence text = password.getText();
                if (text != null) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;
            case R.id.iv_affirm_eye:
                if (!flag2) {
                    affirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    affirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                flag2 = !flag2;
                affirmPassword.postInvalidate();
                CharSequence text2 = affirmPassword.getText();
                if (text2 != null) {
                    Spannable spanText = (Spannable) text2;
                    Selection.setSelection(spanText, text2.length());
                }
                break;
            case R.id.tv_register:
                showLoading();
                presenter.regex(etPhone.getText().toString(), password.getText().toString(), authcode.getText().toString(), InvitationCode.getText().toString(), affirmPassword.getText().toString());
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        CheckBtn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

