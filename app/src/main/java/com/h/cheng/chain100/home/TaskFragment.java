package com.h.cheng.chain100.home;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseFragment;

public class TaskFragment extends BaseFragment {

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
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
