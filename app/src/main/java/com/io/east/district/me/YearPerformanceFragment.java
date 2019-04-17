package com.io.east.district.me;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;


/**
 *   年业绩
 */
public class YearPerformanceFragment extends BaseFragment {
    @Override
    protected void init() {

    }


    public static YearPerformanceFragment newInstance() {
        YearPerformanceFragment fragment = new YearPerformanceFragment();
        return fragment;
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.year_performance_fragement;
    }
}
