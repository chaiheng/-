package com.h.cheng.chain100.activity;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.adapter.ViewPagerAdapter;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.downfile.FilePresenter;
import com.h.cheng.chain100.home.DynamicFragment;
import com.h.cheng.chain100.home.HotIssueFragment;
import com.h.cheng.chain100.home.MyFragment;
import com.h.cheng.chain100.view.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected FilePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    public void initView() {
        MyApplication.getInstance().exit();
        mFragments.add(HotIssueFragment.newInstance());
        mFragments.add(DynamicFragment.newInstance());
//        mFragments.add(TaskFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments, null);
        mainViewpager.setAdapter(mViewPagerAdapter);
        mainViewpager.setOffscreenPageLimit(mFragments.size());
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        bottomBar.selectTabWithId(R.id.tab_hot);
                        break;
                    case 1:
                        bottomBar.selectTabWithId(R.id.tab_dynamic);
                        break;
                    case 2:
                        bottomBar.selectTabWithId(R.id.tab_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                selectTab(tabId);
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                selectTab(tabId);
            }
        });


    }

    private void selectTab(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_hot:
                mainViewpager.setCurrentItem(0, false);
                break;
            case R.id.tab_dynamic:
                mainViewpager.setCurrentItem(1, false);
                break;

            case R.id.tab_my:
                mainViewpager.setCurrentItem(3, false);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
