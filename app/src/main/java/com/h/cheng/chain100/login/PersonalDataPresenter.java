package com.h.cheng.chain100.login;

import android.text.TextUtils;

import com.h.cheng.chain100.base.BaseObserver;
import com.h.cheng.chain100.base.BasePresenter;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者： ch
 * 时间： 2018/3/21 0021-下午 4:13
 * 描述：
 * 来源：
 */


public class PersonalDataPresenter extends BasePresenter<PersonalDataView> {
    public PersonalDataPresenter(PersonalDataView baseView) {
        super(baseView);
    }

    /**
     * 设置用户信息
     *
     */
    public void SetUserInfo(String path, String nickname) {
        addDisposable(apiServer.SetUserInfo(path, nickname), new BaseObserver<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.SetUserInfoSucc();

            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }

    /**
     * 上传头像
     *
     * @param path
     */
    public void upload(String path) {

        File file = new File(path);
        //  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        addDisposable(apiServer.upload(body), new BaseObserver<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                if (!TextUtils.isEmpty(o)) {
                    baseView.UploadImageSucc(o);
                }


            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }


}
