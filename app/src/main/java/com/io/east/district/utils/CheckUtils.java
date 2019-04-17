package com.io.east.district.utils;

import android.text.TextUtils;

public class CheckUtils {

    /**
     * 6-18位字母加数字 区分大小写 正则表达式验证
     *
     * @param pass
     * @return
     */
    public static boolean validatePhonePass(String pass) {
        String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        return !TextUtils.isEmpty(pass) && pass.matches(passRegex);
    }

    /**
     * 判断是否是手机号码 正则表达式验证
     *
     * @param phone
     * @return
     */
    public static boolean validatePhone(String phone) {
        //

        String passRegex = "^(13\\d|14[014-9]|15[0-35-9]|16[67]|17[0-8]|18\\d|19[189])\\d{8}$";
        return !TextUtils.isEmpty(phone) && phone.matches(passRegex);
    }

}
