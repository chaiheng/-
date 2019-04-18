package com.io.east.district.money;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;

import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * 资产管理
 */
public class AssetsManagementActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_general_assets)
    TextView tvGeneralAssets;
    @BindView(R.id.bt_withdraw_deposit)
    Button btWithdrawDeposit;
    @BindView(R.id.tv_usable)
    TextView tvUsable;
    @BindView(R.id.tv_route)
    TextView tvRoute;
    @BindView(R.id.bt_select_time)
    Button btSelectTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_management;
    }


    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.iv_go_back, R.id.bt_withdraw_deposit, R.id.bt_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_withdraw_deposit:
                break;
            case R.id.bt_select_time:

                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
                picker.setGravity(Gravity.BOTTOM);
                picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
                picker.setRangeStart(2016, 10, 14);
                picker.setLabel("", "", "");
                picker.setUseWeight(true);
                picker.setBackgroundColor(R.color.white);
                picker.setCancelText("取消");
                picker.setCancelTextColor(R.color.hint_color);
                picker.setCancelTextSize(18);
                picker.setSubmitText("确定");
                picker.setSubmitTextSize(18);
                picker.setCancelTextColor(R.color.color_333);
                picker.setDividerColor(R.color.line);
                picker.setTextColor(R.color.black);
                picker.setTopLineVisible(true);
                picker.setTopBackgroundColor(R.color.white);
                picker.setTopHeight(55);
                picker.setTopLineColor(R.color.line);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
                picker.setRangeEnd(2030, 11, 11);
                picker.setSelectedItem(2019, 1);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
//                        showToast(year + "-" + month);
                    }
                });
                picker.show();
                break;
        }
    }
}
