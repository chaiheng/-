package com.io.east.district.login;

import android.text.TextUtils;

import com.io.east.district.Constant;
import com.io.east.district.base.BaseObserver;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.utils.SharedPreferencesUtil;

/**
 * 作者： ch
 * 时间： 2018/3/21 0021-下午 4:13
 * 描述：
 * 来源：
 */


public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView baseView) {
        super(baseView);
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

    /**
     * 注册
     *
     * @param tel
     */
    public void regex(String tel, String password, String msgcode, String inviteCode, String confirmPassword) {

        addDisposable(apiServer.regex(tel, password, msgcode, inviteCode, confirmPassword), new BaseObserver<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                if (!TextUtils.isEmpty(o)) {
                    SharedPreferencesUtil.getInstance().saveInfo(Constant.API_TOKEN, o);
                }
                baseView.onRegisterSucc();

            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }


}
