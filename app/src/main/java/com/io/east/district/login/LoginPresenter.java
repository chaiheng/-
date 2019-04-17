package com.io.east.district.login;

import com.io.east.district.Constant;
import com.io.east.district.MyApplication;
import com.io.east.district.base.BaseObserver;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.LoginResponse;
import com.io.east.district.utils.SharedPreferencesUtil;

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
