package com.io.east.district.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.MyApplication;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.bean.UpdateBean;
import com.io.east.district.bean.VerificationBean;
import com.io.east.district.event.LogoutEvent;
import com.io.east.district.login.LoginActivity;
import com.io.east.district.statusbar.UtilsStatusBar;
import com.io.east.district.statusbar.UtilsStyle;
import com.io.east.district.utils.PackageUtils;
import com.io.east.district.utils.ToastUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    public Context context;
    private ProgressDialog dialog;
    public Toast toast;
    protected P presenter;
    private Activity mActivity;

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsStatusBar.setStatusBar(this, false, true);
        UtilsStyle.setStatusBarMode(this, true);
        Log.e("activity", "activity----" + getClass().getName());
        context = this;
        mActivity = this;
        setContentView(getLayoutId());
        presenter = createPresenter();
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(this);
        initView();
        initData();
//        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goLogin(LogoutEvent event) {
        if (event.isLogout) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    public void initData() {
    }

    public void initView() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.detachView();
        }
    }

    /**
     * @param s
     */
    public void showtoast(String s) {
        ToastUtil.show(mActivity, s);
    }


    public void showFileDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("正在下载中,请稍后");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.show();
    }

    public void hideFileDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void showLoadingDialog() {

        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("加载中...");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }


    @Override
    public void showError(String msg) {
        hideLoading();
        showtoast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
    }

    @Override
    public void showLoadingFileDialog() {
        showFileDialog();
    }

    @Override
    public void hideLoadingFileDialog() {
        hideFileDialog();
    }

    @Override
    public void onProgress(long totalSize, long downSize) {
        if (dialog != null) {
            dialog.setProgress((int) (downSize * 100 / totalSize));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
//        updateApp();
//        isLoginOut();
    }

    /**
     * 版本更新
     */
    private void updateApp() {


        EasyHttp.get(UrlDeploy.version)

                .execute(new SimpleCallBack<String>() {


                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("up", "...." + s);
                        try {
                            UpdateBean updateBean = GsonUtils.fromJson(s, UpdateBean.class);
                            String versionName = PackageUtils.packageName(BaseActivity.this);
                            String replace = versionName.replace(".", "");
                            Integer localVersion = Integer.valueOf(replace);
                            if (updateBean.getData() != null) {
                                String version = updateBean.getData().getNewversion();
                                int isUpdate = updateBean.getData().getEnforce();
                                String downloadUrl = updateBean.getData().getDownloadurl();
                                String content = updateBean.getData().getContent();
                                String replace2 = version.replace(".", "");
                                Integer serverVersion = Integer.valueOf(replace2);
                                if (1 == isUpdate && localVersion < serverVersion) {
                                    //                     强制更新
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
                                    builder1.setTitle("更新提示");
                                    builder1.setCancelable(false);
                                    builder1.setMessage(content);
                                    builder1.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            Uri content_url = Uri.parse(downloadUrl);
                                            intent.setData(content_url);
                                            startActivity(intent);
                                            builder1.create().dismiss();
                                        }
                                    });
                                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            Application.getInstance().finishActivities();
                                            builder1.create().dismiss();
                                            finish();
                                            System.exit(0);

                                        }
                                    });
                                    builder1.create().show();


                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                });
    }
/**   判断是否登录过期
 *
 * */
    private void isLoginOut() {
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.verification)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        VerificationBean verificationBean = JSON.parseObject(s, VerificationBean.class);
                        String msg = verificationBean.getMsg();
                        int code = verificationBean.getCode();
                        if (code == 10001) {
                            ToastUtils.show("登录已经失效，请重新登录");
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(BaseActivity.this,LoginActivity.class));

                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task,1000);

                        }
                    }
                });
    }


}
