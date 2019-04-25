package com.io.east.district.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.bean.VerificationBean;
import com.io.east.district.certification.CertificationActivity;
import com.io.east.district.money.MarginDepositActivity;
import com.io.east.district.view.dialog.IdentificationDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 招募合伙人
 */
public class PartnerFragment extends BaseFragment {

    @BindView(R.id.bt_recharge)
    Button btRecharge;

    public static PartnerFragment newInstance() {
        PartnerFragment fragment = new PartnerFragment();
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_task;
    }

    @OnClick(R.id.bt_recharge)
    public void onViewClicked() {
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
                            int is_verify = verificationBean.getData().getIs_verify();
                            if (is_verify==0){
//                                   没有认证
                                IdentificationDialog  dialog  = new IdentificationDialog(Objects.requireNonNull(getActivity()));
                                dialog.show();
                                dialog.setIdentificationLister(new IdentificationDialog.Identification() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getActivity(), CertificationActivity.class));
                                    }
                                });
                            }else {
                                startActivity(new Intent(getActivity(), MarginDepositActivity.class));
                            }
                        }
                    }
                });

    }
}
