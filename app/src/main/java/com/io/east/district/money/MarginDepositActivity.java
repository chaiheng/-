package com.io.east.district.money;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.RechargeBean;
import com.io.east.district.view.dialog.OrderDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zjun.widget.RuleView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarginDepositActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_recharge_amount)
    TextView tvRechargeAmount;

    @BindView(R.id.tv_actual_amount)
    TextView tvActualAmount;
    @BindView(R.id.bt_go_prepaid)
    Button btGoPrepaid;
    @BindView(R.id.horizontalScale)
    RuleView horizontalScale;
    private Vibrator vibrator;
    private String money;
    private String num;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_margin_deposit;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

//        horizontalScale.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent horizontalScale) {
//                if (MotionEvent.ACTION_MOVE==horizontalScale.getAction()){
//                    vibrator.vibrate(500);
//                }else {
//                    vibrator.cancel();
//                }
//                return false;
//            }
//        });
        horizontalScale.setOnValueChangedListener(new RuleView.OnValueChangedListener() {
            @Override
            public void onValueChanged(float value) {
                BigDecimal bigDecimal  = new BigDecimal(value) ;

                num = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toPlainString();
                Integer valueOf = Integer.valueOf(num);
                int figure = valueOf * 10000;
                int practical  = figure + 1000*valueOf;
                money  = String.valueOf(valueOf * 10000);
                tvActualAmount.setText(""+practical );
                tvRechargeAmount.setText(money);

            }
        });

    }


    @OnClick({R.id.iv_go_back, R.id.bt_go_prepaid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_go_prepaid:
                OrderDialog  orderDialog  =  new OrderDialog(this);
                orderDialog.show();
                orderDialog.setOrderLister(new OrderDialog.Order() {
                    @Override
                    public void onClick(View view) {

                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.post(UrlDeploy.recharge)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")
                                .params("money",money)
                                .params("num",num)
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        Log.d("ccc","...."+s);
                                        RechargeBean rechargeBean = JSON.parseObject(s,  RechargeBean.class);

                                        int code = rechargeBean.getCode();
                                        if (code==1){
                                            int recharge_id = rechargeBean.getData().getRecharge_id();
                                            startActivity(new Intent(MarginDepositActivity.this,PrepaidActivity.class)
                                                    .putExtra("recharge_id",""+recharge_id));
                                            SPUtils.getInstance("login").put("recharge_id",""+recharge_id);
                                            finish();
                                        }else {
                                            ToastUtils.show(rechargeBean.getMsg());
                                        }
//
                                    }
                                });
                        orderDialog.dismiss();
                    }
                });

                break;
        }
    }
}
