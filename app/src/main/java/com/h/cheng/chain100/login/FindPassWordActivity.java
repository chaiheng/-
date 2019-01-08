package com.h.cheng.chain100.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.bean.FindPassBean;
import com.h.cheng.chain100.utils.CheckUtils;
import com.h.cheng.chain100.utils.CountDownTimerUtils;
import com.h.cheng.chain100.utils.EmojiInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FindPassWordActivity extends BaseActivity<FindPassPresenter> implements FindPassView {

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
    @BindView(R.id.tv_findpass)
    TextView tvFindpass;
    private boolean flag = false;
    private boolean flag2 = false;

    @Override
    protected FindPassPresenter createPresenter() {
        return new FindPassPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_findpass;
    }

    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        headerTvText.setText("找回密码");
        etPhone.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(11)});
        password.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
        affirmPassword.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
        tvFindpass.getBackground().setAlpha(LoginActivity.Alpha_120);
        tvFindpass.setEnabled(false);
        etPhone.addTextChangedListener(new TextWatcher() {
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
        });
        password.addTextChangedListener(new TextWatcher() {
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
        });
        authcode.addTextChangedListener(new TextWatcher() {
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
        });
        affirmPassword.addTextChangedListener(new TextWatcher() {
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
        });
    }


    @Override
    public void onFindSucc() {
        showtoast("找回密码成功");
        hideLoading();
        startActivity(new Intent(this, LoginActivity.class));
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
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            showtoast("请输入6位验证码");
            return false;
        } else if (!CheckUtils.validatePhonePass(affirmPassword.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        } else if (!affirmPassword.getText().toString().trim().equals(password.getText().toString())) {
            showtoast("两次输入密码不一致");
            return false;
        }
        return true;
    }

    public void CheckBtn() {
        if (CheckUtils.validatePhone(etPhone.getText().toString()) && CheckUtils.validatePhonePass(password.getText().toString()) && CheckUtils.validatePhonePass(affirmPassword.getText().toString()) && authcode.getText().toString().trim().length() == 6) {
            tvFindpass.getBackground().setAlpha(LoginActivity.Alpha_255);
            tvFindpass.setEnabled(true);
        } else {
            tvFindpass.getBackground().setAlpha(LoginActivity.Alpha_120);
            tvFindpass.setEnabled(false);
        }
    }


    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.iv_eye, R.id.iv_affirm_eye, R.id.tv_findpass})
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
            case R.id.tv_findpass:
                if (Check()) {
                    showLoading();
                    FindPassBean findPassBean = new FindPassBean(etPhone.getText().toString(), password.getText().toString(), authcode.getText().toString());
                    presenter.findpass(findPassBean);
                }
                break;
        }
    }

}

