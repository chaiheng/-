package com.io.east.district.me;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.BaseEntity;
import com.io.east.district.login.LoginActivity;
import com.io.east.district.utils.DataCleanManager;
import com.io.east.district.view.dialog.QuitDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 *
 * @author Administrator
 */
public class SettingActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_bytes)
    TextView tvBytes;
    @BindView(R.id.rl_clear_cache)
    RelativeLayout rlClearCache;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.login_out_container)
    RelativeLayout loginOutContainer;





    @Override
    public void initView() {
        super.initView();
      /*  if (!LogingUtils.isLogin(mContext, false)) {
            loginOutContainer.setVisibility(View.GONE);
        } else {
            loginOutContainer.setVisibility(View.VISIBLE);
        }*/
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tvBytes.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoginOutDialog() {
        QuitDialog quitDialog = new QuitDialog(this);
        quitDialog.show();
        quitDialog.setQuit(new QuitDialog.Quit() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_quit:
                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.get(UrlDeploy.logout)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        BaseEntity baseEntity = GsonUtils.fromJson(s, BaseEntity.class);
                                        if (baseEntity.getCode()==1){
                                            logout (SettingActivity.this);

                                        }
                                    }
                                });

                        break;
                    case R.id.bt_cancel:
                        quitDialog.dismiss();

                        break;
                }
            }
        });

    }

    public static void logout (Activity context) {

            String phone = SPUtils.getInstance("login").getString("phone");
            context.startActivity(new Intent(context, LoginActivity.class)
                    .putExtra("phone", phone));
//            SharedPreferencesTool.loginOut(context);
            SPUtils.getInstance("login").clear();
            context.finish();


    }


    @OnClick({R.id.iv_go_back, R.id.rl_clear_cache, R.id.rl_about, R.id.login_out_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.rl_clear_cache:
                DataCleanManager.clearAllCache(this);
                tvBytes.setText("0kb");

                ToastUtils.show("清除成功");
                break;
            case R.id.rl_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                finish();
                break;

            case R.id.login_out_container:
                showLoginOutDialog();
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
       return R.layout.activity_setting;
    }
}
