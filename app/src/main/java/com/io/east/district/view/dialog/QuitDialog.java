package com.io.east.district.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.io.east.district.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退出
 *
 * @author Administrator
 */
public class QuitDialog extends Dialog {
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    public Quit mQuit;

    public QuitDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_quit);

        ButterKnife.bind(this);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;


        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.BOTTOM);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);
    }


    @OnClick({R.id.tv_quit, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
            case R.id.bt_cancel:
                if (mQuit != null) {
                    mQuit.onClick(view);
                }
                dismiss();
                break;
        }
    }

    public void setQuit(Quit mQuit) {
        this.mQuit = mQuit;
    }

    public interface Quit {
        void onClick(View view);
    }
}
