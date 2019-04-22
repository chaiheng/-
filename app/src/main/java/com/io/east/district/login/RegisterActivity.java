package com.io.east.district.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.MyApplication;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.bean.RegisterBean;
import com.io.east.district.utils.CheckUtils;
import com.io.east.district.utils.EmojiInputFilter;
import com.io.east.district.view.ClearEditText;
import com.io.east.district.view.PasswdEditText;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class RegisterActivity extends BaseActivity {

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
    private Disposable disposable;

    @Override
    protected RegisterPresenter createPresenter() {
        return null;
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


//        showtoast("注册成功");
//        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, true);
//        hideLoading();
//        startActivity(new Intent(this, MainActivity.class));


//        showtoast("验证码发送成功");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public boolean Check() {
        if (TextUtils.isEmpty(etPhone.getText().toString())){
            ToastUtils.show("请输入手机号");
            return false;
        } else if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
            ToastUtils.show("手机号应是11位数");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            ToastUtils.show("请输入6位验证码");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            ToastUtils.show("密码必须是6-18位字母加数字组合");
            return false;
        } else if (InvitationCode.getText().toString().length() == 0) {
            ToastUtils.show("请输入邀请码");
            return false;
        }
        return true;
    }


    @OnClick({R.id.header_back, R.id.tv_sendauth, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                if (disposable != null) {
                    disposable.dispose();
                }
                finish();
                break;
            case R.id.tv_sendauth:
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtils.show("请输入手机号");

                }else  if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
                    ToastUtils.show("请输入正确手机号");
                } else {

                    EasyHttp.get(UrlDeploy.verification_code)
                            .params("mobile", etPhone.getText().toString())
                            .params("reset", "")
                            .execute(new SimpleCallBack<String>() {

                                @Override
                                public void onError(ApiException e) {
                                    Log.d("eee", e.getMessage());
                                }

                                @Override
                                public void onSuccess(String s) {
                                    JSONObject jsonObject = JSON.parseObject(s);
                                    int code = jsonObject.getIntValue("code");
                                    String msg = jsonObject.getString("msg");
                                    if (code == 1) {
                                        ToastUtils.show("验证码发送成功");
                                        startCountDownTime();

                                    } else {
                                        ToastUtils.show(msg);
                                    }


                                }
                            });

//                    presenter.SendMsg(etPhone.getText().toString());
                }
                break;
            case R.id.tv_register:
                if (Check()) {
//                    showLoading();
                    EasyHttp.post(UrlDeploy.register)
                            .params("mobile", etPhone.getText().toString())
                            .params("password", Objects.requireNonNull(password.getText()).toString())
                            .params("invitation_code", "000F")
                            .params("verification_code", authcode.getText().toString())
                            .params("device_type", "android")
                            .execute(new SimpleCallBack<String>() {

                                @Override
                                public void onError(ApiException e) {

                                }

                                @Override
                                public void onSuccess(String s) {
                                    if (!TextUtils.isEmpty(s)) {
                                        JSONObject jsonObject = JSON.parseObject(s);
                                        int code = jsonObject.getIntValue("code");
                                        String msg = jsonObject.getString("msg");

                                        if (code == 1) {
                                            RegisterBean registerBean = JSON.parseObject(s, RegisterBean.class);
                                            ToastUtils.show("注册成功");
                                            SPUtils.getInstance("login").put("token", registerBean.getData().getToken());
                                            SPUtils.getInstance("invitation_code").put("invitation_code", registerBean.getData().getInvitation_code());
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        } else {
                                            ToastUtils.show(msg);
                                        }
                                    }

                                }
                            });
                }
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }


    private void startCountDownTime() {


        disposable = Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)

                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        Log.d(TAG, "倒计时");
                        tvSendauth.setEnabled(false);
                        tvSendauth.setTextColor(
                                AppCompatResources.getColorStateList(RegisterActivity.this, R.color.hint_color));
                        tvSendauth.setText((60 - aLong) + "秒可重新发送");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "倒计时完毕");
                        tvSendauth.setEnabled(true);
                        tvSendauth.setTextColor(
                                AppCompatResources.getColorStateList(RegisterActivity.this, R.color.color_333));
                        tvSendauth.setText("获取验证码");
                    }
                })
                .subscribe();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }

    }
}

