package com.io.east.district.certification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.io.east.district.api.UrlDeploy;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.AddIdCardBean;
import com.io.east.district.bean.BaseEntity;
import com.io.east.district.bean.LooKVerifyBean;
import com.io.east.district.utils.OSSClientUtil;
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
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
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

    private boolean afresh;


    @Override
    protected void onStart() {
        super.onStart();

        InputFilter[] filters = {getInputFilterProhibitEmoji(), getInputFilterProhibitSP()};
        etName.setFilters(filters);
    }

    @Override
    public void initData() {
        super.initData();
        String token = SPUtils.getInstance("login").getString("token");
        EasyHttp.get(UrlDeploy.verify)
                .headers("XX-Token", token)
                .headers("XX-Device-Type", "android")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        JSONObject jsonObject = JSON.parseObject(s);
                        int code = jsonObject.getIntValue("code");

                        LooKVerifyBean looKVerifyBean = JSON.parseObject(s, LooKVerifyBean.class);
                        if (looKVerifyBean.getData()== null) {
//                               没有认证
                            tvPositiveHints.setVisibility(View.VISIBLE);
                            tvReverseHints.setVisibility(View.VISIBLE);
                            tvSubmit.setVisibility(View.VISIBLE);
                            llCredentials.setVisibility(View.VISIBLE);
                            llUploadPictures.setVisibility(View.VISIBLE);
                            llAuditStatus.setVisibility(View.GONE);
                        } else {

                            String country = looKVerifyBean.getData().getCountry();
                            String real_name = looKVerifyBean.getData().getReal_name();
                            String id_number = looKVerifyBean.getData().getId_number();
                            String img_front = looKVerifyBean.getData().getImg_front();
                            String img_reverse = looKVerifyBean.getData().getImg_reverse();
                            String remark = looKVerifyBean.getData().getRemark();
                            int status = looKVerifyBean.getData().getStatus();


                            switch (status) {
                                case 2:
//                                      成功
                                    tvName.setText(real_name);
                                    tvIDNo.setText(id_number);
                                    tvPositiveHints.setVisibility(View.GONE);
                                    tvReverseHints.setVisibility(View.GONE);
                                    tvMsg.setVisibility(View.GONE);
                                    Glide.with(CertificationActivity.this).load(img_front).into(ivPositive);
                                    Glide.with(CertificationActivity.this).load(img_reverse).into(ivContrary);
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

                                case 3:
//                          审核失败
                                    tvName.setText(real_name);
                                    tvIDNo.setText(id_number);

                                    Glide.with(CertificationActivity.this).load(img_front).into(ivPositive);
                                    Glide.with(CertificationActivity.this).load(img_reverse).into(ivContrary);
                                    tvSubmit.setVisibility(View.GONE);
                                    llCredentials.setVisibility(View.GONE);
                                    llUploadPictures.setVisibility(View.GONE);
                                    llAuditStatus.setVisibility(View.VISIBLE);
                                    tvPositiveHints.setVisibility(View.GONE);
                                    tvReverseHints.setVisibility(View.GONE);
                                    ivStatus.setImageResource(R.mipmap.failure);
                                    tvPrompt.setTextColor(AppCompatResources.getColorStateList(CertificationActivity.this, R.color.black));
                                    tvPrompt.setText("审核失败");
                                    tvPrompt.setTextSize(18f);
                                    tvMsg.setVisibility(View.VISIBLE);
                                    tvMsg.setText(remark);
                                    btConfirm.setText("重新上传");
                                    break;

                                case 1:
//                                        审核中
                                    tvName.setText(real_name);
                                    tvIDNo.setText(id_number);
                                    tvPositiveHints.setVisibility(View.GONE);
                                    tvReverseHints.setVisibility(View.GONE);
                                    Glide.with(CertificationActivity.this).load(img_front).into(ivPositive);
                                    Glide.with(CertificationActivity.this).load(img_reverse).into(ivContrary);
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


                                default:
                                    tvPositiveHints.setVisibility(View.VISIBLE);
                                    tvReverseHints.setVisibility(View.VISIBLE);
                                    tvSubmit.setVisibility(View.VISIBLE);
                                    llCredentials.setVisibility(View.VISIBLE);
                                    llUploadPictures.setVisibility(View.VISIBLE);
                                    llAuditStatus.setVisibility(View.GONE);
                                    break;


                            }
                        }
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();


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
                    if (afresh){
//                           修改 身份证

                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.put(UrlDeploy.ChangeVerify)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")
                                .params("real_name",name)
                                .params("id_number",idNumber)
                                .params("img_front",frontUrl)
                                .params("img_reverse",reverseUrl)
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        BaseEntity baseEntity = JSON.parseObject(s, BaseEntity.class);
                                        int code = baseEntity.getCode();
                                        if (code==1){
                                            ToastUtils.show("提交成功");
                                            tvPositiveHints.setVisibility(View.GONE);
                                            tvReverseHints.setVisibility(View.GONE);
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
                                        }else {
                                            ToastUtils.show(baseEntity.getMsg());
                                        }


                                    }
                                });

                    }else {
//                           添加身份证
                        String token = SPUtils.getInstance("login").getString("token");
                        EasyHttp.post(UrlDeploy.verify)
                                .headers("XX-Token", token)
                                .headers("XX-Device-Type", "android")
                                .params("real_name",name)
                                .params("id_number",idNumber)
                                .params("img_front",frontUrl)
                                .params("img_reverse",reverseUrl)
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onError(ApiException e) {

                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        AddIdCardBean addIdCardBean = JSON.parseObject(s, AddIdCardBean.class);
                                        int code = addIdCardBean.getCode();
                                        if (code==1){
                                          ToastUtils.show("提交成功");

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
                                        }else {
                                            ToastUtils.show(addIdCardBean.getMsg());
                                        }


                                    }
                                });
                    }
//

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
                    afresh = true;

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
                        tvPositiveHints.setVisibility(View.GONE);
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
                        tvReverseHints.setVisibility(View.GONE);
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


        String token = SPUtils.getInstance("login").getString("token");
        Log.d("token", "...." + token);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);


        String objectKey = "qdd/identityCard/" +time + ".jpg";
        OSSClientUtil ossClientUtil = new OSSClientUtil(this);

        PutObjectRequest put = new PutObjectRequest("<bucketName>", objectKey, path);
        ossClientUtil.ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                if (state == 1) {
//                             正面
                    frontUrl = ossClientUtil.getImgUrl("",objectKey);
                    Log.d("frontUrl", "...." + frontUrl);

                } else if (state == 2) {
//                               反面
                    reverseUrl =ossClientUtil.getImgUrl("",objectKey);
                    Log.d("reverseUrl", "...." + reverseUrl);
                }
                ToastUtils.show("上传成功");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                ToastUtils.show("上传失败");
                if (clientException != null) {
                    // Local exception, such as a network exception
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

//




    }



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
    }


    /**
     * 认证身份证
     */


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
                        ;
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


}
