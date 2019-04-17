package com.io.east.district.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.io.east.district.Constant;
import com.io.east.district.MyApplication;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.utils.CheckUtils;
import com.io.east.district.utils.EmojiInputFilter;
import com.io.east.district.utils.SharedPreferencesUtil;
import com.io.east.district.view.ClearEditText;
import com.io.east.district.view.PasswdEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

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
    @BindView(R.id.password)
    PasswdEditText password;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.test)
    LinearLayout test;
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
        etPhone.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(11)});
        password.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getInfo(Constant.LOGIN_PHONE))) {
            etPhone.setText(SharedPreferencesUtil.getInstance().getInfo(Constant.LOGIN_PHONE));
            etPhone.setSelection(etPhone.getText().length());
        }
    }


    @Override
    public void onLoginSucc(String nickname) {
        showtoast("登录成功");
        hideLoading();
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, true);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.LOGIN_PHONE, etPhone.getText().toString());
        startActivity(new Intent(this, MainActivity.class));
        finish();
        //退出登录清空
    }

    @Override
    public void onSendSucc() {
//        showtoast("验证码发送成功");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.header_back, R.id.tv_forget, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, FindPassWordActivity.class));
                //忘记密码
                break;
            case R.id.tv_login:
                //示例代码，示例接口
//                if (Check()) {
//                    showLoading();
//                    presenter.login(etPhone.getText().toString(), password.getText().toString(), "");
//                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                //注册
                break;
        }
    }

    public boolean Check() {
        if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
            showtoast("手机号码不存在");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            showtoast("密码必须是6-18位字母加数字组合");
            return false;
        }
        return true;
    }


}

