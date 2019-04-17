package com.io.east.district.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.io.east.district.MyApplication;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.bean.FindPassBean;
import com.io.east.district.utils.CheckUtils;
import com.io.east.district.utils.CountDownTimerUtils;
import com.io.east.district.utils.EmojiInputFilter;
import com.io.east.district.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FindPassWordActivity extends BaseActivity<FindPassPresenter> implements FindPassView {

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
    EditText password;
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
        }
        return true;
    }


    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.tv_findpass})
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

