package com.io.east.district.callback;

import com.zhouyou.http.model.ApiResult;

public class BaseApiResult<T> extends ApiResult<T> {


    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {//请求成功的判断方法
        return code == 1 ? true : false;
    }
}