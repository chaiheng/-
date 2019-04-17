package com.io.east.district.home;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;

/**
 * 充值保证金
 */
public class MarginDepositFragment extends BaseFragment {
    @Override
    protected void init() {

    }
    public static MarginDepositFragment newInstance() {
        MarginDepositFragment fragment = new MarginDepositFragment();
        return fragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.margin_deposit_fragment;
    }
}
