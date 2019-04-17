package com.io.east.district;

import android.os.Environment;

/**
 * 常量，SP数据的key
 */

public final class Constant {
    public static String FILEPROVIDER = "com.h.cheng.chain100.fileprovider";
    public static String APP_SAVE_PATH = Environment.getExternalStorageDirectory()+"/chain100/file";
    public static final String IS_FIRST = "isFirst";
    public static final String IS_LOGIN = "isLogin";
    public static final String LOGIN_PHONE = "login_phone";
    public static final String API_TOKEN = "api_token";
    public static final String IS_SETUSER = "is_setuser";//记录是否已经设置过个人资料
}

