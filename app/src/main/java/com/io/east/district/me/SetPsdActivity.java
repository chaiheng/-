package com.io.east.district.me;

import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.blankj.utilcode.util.GsonUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BaseBody;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.utils.CheckUtils;
import com.io.east.district.utils.EmojiInputFilter;
import com.io.east.district.view.ClearEditText;
import com.io.east.district.view.PasswdEditText;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * 设置密码
 *
 * @author Administrator
 */
public class SetPsdActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.authcode)
    EditText authcode;
    @BindView(R.id.tv_sendauth)
    TextView tvSendauth;
    @BindView(R.id.password)
    PasswdEditText password;
    @BindView(R.id.tv_findpass)
    TextView tvFindpass;
    private Disposable disposable;

    @Override
    public void initView() {
        super.initView();
        etPhone.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(11)});
        password.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(18)});

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_psd;
    }

    public boolean Check() {
        if (TextUtils.isEmpty(etPhone.getText().toString())){
            ToastUtils.show("请输入手机号码");
            return false;
        }else if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
            ToastUtils.show("请输入正确的手机号码");
            return false;
        } else if (!CheckUtils.validatePhonePass(password.getText().toString())) {
            ToastUtils.show("密码必须是6-18位字母加数字组合");
            return false;
        } else if (authcode.getText().toString().trim().length() < 6) {
            ToastUtils.show("请输入6位验证码");
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_go_back, R.id.tv_findpass,R.id.tv_sendauth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;

            case R.id.tv_sendauth:
                if (TextUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtils.show("请输入手机号码");
                }else if (!CheckUtils.validatePhone(etPhone.getText().toString())) {
                    ToastUtils.show("请输入正确的手机号码");
                } else {

                    EasyHttp.get(UrlDeploy.verification_code)
                            .params("mobile", etPhone.getText().toString())
                            .params("reset", "1")
                            .execute(new SimpleCallBack<String>() {

                                @Override
                                public void onError(ApiException e) {
                                    Log.d("eee", e.getMessage());
                                }

                                @Override
                                public void onSuccess(String s) {

                                    BaseBody baseBody = GsonUtils.fromJson(s, BaseBody.class);
                                    if (baseBody.isOk()){
                                        ToastUtils.show("验证码发送成功");
                                        startCountDownTime();

                                    }else {
                                        ToastUtils.show(baseBody.getMsg());
                                    }

                                }
                            });
//                    CountDownTimerUtils.startCount(tvSendauth);
//                    presenter.SendMsg(etPhone.getText().toString());
                }
                break;
            case R.id.tv_findpass:
            if (Check()){
                EasyHttp.post(UrlDeploy.PasswordReset)
                        .params("mobile",etPhone.getText().toString())
                        .params("password",password.getText().toString())
                        .params("verification_code",authcode.getText().toString())
                        .params("password_type","password")
                        .execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {
                                Log.d("ee",e.getMessage());
                            }

                            @Override
                            public void onSuccess(String s) {
                                BaseBody baseBody = GsonUtils.fromJson(s, BaseBody.class);
                                if (baseBody.isOk()){
                                    ToastUtils.show("修改成功");
                                    finish();
                                }else {
                                    ToastUtils.show(baseBody.getMsg());
                                }
                            }
                        });

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
                        tvSendauth.setEnabled(false);
                        tvSendauth.setTextColor(
                                AppCompatResources.getColorStateList(SetPsdActivity.this, R.color.hint_color));
                        tvSendauth.setText((60 - aLong) + "秒可重新发送");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "倒计时完毕");
                        tvSendauth.setEnabled(true);
                        tvSendauth.setTextColor(
                                AppCompatResources.getColorStateList(SetPsdActivity.this, R.color.color_333));
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
