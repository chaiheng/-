package com.io.east.district.me;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;


/**
 *   周业绩
 */
public class WeeksPerformanceFragment extends BaseFragment {
    @Override
    protected void init() {

    }


    public static WeeksPerformanceFragment newInstance() {
        WeeksPerformanceFragment fragment = new WeeksPerformanceFragment();
        return fragment;
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.weeks_performance_fragement;
    }
}
