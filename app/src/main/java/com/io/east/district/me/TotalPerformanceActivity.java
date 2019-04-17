package com.io.east.district.me;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BaseFragmentAdapter;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.view.NoTouchViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.lucode.hackware.magicindicator.buildins.UIUtil.dip2px;

/**
 * 总业绩
 */
public class TotalPerformanceActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tab_layout_quotes_detail)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_quotes_detail)
    NoTouchViewPager mViewPager;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_total_performance;
    }

    @Override
    protected void onStart() {
        super.onStart();
        reflex(mTabLayout);
    }
    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("近一周");
        titleList.add("近一个月");
        titleList.add("近半年");
        titleList.add("近一年");
        fragmentList.add(WeeksPerformanceFragment.newInstance());
        fragmentList.add(MonthPerformanceFragment.newInstance());
        fragmentList.add(HalfYearPerformanceFragment.newInstance());
        fragmentList.add(YearPerformanceFragment.newInstance());
        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(titleList.size());
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }
}
