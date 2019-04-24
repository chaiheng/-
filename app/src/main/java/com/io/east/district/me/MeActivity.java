package com.io.east.district.me;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.BaseEntity;
import com.io.east.district.bean.UserProBean;
import com.io.east.district.utils.OSSClientUtil;
import com.io.east.district.view.CircleImageView;
import com.io.east.district.view.dialog.IconDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public void initData() {
        super.initData();
        fetchPersonalInfo();
    }

    private void fetchPersonalInfo() {

        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.read)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        UserProBean userProBean = JSON.parseObject(s, UserProBean.class);
                        if (userProBean.getCode() == 1) {
                            String avatar = userProBean.getData().getAvatar();
                            String mobile = userProBean.getData().getMobile();
                            String nickname = userProBean.getData().getNickname();
                            nameTextView.setText(nickname);
                            if (!TextUtils.isEmpty(avatar) && avatar.startsWith("http") || avatar.startsWith("https")) {
                                Glide.with(MeActivity.this).load(avatar).into(avatarImageView);
                            } else {
                                Glide.with(MeActivity.this).load(R.mipmap.photo).into(avatarImageView);
                            }

                            if (!TextUtils.isEmpty(mobile) && mobile.length() >= 11) {
                                String reStr = mobile.substring(mobile.length() - 4, mobile.length());
                                String preStr = mobile.substring(0, mobile.length() - 8);
                                StringBuilder sb = new StringBuilder();
                                sb.append(preStr).append("****").append(reStr);
                                phoneTextView.setText(sb.toString());
                            }

                        }


                    }
                });


    }

    private void submitPersonalInfo() {

        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.put(UrlDeploy.modified)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .params("avatar", url)
                .params("nickname", nameTextView.getText().toString())

                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            BaseEntity baseEntity = JSON.parseObject(s, BaseEntity.class);
                            if (baseEntity.getCode() == 1) {
                                ToastUtils.show("修改成功");
                                finish();
                            } else {
                                ToastUtils.show(baseEntity.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    /**
     * 上传头像
     * <p>
     * ;
     */
    private void uploadAvatar(String path) {


//        File file = new File(path);
        String token = SPUtils.getInstance("login").getString("token");
        Log.d("token", "...." + token);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String objectKey = "qdd/headPortrait/" + time + ".jpg";
        OSSClientUtil ossClientUtil = new OSSClientUtil(this);
        PutObjectRequest put = new PutObjectRequest("<bucketName>", objectKey, path);
        ossClientUtil.ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                String imgUrl = ossClientUtil.getImgUrl("", objectKey);

                Glide.with(MeActivity.this).load(imgUrl).into(avatarImageView);

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });


    }


    @OnClick({R.id.avatar_container, R.id.iv_go_back, R.id.tv_save, R.id.phone_container
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
