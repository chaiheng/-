package com.h.cheng.chain100.home;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.activity.SettingActivity;
import com.h.cheng.chain100.base.BaseFragment;

import butterknife.OnClick;

public class MyFragment extends BaseFragment {

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

    @OnClick(R.id.lv_setting)
    public void onViewClicked() {
        startAct(SettingActivity.class);
    }
}
