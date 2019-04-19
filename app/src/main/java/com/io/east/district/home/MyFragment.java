package com.io.east.district.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;
import com.io.east.district.certification.CertificationActivity;
import com.io.east.district.me.AboutPartnersActivity;
import com.io.east.district.me.MeActivity;
import com.io.east.district.me.NoticeActivity;
import com.io.east.district.me.SafeCenterActivity;
import com.io.east.district.me.SettingActivity;
import com.io.east.district.me.ShareActivity;
import com.io.east.district.view.dialog.ServiceDialog;

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

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_my;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.iv_head, R.id.iv_set, R.id.tv_name, R.id.bt_notice_number, R.id.cl_inform, R.id.iv_see, R.id.tv_all_orders, R.id.ll_obligation, R.id.ll_deliver, R.id.ll_receiving, R.id.ll_evaluate, R.id.rl_invite_friends, R.id.rl_about_partners, R.id.rl_identity_authentication, R.id.rl_safety_center, R.id.rl_my_customer_service})
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
                ServiceDialog  serviceDialog  = new ServiceDialog(Objects.requireNonNull(getContext()));
                serviceDialog.show();
                break;
        }
    }
}
