package com.io.east.district.activity;

import android.content.Intent;
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
import com.io.east.district.base.BaseFragmentAdapter;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.PeopleBean;
import com.io.east.district.event.ConnectionEvent;
import com.io.east.district.home.ConnectionFragment;
import com.io.east.district.home.MyFragment;
import com.io.east.district.home.PartnerFragment;
import com.io.east.district.home.ProjectFragment;
import com.io.east.district.money.ConfirmPrepaidActivity;
import com.io.east.district.view.NoTouchViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.main_viewpager)
    NoTouchViewPager mainViewpager;

    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public int is_partner;
    private boolean isNo;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    public void initView() {
        EventBus.getDefault().register(this);
        getIntent().getBooleanExtra("isNo", false);


        mFragments.add(ProjectFragment.newInstance());
        mFragments.add(ConnectionFragment.newInstance());

        mFragments.add(PartnerFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        Log.d("size", "...." + mFragments.size());
        int currentItem = mainViewpager.getCurrentItem();
        Log.d("currentItem", "...cu1" + currentItem);
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments);
        mainViewpager.setAdapter(baseFragmentAdapter);
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
                    case 2:
                        bottomBar.selectTabWithId(R.id.tab_dynamic);
                        break;
                    case 3:
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
                        .headers("XX-Token", token)
                        .headers("XX-Device-Type", "android")
                        .execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {
                                Log.d("ssss", "...." + e.getMessage());
                            }

                            @Override
                            public void onSuccess(String s) {
                                Log.d("renmai", "...." + s);

                                PeopleBean peopleBean = JSON.parseObject(s, PeopleBean.class);

                                if (peopleBean.getCode() == 1) {

                                    is_partner = peopleBean.getData().getIs_partner();
                                    int recharge_id = peopleBean.getData().getRecharge_id();
                                    int is_already = peopleBean.getData().getIs_already();
                                    if (is_already == 1) {
//                                               是有订单
                                        startActivity(new Intent(MainActivity.this, ConfirmPrepaidActivity.class)
                                                .putExtra("recharge_id", "" + recharge_id));
                                    } else {
                                        if (is_partner == 0) {
                                            mainViewpager.setCurrentItem(2, false);
                                        } else {
                                            mainViewpager.setCurrentItem(1, false);
                                        }
                                    }


                                }


                            }
                        });


                break;

            case R.id.tab_my:
                int currentItem = mainViewpager.getCurrentItem();
                Log.d("currentItem", "...currentItem1" + currentItem);
                mainViewpager.setCurrentItem(3, false);
                Log.d("currentItem", "...currentItem" + currentItem);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goConnection(ConnectionEvent event) {
        if (event.isPartner) {
            mainViewpager.setCurrentItem(1, false);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();   finish();
//
//        finish();
//        System.exit(0);
        MyApplication.getInstance().exit();
        finish();
    }
}
