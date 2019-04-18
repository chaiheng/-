package com.io.east.district.me;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;

import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.view.CircleImageView;
import com.io.east.district.view.dialog.IconDialog;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;


import com.yanzhenjie.permission.runtime.Permission;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


import static com.luck.picture.lib.config.PictureMimeType.ofImage;


/**
 * 资料编辑
 *
 * @author Administrator
 */
@SuppressLint("Registered")
public class MeActivity extends BaseActivity {
    @BindView(R.id.avatar_image_view)
    CircleImageView avatarImageView;
    @BindView(R.id.name_text_view)
    EditText nameTextView;

    @BindView(R.id.phone_text_view)
    TextView phoneTextView;



    private static final int REQ_CODE_PERMISSION = 1;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.avatar_container)
    RelativeLayout avatarContainer;

    @BindView(R.id.phone_container)
    RelativeLayout phoneContainer;
    @BindView(R.id.name_container)
    RelativeLayout nameContainer;
    private String url;
    private String nickname;


    @Override
    protected void onStart() {
        super.onStart();

        InputFilter[] filters = {getInputFilterProhibitEmoji(), getInputFilterProhibitSP()};
        nameTextView.setFilters(filters);
    }


 /*   private void fetchPersonalInfo() {
        if (mUser == null) {
            mUser = SharedPreferencesTool.getUser(this);
        }
        String token = SPUtils.getInstance("login").getString("token");

        EasyHttp.post(URLConfig.URL_USER_INFO)
                .params("token", token)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {

                        try {
                            UserProfileBean userProfileBean = GsonUtils.fromJson(s, UserProfileBean.class);

                                   if ("登录已失效，请重新登录!".equals(userProfileBean.getMsg())) {

                                    ToastUtils.show("登录已失效请重新登录");

                                    openLogin();

                                    return;
                                }
                            if (userProfileBean.getCode() == 200) {
                                String favatar = userProfileBean.getData().getFavatar();
                                url = favatar;

                                nickname = userProfileBean.getData().getFnickname();
                                Log.d("nnn",nickname);
                                if (!TextUtils.isEmpty(favatar) && favatar.startsWith("http") || favatar.startsWith("https")) {
                                    Glide.with(MeActivity.this).load(favatar).into(avatarImageView);
                                } else {
                                    Glide.with(MeActivity.this).load(R.drawable.head_portrait).into(avatarImageView);
                                }
                                nameTextView.setText(nickname);


                                String ftelephone = userProfileBean.getData().getFtelephone();
                                if (!TextUtils.isEmpty(ftelephone) && ftelephone.length() >= 11) {
                                    String reStr = ftelephone.substring(ftelephone.length() - 4, ftelephone.length());
                                    String preStr = ftelephone.substring(0, ftelephone.length() - 8);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(preStr).append("****").append(reStr);
                                    phoneTextView.setText(sb.toString());
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


    }*/

    private void submitPersonalInfo() {

    /*    String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.post(URLConfig.ModifyPersonal)
                .params("favatar", url)
                .params("fnickname", nameTextView.getText().toString())
                .params("token", token)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            Entity entity = GsonUtils.fromJson(s, Entity.class);
                            if (entity.getCode() == 200) {
                                ToastUtils.show("修改成功");
                                finish();
                            } else if (entity.getCode() == 401) {
                                ToastUtils.show("登录已失效请重新登录");
                                openLogin();

                            } else {
                                ToastUtils.show(entity.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/


    }

    /**
     * 上传头像
     * <p>
     * ;
     */
    private void uploadAvatar(String path) {

        final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                HttpLog.e(progress + "% ");
                dialog.setProgress(progress);
                dialog.setMessage(progress + "%");
                if (done) {//完成
                    dialog.setMessage("头像上传完成");
                }
            }
        };
        File file = new File(path);
        String token = SPUtils.getInstance("login").getString("token");
        Log.d("token", "...." + token);
      /*  EasyHttp.post(URLConfig.URL_IDENTITY_CARD_PHOTO)
                .params("token", token)
                .params("type", "8")
                .params("file", file, file.getName(), MediaType.parse("image/*"), listener)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
//                        ToastUtils.show("上传失败"+e.getMessage());

                    }

                    @Override
                    public void onSuccess(String string) {
                        try {
                            Gson gson = new Gson();
                            UpLoadBean upLoadBean = gson.fromJson(string, UpLoadBean.class);

                            if ("SUCCESS".equals(upLoadBean.getState())) {

                                tvSave.setEnabled(true);
                                tvSave.setTextColor(AppCompatResources.getColorStateList(MeActivity.this, R.color.black));
                                url = upLoadBean.getUrl();
                                if (mPersonalInfo == null) {
                                    mPersonalInfo = new PersonalInfo();
                                }

                                mPersonalInfo.setFavatar(url);
                                Glide.with(MeActivity.this).load(url).into(avatarImageView);

                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

    }


    private ProgressDialog dialog;
    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            if (dialog == null) {
                dialog = new ProgressDialog(MeActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
                dialog.setMessage("正在上传...");
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("头像上传");
                dialog.setMax(100);
            }
            return dialog;
        }
    };



    @OnClick({R.id.avatar_container, R.id.iv_go_back, R.id.tv_save,R.id.phone_container
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                String s = nameTextView.getText().toString().trim();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.show("昵称不能为空");
                } else {
                    submitPersonalInfo();
                }


                break;

            case R.id.iv_go_back:
                finish();
                break;
            case R.id.avatar_container:
                openAlbum();
                break;
            case   R.id.phone_container:
//                startActivity(new Intent(this,BindingPhoneValidationActivity.class));
                finish();
                break;


        }
    }




    /**
     * 打开相册或者 拍照
     */

    private void openAlbum() {


        IconDialog iconDialog = new IconDialog(this);
        iconDialog.show();
        iconDialog.setIconLister(new IconDialog.Icon() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_photos:
                        iconDialog.dismiss();
                        AndPermission.with(MeActivity.this)
                                .runtime()
                                .permission(Permission.Group.STORAGE)

                                .onGranted(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        //    接受权限
                                        PictureSelector.create(MeActivity.this).openGallery(ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                                .theme(R.style.choose_picture)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                                .imageSpanCount(4)// 每行显示个数 int
                                                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                                .previewImage(false)// 是否可预览图片 true or false
                                                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                                .enableCrop(true)// 是否裁剪 true or false
                                                .compress(true)// 是否压缩 true or false
                                                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                                                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                                                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code*/

                                    }
                                })
                                .onDenied(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
//       拒绝权限
                                        ToastUtils.show("请给予读取权限");
                                        if (AndPermission.hasAlwaysDeniedPermission(MeActivity.this, data)) {
                                            AndPermission.with(MeActivity.this)
                                                    .runtime()
                                                    .setting()
                                                    .start(REQ_CODE_PERMISSION);
                                        }
                                    }
                                })
                                .start();


                        break;
                    case R.id.tv_take_photos:
                        iconDialog.dismiss();
                        AndPermission.with(MeActivity.this)
                                .runtime()
                                .permission(
                                        Permission.WRITE_EXTERNAL_STORAGE,
                                        Permission.CAMERA
                                )
                                .onGranted(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        PictureSelector.create(MeActivity.this)
                                                .openCamera(ofImage())
                                                .compress(true)// 是否压缩 true or false
                                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                                .synOrAsy(false)//
                                                .enableCrop(true)// 是否裁剪 true or false
                                                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                                .forResult(PictureConfig.CAMERA);
                                    }
                                })
                                .onDenied(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        ToastUtils.show("请给予拍照权限");
                                        if (AndPermission.hasAlwaysDeniedPermission(MeActivity.this, data)) {
                                            AndPermission.with(MeActivity.this)
                                                    .runtime()
                                                    .setting()
                                                    .start(REQ_CODE_PERMISSION);
                                        }
                                    }
                                })
                                .start();
                        break;
                    case R.id.bt_cancel:
                        iconDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                if (data == null) {
                    return;
                }
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    uploadAvatar(selectList.get(0).getCompressPath());
                    PictureFileUtils.deleteCacheDirFile(MeActivity.this);

                }
            } else if (requestCode == PictureConfig.CAMERA) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                if (localMedia != null && localMedia.size() > 0) {
                    uploadAvatar(localMedia.get(0).getCompressPath());
                    PictureFileUtils.deleteCacheDirFile(MeActivity.this);
                }

            }
        }
    }






    /**
     * 过滤 表情
     */
    public InputFilter getInputFilterProhibitEmoji() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint)) {
                        buffer.append(codePoint);
                    } else {
//                        ToastUtil.show(ApplicationContext.getString(R.string
//                                .installment_contact_detail_address_face_tip));
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }

    public boolean getIsEmoji(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }

    /**
     * 过滤特殊符号
     */
    public InputFilter getInputFilterProhibitSP() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                try {
                    buffer = new StringBuffer();
                    for (int i = start; i < end; i++) {
                        char codePoint = source.charAt(i);
                        if (!getIsSp(codePoint)) {
                            buffer.append(codePoint);
                        } else {
//                        ToastUtil.show(ApplicationContext.getString(R.string
//                                .installment_contact_detail_address_error_tip));
                            i++;
                            continue;
                        }
                    }
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(buffer);
                        TextUtils.copySpansFrom((Spanned) source, start, end, null,
                                sp, 0);
                        return sp;
                    } else {
                        return buffer;
                    }
                } catch (Exception e) {
                    return buffer;
                }


            }

        };

        return filter;
    }

    public boolean getIsSp(char codePoint) {
        if (Character.getType(codePoint) > Character.LETTER_NUMBER) {
            return true;
        }
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
     return R.layout.activity_me;
    }
}
