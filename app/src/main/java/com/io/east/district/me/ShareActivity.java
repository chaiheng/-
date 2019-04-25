package com.io.east.district.me;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.view.StateView;
import com.io.east.district.view.dialog.ShareDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 分享下载
 *
 * @author Administrator
 */
public class ShareActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.bt_save)
    Button btSave;


    TextView tvShare;
    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.bt_copy_invitation_code)
    Button btCopyInvitationCode;
    @BindView(R.id.bt_copy_address)
    Button btCopyAddress;
    private String url;
    protected StateView mStateView;
    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;

    private Bitmap qrBitmap;

    private String invitation_code;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }


    @Override
    public void initView() {
        super.initView();
        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        invitation_code = SPUtils.getInstance("login").getString("invitation_code");
        tvInvitationCode.setText(invitation_code);
       url = "https://h5.qdd.world/#/Register?code=" + invitation_code;
        qrBitmap = CodeUtils.createImage(url, 160, 160, null);
        ivQrCode.setImageBitmap(qrBitmap);
    }

    @OnClick({R.id.iv_go_back, R.id.bt_save, R.id.tv_share,
            R.id.bt_copy_invitation_code, R.id.bt_copy_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.bt_save:
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

                                String fileName = "share" + ".jpg";
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
            case R.id.tv_share:
                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.show();
                break;
            case R.id.bt_copy_invitation_code:
//                String copy = tvInvitationCode.getText().toString();
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("Simple test", invitation_code);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);

                ToastUtils.show("复制成功！");
                break;
            case R.id.bt_copy_address:


                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("Simple test", url);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);

                ToastUtils.show("复制成功！");
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
