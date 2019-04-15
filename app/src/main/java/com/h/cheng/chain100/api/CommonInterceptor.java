package com.h.cheng.chain100.api;

import android.os.Build;
import android.text.TextUtils;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.MyApplication;
import com.h.cheng.chain100.utils.EncodeUtils;
import com.h.cheng.chain100.utils.EncryptUtil;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共参数
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数
        String datetime = String.valueOf(new Date().getTime());
        String token = SharedPreferencesUtil.getInstance().getInfo(Constant.API_TOKEN);
        if (TextUtils.isEmpty(token)) {
            token = "";
        }
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("system", "android")
                .addQueryParameter("device_name", Build.BRAND + ":" + Build.MODEL)
//                .addQueryParameter("platform", "coolf")
                .addQueryParameter("app_name", "chain100")
                .addQueryParameter("app_version", MyApplication.getInstance().GetVersion())
                .addQueryParameter("pp_version", String.valueOf(MyApplication.getInstance().GetVersionCode()))
                .addQueryParameter("datetime", datetime)
                .addQueryParameter("device_id", MyApplication.getImei())
//                .addQueryParameter("sign", EncryptUtil.encrypt(datetime, "625202f9149e061d", "5efd3f6060e20330"))
//                .addQueryParameter("secret", EncodeUtils.enCode(datetime))
                .addQueryParameter("system_version", android.os.Build.VERSION.RELEASE);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .addHeader("token", token)
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
