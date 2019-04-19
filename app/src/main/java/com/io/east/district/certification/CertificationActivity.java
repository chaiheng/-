package com.io.east.district.certification;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.view.dialog.IconDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;


/**
 * 身份认证
 *
 * @author Administrator
 */
public class CertificationActivity extends BaseActivity {


    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_ID_number)
    EditText etIDNumber;
    @BindView(R.id.ll_credentials)
    LinearLayout llCredentials;
    @BindView(R.id.iv_front)
    ImageView ivFront;
    @BindView(R.id.iv_verso)
    ImageView ivVerso;
    @BindView(R.id.ll_upload_pictures)
    LinearLayout llUploadPictures;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_ID_No)
    TextView tvIDNo;
    @BindView(R.id.iv_positive)
    ImageView ivPositive;
    @BindView(R.id.iv_contrary)
    ImageView ivContrary;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private static final int REQ_CODE_PERMISSION = 3;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.iv_camera2)
    ImageView ivCamera2;
    @BindView(R.id.ll_audit_status)
    LinearLayout llAuditStatus;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_positive_hints)
    TextView tvPositiveHints;
    @BindView(R.id.tv_reverse_hints)
    TextView tvReverseHints;
    private int state = 0;
    private String frontUrl;
    private String reverseUrl;

    private String name;
    private String idNumber;


    private List<String> mData = new ArrayList<>();
    private String status;
