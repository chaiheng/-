package com.io.east.district.callback;

import com.io.east.district.event.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomSignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截请求，获取到该次请求的request
        Request request = chain.request();
        // 执行本次网络请求操作，返回response信息
        Response response = chain.proceed(request);
        int code = response.code();
        if (code==10001){
            EventBus.getDefault().post(new LogoutEvent(true));
        }
        return response;
    }
}
