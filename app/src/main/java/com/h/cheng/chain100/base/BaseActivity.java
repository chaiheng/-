package com.h.cheng.chain100.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.h.cheng.chain100.statusbar.UtilsStatusBar;
import com.h.cheng.chain100.statusbar.UtilsStyle;
import com.h.cheng.chain100.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    public Context context;
    private ProgressDialog dialog;
    public Toast toast;
    protected P presenter;

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsStatusBar.setStatusBar(this, false, true);
        UtilsStyle.setStatusBarMode(this, true);
        context = this;
        setContentView(getLayoutId());
        presenter = createPresenter();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initData() {
    }

    public void initView() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    /**
     * @param s
     */
    public void showtoast(String s) {
        ToastUtil.show(context, s);
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


}
