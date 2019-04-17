package com.io.east.district.home;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;

/**
 *   招募合伙人
 */
public class PartnerFragment extends BaseFragment {

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
}
