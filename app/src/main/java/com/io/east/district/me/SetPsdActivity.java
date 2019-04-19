package com.io.east.district.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.utils.Validator;


import butterknife.BindView;
import butterknife.OnClick;



/**
 * 设置密码
 *
 * @author Administrator
 */
public class SetPsdActivity extends BaseActivity {



    @Override
    public void initView() {
        super.initView();



    }






    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
    return R.layout.activity_set_psd;
    }
}
