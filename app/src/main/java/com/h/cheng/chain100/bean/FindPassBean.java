package com.h.cheng.chain100.bean;

public class FindPassBean {
    private String mobile;
    private String password;
    private String verifyCode;

    public FindPassBean(String mobile, String password, String verifyCode) {
        this.mobile = mobile;
        this.password = password;
        this.verifyCode = verifyCode;
    }
}
