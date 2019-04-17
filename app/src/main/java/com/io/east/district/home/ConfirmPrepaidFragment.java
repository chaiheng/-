package com.io.east.district.home;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * 确认充值
 */
public class ConfirmPrepaidFragment extends BaseFragment {
    @BindView(R.id.srl_deposit)
    SmartRefreshLayout srlDeposit;

    @Override
    protected void init() {

    }

    public static ConfirmPrepaidFragment newInstance() {
        ConfirmPrepaidFragment fragment = new ConfirmPrepaidFragment();
        return fragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.margin_deposit_fragment;
    }
}