//    private boolean afresh;


    @Override
    protected void onStart() {
        super.onStart();

        InputFilter[] filters = {getInputFilterProhibitEmoji(), getInputFilterProhibitSP()};
        etName.setFilters(filters);
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        String fcode = intent.getStringExtra("fcode");
        String fname = intent.getStringExtra("fname");
        String remark = intent.getStringExtra("remark");
        String idCardZmImgURL = intent.getStringExtra("idCardZmImgURL");
        String idCardFmImgURL = intent.getStringExtra("idCardFmImgURL");

        if (!TextUtils.isEmpty(status)){
            switch (status) {
                case "succeed":
                    tvName.setText(fname);
                    tvIDNo.setText(fcode);

                    tvMsg.setVisibility(View.GONE);
                    Glide.with(CertificationActivity.this).load(idCardZmImgURL).into(ivPositive);
                    Glide.with(CertificationActivity.this).load(idCardFmImgURL).into(ivContrary);
                    tvSubmit.setVisibility(View.GONE);
                    llCredentials.setVisibility(View.GONE);
                    llUploadPictures.setVisibility(View.GONE);
                    llAuditStatus.setVisibility(View.VISIBLE);
                    ivStatus.setImageResource(R.mipmap.succ);
                    tvPrompt.setTextColor(AppCompatResources.getColorStateList(CertificationActivity.this, R.color.black));
                    tvPrompt.setText("审核成功");
                    tvPrompt.setTextSize(18f);
                    btConfirm.setText("确定");
                    break;

                case "failure":

                    tvName.setText(fname);
                    tvIDNo.setText(fcode);


                    Glide.with(CertificationActivity.this).load(idCardZmImgURL).into(ivPositive);
                    Glide.with(CertificationActivity.this).load(idCardFmImgURL).into(ivContrary);
                    tvSubmit.setVisibility(View.GONE);
                    llCredentials.setVisibility(View.GONE);
                    llUploadPictures.setVisibility(View.GONE);
                    llAuditStatus.setVisibility(View.VISIBLE);

                    ivStatus.setImageResource(R.mipmap.failure);
                    tvPrompt.setTextColor(AppCompatResources.getColorStateList(CertificationActivity.this, R.color.black));
                    tvPrompt.setText("审核失败");
                    tvPrompt.setTextSize(18f);
                    tvMsg.setVisibility(View.VISIBLE);
                    tvMsg.setText(remark);
                    btConfirm.setText("重新上传");
                    break;

                case "audit":
                    tvName.setText(fname);
                    tvIDNo.setText(fcode);

                    Glide.with(CertificationActivity.this).load(idCardZmImgURL).into(ivPositive);
                    Glide.with(CertificationActivity.this).load(idCardFmImgURL).into(ivContrary);
                    tvSubmit.setVisibility(View.GONE);
                    llCredentials.setVisibility(View.GONE);
                    llUploadPictures.setVisibility(View.GONE);
                    llAuditStatus.setVisibility(View.VISIBLE);
                    ivStatus.setImageResource(R.mipmap.audit);
                    tvMsg.setVisibility(View.GONE);
                    tvPrompt.setTextColor(AppCompatResources.getColorStateList(CertificationActivity.this, R.color.hint_color));
                    tvPrompt.setText("大约需要1～2个工作日，请耐心等候");
                    btConfirm.setText("确定");
                    break;

                case "no":

                    tvSubmit.setVisibility(View.VISIBLE);
                    llCredentials.setVisibility(View.VISIBLE);
                    llUploadPictures.setVisibility(View.VISIBLE);
                    llAuditStatus.setVisibility(View.GONE);
                    break;
                default:

                    tvSubmit.setVisibility(View.VISIBLE);
                    llCredentials.setVisibility(View.VISIBLE);
                    llUploadPictures.setVisibility(View.VISIBLE);
                    llAuditStatus.setVisibility(View.GONE);
                    break;


            }
        }


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setState();
            }
        });


        etIDNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                setState();
            }
        });
    }


    private void setState() {


        String s = etName.getText().toString().trim();
        String s2 = etIDNumber.getText().toString().trim();
        if (s.length() > 0 && s2.length() == 18 && mData.size() == 2) {
            tvSubmit.setEnabled(true);
            tvSubmit.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        }
    }

    @OnClick({R.id.iv_go_back, R.id.tv_submit, R.id.iv_front, R.id.iv_verso, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.tv_submit:
                name = etName.getText().toString().trim();
                boolean zh = RegexUtils.isZh(name);

                if (name.length() < 2) {
                    ToastUtils.show("姓名长度不能少于2位");
                    return;
                }
                if (!zh) {
                    ToastUtils.show("姓名必须为汉字");
                    return;
                }

                idNumber = etIDNumber.getText().toString().trim();
                boolean idCard18Exact = RegexUtils.isIDCard18(idNumber);


                if (idCard18Exact) {
                    certification(name, "7", idNumber, frontUrl, reverseUrl);
                } else {

                    ToastUtils.show("身份证号不合法");
                }


                break;
            case R.id.iv_front:
                state = 1;
                showChoice();
                break;

            case R.id.iv_verso:
                state = 2;
                showChoice();
                break;


            case R.id.bt_confirm:

                if (btConfirm.getText().toString().equals("重新上传")) {
                    llAuditStatus.setVisibility(View.GONE);
                    tvSubmit.setVisibility(View.VISIBLE);
                    llCredentials.setVisibility(View.VISIBLE);
                    llUploadPictures.setVisibility(View.VISIBLE);
//                    afresh = true;
                } else {
                    finish();
                }

                break;
            default:
                break;

        }
    }


    private Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, Object data, RequestExecutor executor) {
            executor.execute();

            // When the user interrupts the request:
//            executor.cancel();
        }


    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            if (requestCode == PictureConfig.CAMERA) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                if (localMedia != null && localMedia.size() > 0) {
//
                    if (state == 1) {
//                           正面
                        LocalMedia localMedia1 = localMedia.get(0);
                        String path = localMedia1.getCompressPath();
                        ivCamera.setVisibility(View.GONE);
                        tvPositiveHints.setVisibility(View.GONE);
                        Glide.with(this).load(path).into(ivFront);
                        mData.add(path);
                        setState();
                        uploading(path);

                    } else if (state == 2) {
//                           背面
                        LocalMedia localMedia1 = localMedia.get(0);
                        String path = localMedia1.getCompressPath();
                        ivCamera2.setVisibility(View.GONE);
                        tvReverseHints.setVisibility(View.GONE);
                        Glide.with(this).load(path).into(ivVerso);
                        mData.add(path);
                        setState();
                        uploading(path);

                    }
                }

            } else if (requestCode == PictureConfig.CHOOSE_REQUEST) {
//                    相册

                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {

                    if (state == 1) {
//                           正面
                        LocalMedia localMedia1 = selectList.get(0);
                        String path = localMedia1.getCompressPath();
                        ivCamera.setVisibility(View.GONE);
                        Glide.with(this).load(path).into(ivFront);
                        mData.add(path);
                        setState();
                        uploading(path);

                    } else if (state == 2) {
//                           背面
                        LocalMedia localMedia1 = selectList.get(0);
                        String path = localMedia1.getCompressPath();
                        ivCamera2.setVisibility(View.GONE);
                        Glide.with(this).load(path).into(ivVerso);
                        uploading(path);
                        mData.add(path);
                        setState();
                    }

                }
            }
        }
    }

    /*
     *   上传身份证
     * */
    private void uploading(String path) {


        final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                HttpLog.e(progress + "% ");
                dialog.setProgress(progress);
                dialog.setMessage(progress + "%");
                if (done) {//完成
                    dialog.setMessage("上传完成");
                }
            }
        };
        File file = new File(path);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type: multipart/form-data"), file);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        String token = SPUtils.getInstance("login").getString("token");
        Log.d("token", "...." + token);
       /* EasyHttp.post(URLConfig.URL_IDENTITY_CARD_PHOTO)
                .params("token", token)
                .params("type", "7")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
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
                            UpLoadBean upLoadBean = GsonUtils.fromJson(string, UpLoadBean.class);
                            if ("SUCCESS".equals(upLoadBean.getState())) {
                                if (state == 1) {
//                             正面
                                    frontUrl = upLoadBean.getUrl();
                                    Log.d("frontUrl", "...." + frontUrl);

                                } else if (state == 2) {
//                               反面
                                    reverseUrl = upLoadBean.getUrl();
                                    Log.d("reverseUrl", "...." + reverseUrl);
                                }
                            }
                        } catch (JsonParseException e) {
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
                dialog = new ProgressDialog(CertificationActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
                dialog.setMessage("正在上传...");
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("图片上传");
                dialog.setMax(100);
            }
            return dialog;
        }
    };
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {


            tvSubmit.setVisibility(View.GONE);
            llCredentials.setVisibility(View.GONE);
            llUploadPictures.setVisibility(View.GONE);
            llAuditStatus.setVisibility(View.VISIBLE);
            ivStatus.setImageResource(R.mipmap.audit);
            tvMsg.setVisibility(View.GONE);
            tvPrompt.setTextColor(AppCompatResources.getColorStateList(CertificationActivity.this, R.color.hint_color));
            tvPrompt.setText("大约需要1～2个工作日，请耐心等候");
            btConfirm.setText("确定");
            tvSubmit.setVisibility(View.GONE);
            llCredentials.setVisibility(View.GONE);
            llUploadPictures.setVisibility(View.GONE);
            llAuditStatus.setVisibility(View.VISIBLE);
            tvName.setText(name);
            tvIDNo.setText(idNumber);

  /*          if (identificationBean != null) {
                Glide.with(CertificationActivity.this).load(frontUrl)
                        .into(ivPositive);
                Glide.with(CertificationActivity.this).load(reverseUrl)
                        .into(ivContrary);
            }
*/

            return false;
        }
    });

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }

    /**
     * 认证身份证
     */

    private void certification(String realname, String identitytype, String identityno,
                               String idCardZmImgURL, String idCardFmImgURL) {
        String token = SPUtils.getInstance("login").getString("token");
        String secretKey = SPUtils.getInstance("login").getString("secretKey");

        Map<String, String> params = new HashMap<>();


    }


    private void showChoice() {


        IconDialog iconDialog = new IconDialog(this);
        iconDialog.show();
        iconDialog.setIconLister(new IconDialog.Icon() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {


                    case R.id.tv_photos:
                        iconDialog.dismiss();
                        AndPermission.with(CertificationActivity.this)
                                .runtime()
                                .permission(Permission.Group.STORAGE)
                                .rationale(mRationale)
                                .onGranted(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        //    接受权限
                                        PictureSelector.create(CertificationActivity.this).openGallery(ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                                .theme(R.style.choose_picture)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                                .imageSpanCount(4)// 每行显示个数 int
                                                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                                .previewImage(false)// 是否可预览图片 true or false
                                                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                                .enableCrop(true)// 是否裁剪 true or false
                                                .compress(true)// 是否压缩 true or false
                                                .withAspectRatio(3, 2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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
                                        if (AndPermission.hasAlwaysDeniedPermission(CertificationActivity.this, data)) {
                                            AndPermission.with(CertificationActivity.this)
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
                        AndPermission.with(CertificationActivity.this)
                                .runtime()
                                .permission(
                                        Permission.WRITE_EXTERNAL_STORAGE,
                                        Permission.CAMERA
                                ).rationale(mRationale)
                                .onGranted(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        PictureSelector.create(CertificationActivity.this)


                                                .openCamera(ofImage())
                                                .compress(true)// 是否压缩 true or false
                                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                                .synOrAsy(false)//
                                                .enableCrop(true)// 是否裁剪 true or false
                                                .compress(true)// 是否压缩 true or false
                                                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                                .showCropGrid(true)// 是
                                                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                                                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                                                .withAspectRatio(3, 2)
                                                .forResult(PictureConfig.CAMERA);
                                    }
                                })
                                .onDenied(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {

                                        ToastUtils.show("请给予拍照权限");
                                        if (AndPermission.hasAlwaysDeniedPermission(CertificationActivity.this, data)) {
                                            AndPermission.with(CertificationActivity.this)
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
