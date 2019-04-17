package com.io.east.district.home;

import com.io.east.district.R;
import com.io.east.district.activity.SettingActivity;
import com.io.east.district.base.BaseFragment;

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


}
