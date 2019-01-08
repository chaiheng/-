package com.h.cheng.chain100.upload;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.login.PersonalDataActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MatisseUtils {

    public static void GoPhoto(Activity context, int resultcode, int picount) {

        Matisse.from(context)
                .choose(MimeType.ofImage()) // 选择 mime 的类型
                .countable(true) // 显示选择的数量
                .capture(true)  // 开启相机，和 captureStrategy 一并使用否则报错
                .captureStrategy(new CaptureStrategy(true, Constant.FILEPROVIDER)) // 拍照的图片路径
                .theme(R.style.Matisse_Dracula) // 黑色背景
                .maxSelectable(picount) // 图片选择的最多数量
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.grid_expected_size)) // 列表中显示的图片大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideAppEngine())// 使用的图片加载引擎
                .forResult(resultcode); // 设置作为标记的请求码，返回图片时使用

    }

    public static void GoPhotoFilter(Activity context, int resultcode, int picount) {

        Matisse.from(context)
                .choose(MimeType.ofImage()) // 选择 mime 的类型
                .countable(true) // 显示选择的数量
                .capture(true)  // 开启相机，和 captureStrategy 一并使用否则报错
                .captureStrategy(new CaptureStrategy(true, Constant.FILEPROVIDER)) // 拍照的图片路径
                .theme(R.style.Matisse_Dracula) // 黑色背景
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(picount) // 图片选择的最多数量
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.grid_expected_size)) // 列表中显示的图片大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideAppEngine())// 使用的图片加载引擎
                .forResult(resultcode); // 设置作为标记的请求码，返回图片时使用

    }


}
