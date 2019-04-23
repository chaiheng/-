package com.io.east.district.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.bean.AssetsBean;
import com.io.east.district.certification.CertificationActivity;
import com.io.east.district.me.AboutPartnersActivity;
import com.io.east.district.me.MeActivity;
import com.io.east.district.me.NoticeActivity;
import com.io.east.district.me.SafeCenterActivity;
import com.io.east.district.me.SettingActivity;
import com.io.east.district.me.ShareActivity;
import com.io.east.district.money.AssetsManagementActivity;
import com.io.east.district.view.dialog.ServiceDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_notice_number)
    Button btNoticeNumber;
    @BindView(R.id.cl_inform)
    ConstraintLayout clInform;
    @BindView(R.id.tv_total_assets)
    TextView tvTotalAssets;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.tv_usable)
    TextView tvUsable;
    @BindView(R.id.tv_transit)
    TextView tvTransit;
    @BindView(R.id.tv_all_orders)
    TextView tvAllOrders;
    @BindView(R.id.ll_obligation)
    LinearLayoutCompat llObligation;
    @BindView(R.id.ll_deliver)
    LinearLayoutCompat llDeliver;
    @BindView(R.id.ll_receiving)
    LinearLayoutCompat llReceiving;
    @BindView(R.id.ll_evaluate)
    LinearLayoutCompat llEvaluate;
    @BindView(R.id.rl_invite_friends)
    RelativeLayout rlInviteFriends;
    @BindView(R.id.rl_about_partners)
    RelativeLayout rlAboutPartners;
    @BindView(R.id.rl_identity_authentication)
    RelativeLayout rlIdentityAuthentication;
    @BindView(R.id.rl_safety_center)
    RelativeLayout rlSafetyCenter;
    @BindView(R.id.rl_my_customer_service)
    RelativeLayout rlMyCustomerService;
    @BindView(R.id.ll_asset)
    LinearLayout llAsset;
    private boolean isHide;
    private int total_assets;
    private int freeze_money;
    private int money;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected void init() {
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.myassets)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        AssetsBean assetsBean = JSON.parseObject(s, AssetsBean.class);

                        int code = assetsBean.getCode();
                        if (code == 1) {
                            String avatar = assetsBean.getData().getAvatar();
                            if (!TextUtils.isEmpty(avatar)) {
                                Glide.with(Objects.requireNonNull(getActivity())).load(avatar).into(ivHead);

                            }
                            String nickname = assetsBean.getData().getNickname();
                            tvName.setText(nickname);
                            total_assets = assetsBean.getData().getTotal_assets();

                            freeze_money = assetsBean.getData().getFreeze_money();
                            money = assetsBean.getData().getMoney();
                            tvUsable.setText("" + money);
                            tvTransit.setText("" + freeze_money);
                            tvTotalAssets.setText("" + total_assets);
                        }
                    }
                });

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_my;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.iv_head, R.id.iv_set, R.id.tv_name,R.id.ll_asset,
            R.id.bt_notice_number, R.id.cl_inform, R.id.iv_see,
            R.id.tv_all_orders, R.id.ll_obligation, R.id.ll_deliver,
            R.id.ll_receiving, R.id.ll_evaluate, R.id.rl_invite_friends,
            R.id.rl_about_partners, R.id.rl_identity_authentication,
            R.id.rl_safety_center, R.id.rl_my_customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
            case R.id.tv_name:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.iv_set:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;


            case R.id.bt_notice_number:

            case R.id.cl_inform:
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
            case R.id.iv_see:
                isHide = !isHide;
                if (isHide) {
                    ivSee.setImageResource(R.mipmap.look);
                    tvUsable.setText("" + money);
                    tvTransit.setText("" + freeze_money);
                    tvTotalAssets.setText("" + total_assets);
                } else {
                    ivSee.setImageResource(R.mipmap.hide);
                    tvUsable.setText("*****");
                    tvTransit.setText("*****");
                    tvTotalAssets.setText("*****");
                }
                break;
            case R.id.ll_asset:
                startActivity(new Intent(getActivity(), AssetsManagementActivity.class)
                .putExtra("money",""+money)
                .putExtra("freeze_money",""+freeze_money)
                .putExtra("total_assets",""+total_assets));
                break;
            case R.id.tv_all_orders:

            case R.id.ll_obligation:

            case R.id.ll_deliver:

            case R.id.ll_receiving:

            case R.id.ll_evaluate:
                ToastUtils.show("敬请期待");
                break;
            case R.id.rl_invite_friends:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            case R.id.rl_about_partners:
                startActivity(new Intent(getActivity(), AboutPartnersActivity.class));
                break;
            case R.id.rl_identity_authentication:
                startActivity(new Intent(getActivity(), CertificationActivity.class));
                break;
            case R.id.rl_safety_center:
                startActivity(new Intent(getActivity(), SafeCenterActivity.class));
                break;
            case R.id.rl_my_customer_service:
                ServiceDialog serviceDialog = new ServiceDialog(Objects.requireNonNull(getContext()));
                serviceDialog.show();
                break;
        }
    }


}
