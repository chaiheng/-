package com.io.east.district.base;

import java.io.Serializable;

public class BaseModel<T> implements Serializable {
    private String code;
    private String message;
    private T result;
    public String getErrcode() {
        return code;
    }

    public void setErrcode(String errcode) {
        this.code = errcode;
    }

    public String getErrmsg() {
        return message;
    }

    public void setErrmsg(String errmsg) {
        this.message = errmsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}