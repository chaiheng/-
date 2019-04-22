package com.io.east.district.callback;

import com.zhouyou.http.model.ApiResult;

public class BaseApiResult<T> extends ApiResult<T> {

    @Override
    public int getCode() {
        return super.getCode();
    }

    @Override
    public boolean isOk() {
        return getCode()==1;
    }
}
