package com.io.east.district.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.io.east.district.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by mayn on 2018/6/1.
 */

public class MyRefreshHeader extends FrameLayout implements RefreshHeader {
    AnimationDrawable mAnimationDrawable;
    protected boolean mNoMoreData = false;
    ImageView animImg;
    TextView tv_finish;

    public MyRefreshHeader(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R.layout.layout_fresh_header, null);
        animImg = inflate.findViewById(R.id.head_loading_img);
        tv_finish = inflate.findViewById(R.id.tv_finish);
        mAnimationDrawable = (AnimationDrawable) animImg.getBackground();
        startAnim(mAnimationDrawable);
        addView(inflate);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    private void startAnim(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.start();
            tv_finish.setVisibility(GONE);
            animImg.setVisibility(VISIBLE);
        }

    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
//        if(mAnimationDrawable != null){
//            mAnimationDrawable.start();
//        }
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        tv_finish.setVisibility(GONE);
        animImg.setVisibility(VISIBLE);
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (mAnimationDrawable != null) {
            mAnimationDrawable.start();
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
            tv_finish.setVisibility(VISIBLE);
            animImg.setVisibility(GONE);
        }
        return 1000;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
