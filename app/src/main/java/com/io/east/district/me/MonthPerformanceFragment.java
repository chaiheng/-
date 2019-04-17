package com.io.east.district.me;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;


/**
 *   月业绩
 */
public class MonthPerformanceFragment extends BaseFragment {
    @Override
    protected void init() {

    }


    public static MonthPerformanceFragment newInstance() {
        MonthPerformanceFragment fragment = new MonthPerformanceFragment();
        return fragment;
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.month_performance_fragement;
    }
}
