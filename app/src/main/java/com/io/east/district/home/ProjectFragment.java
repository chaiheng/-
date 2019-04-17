package com.io.east.district.home;

import com.io.east.district.R;
import com.io.east.district.base.BaseFragment;

/**
 *    商品
 */
public class ProjectFragment extends BaseFragment {

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
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
