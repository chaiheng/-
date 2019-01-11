package com.h.cheng.chain100.utils;

import android.app.Activity;
import android.content.Intent;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.login.LoginActivity;

public class LoginUtils {

    public static void exitLogin(Activity context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_SETUSER, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_LOGIN, false);
        SharedPreferencesUtil.getInstance().saveInfo(Constant.API_TOKEN, "");
        context.finish();
    }
}
