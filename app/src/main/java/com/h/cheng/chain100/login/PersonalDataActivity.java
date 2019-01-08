package com.h.cheng.chain100.login;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.activity.MainActivity;
import com.h.cheng.chain100.base.BaseActivity;
import com.h.cheng.chain100.upload.LubanInterface;
import com.h.cheng.chain100.upload.LubanUtil;
import com.h.cheng.chain100.upload.MatisseUtils;
import com.h.cheng.chain100.utils.FileByUri;
import com.h.cheng.chain100.utils.ImageUtils;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PersonalDataActivity extends BaseActivity<PersonalDataPresenter> implements PersonalDataView {
    @BindView(R.id.header_back)
    ImageView headerBack;
    @BindView(R.id.header_tv_text)
    TextView headerTvText;
    @BindView(R.id.iv_addhead)
    ImageView ivAddhead;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private int REQUEST_CODE_CHOOSE = 99;
    private File myfile;

    @Override
    protected PersonalDataPresenter createPresenter() {
        return new PersonalDataPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_information;
    }

    @Override
    public void initData() {
        headerTvText.setText("基本资料");
        headerBack.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_addhead, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_addhead:
                readPic();
                break;
            case R.id.tv_submit:
                if (Check()) {
                    showLoading();
                    presenter.upload(myfile.getAbsolutePath());
                }
                break;
        }
    }

    public boolean Check() {

        if (myfile == null) {
            showError("请设置头像");
            return false;
        } else if (etNickname.getText().toString().length() == 0) {
            showError("请输入昵称");
            return false;
        }
        return true;
    }

    @Override
    public void UploadImageSucc(String path) {
        presenter.SetUserInfo(path, etNickname.getText().toString());
    }

    @Override
    public void SetUserInfoSucc() {
        hideLoading();
        showtoast("设置成功");
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_SETUSER, true);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void readPic() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            MatisseUtils.GoPhotoFilter(PersonalDataActivity.this, REQUEST_CODE_CHOOSE, 1);
                        } else {
                            showError("请确认权限通过");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                List<Uri> array = Matisse.obtainResult(data);
                if (array != null) {
                    if (array.size() > 0) {
                        myfile = FileByUri.getFilePathByUri(context, array.get(0));
                        if (myfile != null) {
                            LubanUtil.getInstance().pressImg(myfile, new LubanInterface() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    myfile = file;
                                    ImageUtils.setGlideImageUrl(context, ivAddhead, myfile.getAbsolutePath());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ImageUtils.setGlideImageUrl(context, ivAddhead, myfile.getAbsolutePath());
                                }
                            });
                        }
                    }
                }
            }

        }
    }
}
