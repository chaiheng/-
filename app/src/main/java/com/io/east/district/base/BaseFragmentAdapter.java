package com.io.east.district.base;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ll on 2017/10/14.
 */

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragmentList;
    List<String> mTitleList;
    FragmentManager mFm;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
        this.mFm = fm;
        Log.d("size","adapter...."+mFragmentList.size());
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleList = titleList;
    }


    @Override
    public Fragment getItem(int position) {
       Log.d("position", "...p" + position);
        int size = mFragmentList.size();
        Log.d("size","....ba"+size);
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null && position < mTitleList.size()) {
            return mTitleList.get(position);
        } else {
            return super.getPageTitle(position);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
