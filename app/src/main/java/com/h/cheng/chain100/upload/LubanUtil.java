package com.h.cheng.chain100.upload;

import android.text.TextUtils;

import com.h.cheng.chain100.MyApplication;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by liunw on 2018/8/10.
 */

public class LubanUtil {
    private final int NO_PRESS_VALUE = 100;
    private static volatile LubanUtil instance = null;

    private LubanUtil() {
    }

    public static LubanUtil getInstance() {
        if (instance == null) {
            synchronized (LubanUtil.class) {
                if (instance == null) {
                    instance = new LubanUtil();
                }
            }
        }

        return instance;
    }

    /**
     //    load	传入原图
     //    filter	设置开启压缩条件
     //    ignoreBy	不压缩的阈值，单位为K
     //    setFocusAlpha	设置是否保留透明通道
     //    setTargetDir	缓存压缩图片路径
     //    setCompressListener	压缩回调接口
     //    setRenameListener	压缩前重命名接口
     */
    public void pressImg(File oriFile, LubanInterface lubanListener) {
        Luban.with(MyApplication.getInstance())
                .load(oriFile)
                .ignoreBy(NO_PRESS_VALUE)
//                .setTargetDir(targetPath)
                .filter(mCompressCondition)
                .setCompressListener(new MyCompressListener(lubanListener))
                .launch();
    }

    public void pressImg(String oriPath, LubanInterface lubanListener) {
        Luban.with(MyApplication.getInstance())
                .load(oriPath)
                .ignoreBy(NO_PRESS_VALUE)
//                .setTargetDir(targetPath)
                .filter(mCompressCondition)
                .setCompressListener(new MyCompressListener(lubanListener))
                .launch();
    }
    private CompressionPredicate mCompressCondition = new CompressionPredicate() {
        @Override
        public boolean apply(String path) {
            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
        }
    };

    class MyCompressListener implements OnCompressListener {
        LubanInterface mLubanListener;
        MyCompressListener(LubanInterface lubanListener){
            mLubanListener = lubanListener;
        }
        @Override
        public void onStart() {
            // TODO 压缩开始前调用，可以在方法内启动 loading UI
            mLubanListener.onStart();
        }

        @Override
        public void onSuccess(File file) {
            // TODO 压缩成功后调用，返回压缩后的图片文件
            mLubanListener.onSuccess(file);
        }

        @Override
        public void onError(Throwable e) {
            // TODO 当压缩过程出现问题时调用
            mLubanListener.onError(e);
        }
    }

}
