package com.h.cheng.chain100.start;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.h.cheng.chain100.R;
import com.h.cheng.chain100.statusbar.UtilsStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuidanceActivity extends AppCompatActivity {
    private ViewPager mViewPager;


    private List<Integer> ids = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsStyle.setStatusBarMode(this, false);
        setContentView(R.layout.activity_guidance);
        initView();
    }


    public void initView() {
        mViewPager = findViewById(R.id.viewpager);
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
    }



    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < ids.size(); i++) {
                mFragmentList.add(GuidanceFragment.newInstance(ids.get(i)));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

}
