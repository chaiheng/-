package com.io.east.district.utils;

import android.app.Activity;
import android.content.Intent;

import com.io.east.district.Constant;
import com.io.east.district.login.LoginActivity;

public class LoginUtils {

    public static void exitLogin(Activity context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_SETUSER, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.API_TOKEN, "");
        context.finish();
    }
}
