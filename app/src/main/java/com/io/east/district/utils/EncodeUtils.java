package com.io.east.district.utils;

import android.util.Log;

import com.io.east.district.MyApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mayn on 2018/5/18.
 */

public class EncodeUtils {
    private static String singnConstant = "system=android&system_version=SYSTEM_VERSION&app_version=APP_VERSION&datetime=DATETIME&device_id=DEVICE_ID";
//              params.put("system","android");
//        params.put("system_version",android.os.Build.VERSION.RELEASE);
//        params.put("app_version",MyApplication.getInstance().
//
//    GetVersion());
//    String datatime = new Date().getTime() + "";
//        params.put("datetime",datatime);
//        params.put("device_id",MyApplication.getImei());

    public static String enCode(String dateTime) {
        String signStr = singnConstant.replace("SYSTEM_VERSION", android.os.Build.VERSION.RELEASE)
                .replace("APP_VERSION", MyApplication.getInstance().GetVersion())
                .replace("DATETIME", dateTime)
                .replace("DEVICE_ID", MyApplication.getImei());

        return getSha1(md5Str(signStr));
    }

    /**
     * 生成32位md5码
     *
     * @param sign
     * @return
     */
    public static String md5Str(String sign) {
        Log.d("sign", sign);
//        LogUtils.d("sign",sign);
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(sign.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            Log.d("md5", buffer.toString());
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            Log.d("sha1", new String(buf));
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

}
