package com.io.east.district.downfile;

import com.io.east.district.base.BaseObserver;
import com.io.east.district.base.BaseView;

import java.io.File;

/**
 * 作者： ch
 * 时间： 2016/12/27.13:56
 * 描述：
 * 来源：
 */


public abstract class FileObserver extends BaseObserver<String> {


    public FileObserver(BaseView view) {
        super(view);
    }

    @Override
    protected void onStart() {
        if (view != null) {
            view.showLoadingFileDialog();
        }
    }

    @Override
    public void onComplete() {
        if (view != null) {
            view.hideLoadingFileDialog();
        }
    }

    @Override
    public void onNext(String path) {
        File file = new File(path);
        if (file != null && file.exists()) {
            onSuccess(file);
        } else {
            onError("file is null or a file does not exist");
        }
    }

    @Override
    public void onSuccess(String body) {

    }


    public abstract void onSuccess(File file);

    public abstract void onError(String msg);

}
