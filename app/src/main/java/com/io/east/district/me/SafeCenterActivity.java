package com.io.east.district.me;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.VerificationBean;
import com.io.east.district.utils.SharedPreferencesUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

;

/**
 * 安全中心
 *
 * @author Administrator
 */
public class SafeCenterActivity extends BaseActivity {

    @BindView(R.id.lin_set_psd)
    RelativeLayout linSetPsd;

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;

    @BindView(R.id.text_age_hint)
    TextView textAgeHint;
    @BindView(R.id.text_sign_hint)
    TextView textSignHint;
    @BindView(R.id.lin_update_psd)
    RelativeLayout linUpdatePsd;

    private String fcode;
    private String fname;
    private String idCardZmImgURL;
    private String idCardFmImgURL;
    int status = 0;
    private String password = "";
    private String remark;
    String phone;



    @Override
    protected void onPause() {
        super.onPause();
        fetchSafeSetting();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_safe_center;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchSafeSetting();
        phone = SharedPreferencesUtil.getInstance().getInfo("fphone");
        if (phone == null) {
            phone = "";
        }

    }



    @OnClick({R.id.lin_set_psd, R.id.iv_go_back,
            R.id.lin_update_psd,})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.lin_set_psd:
                startActivity(new Intent(this, SetPsdActivity.class));
                break;

            case R.id.iv_go_back:
                finish();
                break;
            case R.id.lin_update_psd:
//                资金密码
                String token = SPUtils.getInstance("login").getString("token");
                EasyHttp.get(UrlDeploy.verification)
                        .headers("XX-Token", token)
                        .headers("XX-Device-Type", "android")
                        .execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {

                            }

                            @Override
                            public void onSuccess(String s) {
                                VerificationBean verificationBean = JSON.parseObject(s, VerificationBean.class);
                                String msg = verificationBean.getMsg();
                                int code = verificationBean.getCode();
                                if (code==1){
                                    int is_payment = verificationBean.getData().getIs_payment();
                                    if (is_payment==1) {
                                        startActivity(new Intent(SafeCenterActivity.this, MoneyPassWordActivity.class)
                                                .putExtra("isSet", false));

                                    } else {
                                        startActivity(new Intent(SafeCenterActivity.this, SetFundPassWordActivity.class)
                                                .putExtra("isSet", true));
                                    }
                                }
                            }
                        });




                break;





            default:
                break;
        }
    }



    /**
     * 获取安全设置状态
     */
    private void fetchSafeSetting() {

//        showProgress();
     /*   String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(URLConfig.URL_SAFE_SETTING_DETAIL)
                .params("token", token)
                .execute(new SimpleCallBack<String>() {


                    @Override
                    public void onError(ApiException e) {
                        Log.e("ApiException", e.getMessage());

                    }

                    @Override
                    public void onSuccess(String s) {

                        Log.d("safe", "...." + s);


                        try {
                            mSafeSettingEntity = GsonUtils.fromJson(s, SafeSettingEntity.class);
                            if ("登录已失效，请重新登录!".equals(mSafeSettingEntity.getMsg())) {
                                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                                ToastUtils.show("登录已失效请重新登录");
                                openLogin();
                                return;
                            }

                            if (mSafeSettingEntity.getCode() == 200) {
                                fuser = mSafeSettingEntity.getData().getFuser();



                                if (mSafeSettingEntity.getData().getIdentity() != null) {
                                    fcode = mSafeSettingEntity.getData().getIdentity().getFcode();
                                    fname = mSafeSettingEntity.getData().getIdentity().getFname();
                                    remark = mSafeSettingEntity.getData().getIdentity().getRemark();
                                    idCardZmImgURL = mSafeSettingEntity.getData().getIdentity().getIdCardZmImgURL();
                                    idCardFmImgURL = mSafeSettingEntity.getData().getIdentity().getIdCardFmImgURL();
                                }


                            } else if (mSafeSettingEntity.getCode() == 401) {
                                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                                ToastUtils.show("登录已失效请重新登录");
                                openLogin();
                            } else {
                                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                                ToastUtils.show(mSafeSettingEntity.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                });*/

    }




}
