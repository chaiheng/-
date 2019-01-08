package com.h.cheng.chain100.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.cheng.chain100.utils.ToastUtil;

public abstract class BaseFragment extends LazyLoadFragment {

    public Activity mContext;
    private boolean isCreated = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    //加载数据
    protected void loadData() {

    }


    public void onPauseFragment() {
        //处理切换fragment时 需要处理的问题
    }

    public void onResumeFragment() {
        //处理onResume
    }

    public void showSuccessToast(String r) {
        ToastUtil.show(mContext, r);
    }

    public void showErrorToast(String r) {
        ToastUtil.showError(mContext, r);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 友盟统计页面跳转，重写此方法，可以保证ViewPager切换Fragment时能够准确的记录Fragment之间的跳转
        // 不用再调用Fragment的生命周期方法
        if (!isCreated) {
            return;
        }

    }

    public String getMyString(int strId){
        return mContext.getResources().getString(strId);
    }
}
