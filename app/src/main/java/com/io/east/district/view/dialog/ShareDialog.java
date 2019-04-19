package com.io.east.district.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.io.east.district.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareDialog extends Dialog {
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.ll_moments)
    LinearLayout llMoments;

    public ShareDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_share);

        ButterKnife.bind(this);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;


        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.BOTTOM);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.ll_wechat, R.id.ll_qq, R.id.ll_moments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat:
                             /*   UMImage image = new UMImage(ShareActivity.this, R.drawable.shape);//网络图片
                image.compressStyle = UMImage.CompressStyle.SCALE;*///大小压缩，默认为大小压缩，适合普通很大的图
//                image.compressFormat = Bitmap.CompressFormat.PNG;
//                    image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//
//                UMImage image = new UMImage(ShareActivity.this, file);//本地文件
//                UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
//                UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
//                UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流

           /*     new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .setCallback(shareListener)//回调监听器
                        .withMedia(image)
                        .share();*/
                break;

            case R.id.ll_qq:
                ToastUtils.show("即将开放");
//                UMImage image2 = new UMImage(ShareActivity.this,  R.drawable.shape);//网络图片
//                image2.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
////                    image2.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                new ShareAction(ShareActivity.this)
//                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                        .setCallback(shareListener)//回调监听器
//                        .withMedia(image2)
//                        .share();

                break;
            case R.id.ll_moments:
                //                UMImage image3 = new UMImage(ShareActivity.this, R.drawable.shape);//网络图片
          /*      image3.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                    image3.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .setCallback(shareListener)//回调监听器
                        .withMedia(image3)
                        .share();*/

                break;
        }
    }


    private UMShareListener shareListener = new UMShareListener() {
        /*  *//* *
         * @descrption 分享开始的回调
         * @param *//*platform 平台类型
         /*/
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /*
         * @descrption 分享成功的回调
         * @param p*//*latform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.show("分享成功");
        }

        /*   *
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因*/

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show("分享失败");
        }

        /*   *
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show("分享取消");

        }
    };
}
