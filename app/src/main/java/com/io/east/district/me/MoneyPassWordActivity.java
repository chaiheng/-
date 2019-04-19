package com.io.east.district.me;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资金密码
 *
 * @author Administrator
 */
public class MoneyPassWordActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.bt_amend)
    Button btAmend;




    @OnClick({R.id.iv_go_back, R.id.bt_amend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_amend:
                startActivity(new Intent(MoneyPassWordActivity.this,SetFundPassWordActivity.class)
                .putExtra("isSet",false));
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_pass_word;
    }
}
