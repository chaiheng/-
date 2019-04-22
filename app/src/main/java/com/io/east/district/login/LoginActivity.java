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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.Constant;
import com.io.east.district.MyApplication;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.bean.RegisterBean;
import com.io.east.district.utils.CheckUtils;
import com.io.east.district.utils.EmojiInputFilter;
import com.io.east.district.utils.SharedPreferencesUtil;
import com.io.east.district.view.ClearEditText;
import com.io.east.district.view.PasswdEditText;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

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
        return null;
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
                if (Check()) {
                    EasyHttp.post(UrlDeploy.login)
                            .params("mobile", etPhone.getText().toString().trim())
                            .params("password", password.getText().toString().trim())
                            .params("device_type", "android")
                            .execute(new SimpleCallBack<String>() {
                                @Override
                                public void onError(ApiException e) {
                                        ToastUtils.show("错误"+e.getMessage());
                                }



                                @Override
                                public void onSuccess(String s) {
                                    if (!TextUtils.isEmpty(s)){
                                        JSONObject jsonObject  = JSON.parseObject(s);
                                        int code = jsonObject.getIntValue("code");
                                        String msg = jsonObject.getString("msg");
                                        if (code==1){

                                            RegisterBean registerBean = JSON.parseObject(s, RegisterBean.class);
                                            SPUtils.getInstance("login").put("token", registerBean.getData().getToken());
                                            SPUtils.getInstance("invitation_code").put("invitation_code", registerBean.getData().getInvitation_code());
                                            ToastUtils.show("登录成功");
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        }else {
                                            ToastUtils.show(msg);
                                        }
                                    }



                                }


                            });

                }

                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                //注册
                break;
        }
    }

    public boolean Check() {
        if (TextUtils.isEmpty(etPhone.getText().toString())){
            ToastUtils.show("请输入手机号");
        }
        if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
            ToastUtils.show("请输入正确手机号");
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())){
            ToastUtils.show("请输入密码");
            return false;
        }
        if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            ToastUtils.show("密码必须是6-18位字母加数字组合");
            return false;
        }

        return true;
    }


}

