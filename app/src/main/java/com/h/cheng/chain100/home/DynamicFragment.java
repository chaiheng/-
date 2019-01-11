package com.h.cheng.chain100.home;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseFragment;

public class DynamicFragment extends BaseFragment {

    public static DynamicFragment newInstance() {
        DynamicFragment fragment = new DynamicFragment();
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_dynamic;
    }
}
