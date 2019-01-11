package com.h.cheng.chain100.home;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseFragment;

public class HotIssueFragment extends BaseFragment {

    public static HotIssueFragment newInstance() {
        HotIssueFragment fragment = new HotIssueFragment();
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_hot_issue;
    }
}
