package com.io.east.district.me;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;


/**
 *   半年业绩
 */
public class HalfYearPerformanceFragment extends BaseFragment {
    @Override
    protected void init() {

    }


    public static HalfYearPerformanceFragment newInstance() {
        HalfYearPerformanceFragment fragment = new HalfYearPerformanceFragment();
        return fragment;
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.half_year_performance_fragement;
    }
}
