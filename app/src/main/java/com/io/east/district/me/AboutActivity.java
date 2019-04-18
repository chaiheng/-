package com.io.east.district.me;

import android.os.Bundle;
import android.widget.ImageView;


import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 关于我们
 *
 * @author Administrator
 */
public class AboutActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;






    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
     return R.layout.activity_about;
    }
}
