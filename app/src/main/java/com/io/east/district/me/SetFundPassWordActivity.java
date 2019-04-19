package com.io.east.district.me;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.blankj.utilcode.util.GsonUtils;

import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.utils.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;



/**
 * 设置交易密码
 *
 * @author Administrator
 */
public class SetFundPassWordActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_hide)
    ImageView ivHide;
    @BindView(R.id.bt_confirm)
    TextView btConfirm;
    private Disposable disposable;
    private boolean isSet;

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        isSet = intent.getBooleanExtra("isSet", false);
        if (isSet) {
            tvTitle.setText("设置交易密码");
        } else {
            tvTitle.setText("修改交易密码");
        }
//        tvPhone.setText();
    }



    @OnClick({R.id.iv_go_back, R.id.tv_get_code, R.id.iv_hide, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.tv_get_code:
//                    验证码
                startCountDownTime();
                break;
            case R.id.iv_hide:

                ivHide.setSelected(!ivHide.isSelected());
                if (ivHide.isSelected()) {
                    //显示密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                break;
            case R.id.bt_confirm:
                String code = etSecurityCode.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(code)) {

                    ToastUtils.show("验证码不能为空");
                    return;
                } else if (TextUtils.isEmpty(password)) {

                    ToastUtils.show("密码不能为空");
                    return;
                } else if (!Validator.isPassword(password)) {

                    ToastUtils.show("6-18位数字、字母组合");
                    return;
                }




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

                        tvGetCode.setEnabled(false);
                        tvGetCode.setTextColor(
                                AppCompatResources.getColorStateList(SetFundPassWordActivity.this, R.color.hint_color));
                        tvGetCode.setText(String.valueOf(60 - aLong) + " 秒可重新发送");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "倒计时完毕");
                        tvGetCode.setEnabled(true);
                        tvGetCode.setText("发送");
                        tvGetCode.setTextColor(AppCompatResources.getColorStateList(SetFundPassWordActivity.this, R.color.blue));
                    }
                })
                .subscribe();


    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_fund_pass_word;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }

    }



}
