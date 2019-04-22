package com.io.east.district.base;

public class BaseBody {

    private int code;
    private String msg;
    private String data;

    public boolean isOk() {
        return code == 1;
    }

    @Override
    public String toString() {
        return "BaseBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
