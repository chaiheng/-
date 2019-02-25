package com.h.cheng.chain100.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.cheng.chain100.view.StateView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 */

public abstract class BaseFragment extends LazyLoadFragment {
    public boolean hasInit = false;
    private View rootView;
    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局
    protected Activity mContext;
    private boolean isCreated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, rootView);

            mStateView = StateView.inject(getStateViewRoot());
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (!hasInit) {
            hasInit = true;
            init();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void init();

    /**
     * StateView的根布局，默认是整个界面，如果需要变换可以重写此方法
     */
    public View getStateViewRoot() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    public String getMyString(int strId) {
        return mContext.getResources().getString(strId);
    }

    private Dialog mDialog;

    protected void showWaitingDialog(String msg) {
        if (mDialog == null) {
            mDialog = ProgressDialog.show(mContext, "", msg);
        } else {
            mDialog.show();
        }
    }

    protected void dismissWaitingDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    //加载数据
    protected void loadData() {

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

    public void startAct(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }
}
