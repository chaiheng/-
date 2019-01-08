package com.h.cheng.chain100.upload;

import java.io.File;

/**
 * Created by Administrator on 2018/8/10.
 */

public interface LubanInterface {
    void onStart();
    void onSuccess(File file);
    void onError(Throwable e);
}
