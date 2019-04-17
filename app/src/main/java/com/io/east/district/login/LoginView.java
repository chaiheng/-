package com.io.east.district.login;

import com.io.east.district.base.BaseView;

/**
 * 作者： ch
 * 时间： 2018/12
 * 描述：
 * 来源：
 */


public interface LoginView extends BaseView {
    void onLoginSucc(String nickname);

    void onSendSucc();

}
