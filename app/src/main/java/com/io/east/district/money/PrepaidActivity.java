package com.io.east.district.money;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.CancelRechargeBean;
import com.io.east.district.bean.PrepaidBean;
import com.io.east.district.event.ConnectionEvent;
import com.io.east.district.view.dialog.CancelOrderDialog;
import com.io.east.district.view.dialog.PaidDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * 发起充值
 */
public class PrepaidActivity extends BaseActivity {

    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.tv_figure)
    TextView tvFigure;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.bt_copy_address)
    Button btCopyAddress;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.bt_paid)
    Button btPaid;
    @BindView(R.id.tv_prepaid_address)
    TextView tvPrepaidAddress;
    @BindView(R.id.cv_count_down)
    CountdownView cvCountDown;
    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;
    private int countdownTime;
    private String address;
    private Bitmap qrBitmap;
    private int status;
    private String recharge_id;
    private int partner;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prepaid;
    }

    @Override
    public void initData() {
        super.initData();
        String token = SPUtils.getInstance("login").getString("token");
        recharge_id = getIntent().getStringExtra("recharge_id");
//        String url = UrlDeploy.cancelRecharge + recharge_id;
//        Log.d("uuu","...."+url);
        EasyHttp.get(UrlDeploy.cancelRecharge + recharge_id)

                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .params("is_address", "1")

                .execute(new SimpleCallBack<String>() {


                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        PrepaidBean prepaidBean = JSON.parseObject(s, PrepaidBean.class);

                        int code = prepaidBean.getCode();
                        if (code == 1) {
                            String money = prepaidBean.getData().getMoney();
                            partner = prepaidBean.getData().getIs_partner();
                            String amount = prepaidBean.getData().getAmount();
                            countdownTime = prepaidBean.getData().getCountdown();
                            tvFigure.setText(amount + "BTA");
                            status = prepaidBean.getData().getStatus();
                            address = prepaidBean.getData().getUser_bta().getAddress();
                            tvPrepaidAddress.setText(address);
                            qrBitmap = CodeUtils.createImage(address, 130, 130, null);
                            ivQrCode.setImageBitmap(qrBitmap);

                        }
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        cvCountDown.start(1800000);
        for (int i = 0; i < 1000; i++) {
            cvCountDown.updateShow(i);
        }
        cvCountDown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
//                   倒计时结束 如果状态成功跳转成功    如果失败取消跳转失败
            }
        });
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startMain();
    }

    private void startMain() {
        if (partner == 1) {
            startActivity(new Intent(this, MainActivity.class));
            EventBus.getDefault().post(new ConnectionEvent(true));
        } else {
            startActivity(new Intent(this, MainActivity.class).putExtra("isNo", true));
        }
    }

    @OnClick({R.id.tv_save, R.id.bt_copy_address, R.id.tv_cancel_order, R.id.bt_paid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:

                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                File appDir = new File(Environment.getExternalStorageDirectory(),
                                        "Photo");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }

                                String fileName = "recharge" + ".jpg";
                                File file = new File(appDir, fileName);
                                String absolutePath = file.getAbsolutePath();
//                                String galleryPath = Environment.getExternalStorageDirectory()
//                                        + File.separator + Environment.DIRECTORY_DCIM
//                                        + File.separator + "Camera" + File.separator;
//                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_qr_code);
//                                File file = new File(galleryPath);

                                MediaStore.Images.Media.insertImage(getContentResolver(), qrBitmap, absolutePath, null);
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                Uri uri = Uri.fromFile(file);
                                intent.setData(uri);
                                sendBroadcast(intent);

                                ToastUtils.show("图片保存成功");

//                                ImgUtils.saveBmp2Gallery(ShareActivity.this, saveImage(),"share");
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
//       拒绝权限
                                ToastUtils.show("请给予读取权限");
                            }
                        })
                        .start();
                break;
            case R.id.bt_copy_address:

                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("Simple test", address);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);

                ToastUtils.show("复制成功！");
                break;
            case R.id.tv_cancel_order:
                CancelOrderDialog cancelOrderDialog = new CancelOrderDialog(this);
                cancelOrderDialog.show();
                cancelOrderDialog.setCancelOrderLister(new CancelOrderDialog.CancelOrder() {
                    @Override
                    public void onClick(View view) {
                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.put(UrlDeploy.cancelRecharge + recharge_id)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")

                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        CancelRechargeBean cancelRechargeBean = JSON.parseObject(s, CancelRechargeBean.class);

                                        int code = cancelRechargeBean.getCode();
                                        if (code == 1) {
                                            ToastUtils.show("取消成功");
                                        } else {
                                            ToastUtils.show(cancelRechargeBean.getMsg());
                                        }
                                    }
                                });
                        cancelOrderDialog.dismiss();
                    }
                });
                break;
            case R.id.bt_paid:
                PaidDialog paidDialog = new PaidDialog(this);
                paidDialog.show();
                paidDialog.setPaidLister(new PaidDialog.Paid() {
                    @Override
                    public void onClick(View view) {
                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.get(UrlDeploy.cancelRecharge + recharge_id)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")
                                .params("is_address", "")
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        PrepaidBean prepaidBean = JSON.parseObject(s, PrepaidBean.class);

                                        int code = prepaidBean.getCode();
                                        if (code == 1) {

                                            int status = prepaidBean.getData().getStatus();
                                            address = prepaidBean.getData().getUser_bta().getAddress();
                                            tvPrepaidAddress.setText(address);
                                            qrBitmap = CodeUtils.createImage(address, 130, 130, null);
                                            ivQrCode.setImageBitmap(qrBitmap);

                                            startActivity(new Intent(PrepaidActivity.this, ConfirmPrepaidActivity.class)
                                                    .putExtra("recharge_id", "" + recharge_id)
                                            );
                                            finish();
                                        }
                                    }
                                });
                    }
                });
                break;
        }
    }
}
