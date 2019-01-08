package com.h.cheng.chain100.login;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.base.BaseObserver;
import com.h.cheng.chain100.base.BasePresenter;
import com.h.cheng.chain100.bean.LoginResponse;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;

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


public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @param code
     */
    public void login(String name, String pwd, String code) {

        addDisposable(apiServer.LoginByRx(name, pwd, code), new BaseObserver<LoginResponse>(baseView) {
            @Override
            public void onSuccess(LoginResponse o) {
                if (o != null && o.getToken() != null) {
                    SharedPreferencesUtil.getInstance().saveInfo(Constant.API_TOKEN, o.getToken());
                    MyApplication.APi_Token = o.getToken();
                    if (o.getNickName() == null) {
                        o.setNickName("");
                    }
                    baseView.onLoginSucc(o.getNickName());
                }



            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    public void SendMsg(String phone) {
        addDisposable(apiServer.SendMsg(phone), new BaseObserver<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onSendSucc();

            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }





}
