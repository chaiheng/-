package com.h.cheng.chain100.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.h.cheng.chain100.view.StateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, TextWatcher {

    @BindView(R.id.header_back)
    ImageView headerBack;
    @BindView(R.id.header_tv_text)
    TextView headerTvText;
    @BindView(R.id.et_phone)
    AutoCompleteTextView et_phone;
    @BindView(R.id.authcode)
    EditText authcode;
    @BindView(R.id.tv_sendauth)
    TextView tvSendauth;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.iv_eye)
    ImageView ivEye;
    @BindView(R.id.test)
    LinearLayout test;
    private boolean flag = false;
    public static int Alpha_120 = 120;
    public static int Alpha_255 = 255;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        headerTvText.setText("登录");
        et_phone.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(11)});
        password.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
        String str = "<font color='#999999'>没有账号？</font>&nbsp<font color='#222222'>立即注册</font>";
        tvRegister.setText(Html.fromHtml(str));
        tvLogin.getBackground().setAlpha(Alpha_120);
        tvLogin.setEnabled(false);
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getInfo(Constant.LOGIN_PHONE))) {
            et_phone.setText(SharedPreferencesUtil.getInstance().getInfo(Constant.LOGIN_PHONE));
            et_phone.setSelection(et_phone.getText().length());
        }
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckBtn();
                if (s.length() == 11) {
                    SharedPreferencesUtil.getInstance().saveInfo(Constant.LOGIN_PHONE, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(this);
        authcode.addTextChangedListener(this);
    }


    @Override
    public void onLoginSucc(String nickname) {
        showtoast("登录成功");
        hideLoading();
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, true);
        if (nickname.equals("0")) {
            startActivity(new Intent(this, PersonalDataActivity.class));
        } else {
            SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_SETUSER, true);
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
        //退出登录清空
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

    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.tv_forget, R.id.tv_login, R.id.tv_register, R.id.iv_eye})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.header_back:
                finish();
                break;
            case R.id.tv_sendauth:
                if (!CheckUtils.validatePhone(et_phone.getText().toString())) {
                    showtoast("请输入正确的手机号码");
                } else {
                    CountDownTimerUtils.startCount(tvSendauth);
                    presenter.SendMsg(et_phone.getText().toString());
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, FindPassWordActivity.class));
                //忘记密码
                break;
            case R.id.tv_login:
                //示例代码，示例接口
                if (Check()) {
                    showLoading();
                    presenter.login(et_phone.getText().toString(), password.getText().toString(), authcode.getText().toString());
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                //注册
                break;
        }
    }

    public boolean Check() {
        if (!CheckUtils.validatePhone(et_phone.getText().toString())) {
            showtoast("请输入正确的手机号码");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            showtoast("请输入6位验证码");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        }
        return true;
    }

    public void CheckBtn() {
        if (et_phone.getText().toString().length() > 0 && password.getText().toString().length() > 0 && authcode.getText().toString().trim().length() > 0) {
            tvLogin.getBackground().setAlpha(Alpha_255);
            tvLogin.setEnabled(true);
        } else {
            tvLogin.getBackground().setAlpha(Alpha_120);
            tvLogin.setEnabled(false);
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

