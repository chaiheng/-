package com.h.cheng.chain100.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.h.cheng.chain100.bean.GlideApp;

/**
 * Created by chai on 2018/6/1.
 */

public class ImageUtils {



    public static void setGlideImageUrl(Context context, ImageView imageView, String url) {
        try {
            if (context != null) {
                GlideApp.with(context)
                        .asBitmap()
                        .load(url)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .centerCrop()
//                        .error(defaultimage)
//                        .placeholder(defaultimage)
                        .into(imageView);
            }
        } catch (Exception ex) {
        }
    }



}
