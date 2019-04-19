package com.io.east.district.view.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.hjq.toast.ToastUtils;
import com.io.east.district.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * 服务弹窗
 */
public class ServiceDialog extends Dialog {
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    private Context context;

    public ServiceDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_dialog);
        ButterKnife.bind(this);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
        setCanceledOnTouchOutside(true);

    }





    @OnClick({R.id.iv_cancel, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.tv_copy:
                String string = tvMail.getText().toString();
                //创建一个新的文本clip对象
                ClipData mClipData = ClipData.newPlainText("Simple test", string);
                //把clip对象放在剪贴板中

                ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                mClipboardManager.setPrimaryClip(mClipData);
                ToastUtils.show("复制成功");
                dismiss();
                break;
        }
    }
}
