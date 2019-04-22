package com.io.east.district.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.MyApplication;
import com.io.east.district.R;
import com.io.east.district.adapter.ViewPagerAdapter;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.bean.PeopleBean;
import com.io.east.district.downfile.FilePresenter;
import com.io.east.district.home.ConfirmPrepaidFragment;
import com.io.east.district.home.ConnectionFragment;
import com.io.east.district.home.MyFragment;
import com.io.east.district.home.PartnerFragment;
import com.io.east.district.home.ProjectFragment;
import com.io.east.district.view.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

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
    public int is_partner;

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
        mFragments.add(ProjectFragment.newInstance());
        mFragments.add(ConnectionFragment.newInstance());
        mFragments.add(ConfirmPrepaidFragment.newInstance());
        mFragments.add(PartnerFragment.newInstance());
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

                String token = SPUtils.getInstance("login").getString("token");

                EasyHttp.get(UrlDeploy.people)
                        .headers("XX-Token",token)
                        .headers("XX-Device-Type","android")
                        .execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {
                                Log.d("ssss", "...." + e.getMessage());
                            }

                            @Override
                            public void onSuccess(String s) {
                                Log.d("renmai", "...." + s);

                                PeopleBean peopleBean = JSON.parseObject(s, PeopleBean.class);
                                if (peopleBean.getData()==null){
//                               为空不是合伙人
                                    mainViewpager.setCurrentItem(3, false);
                                }else {
                                    if (peopleBean.getCode()==1){
                                        is_partner = peopleBean.getData().getIs_partner();

                                        mainViewpager.setCurrentItem(1, false);
//                            clpProgress.setProgress();
                                    }

                                }

                            }
                        });


//                    mainViewpager.setCurrentItem(2, false);



                break;

            case R.id.tab_my:

                mainViewpager.setCurrentItem(5, false);
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
