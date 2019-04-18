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
 * 拍照 相册
 *
 * @author Administrator
 */
public class IconDialog extends Dialog {
    @BindView(R.id.tv_photos)
    TextView tvPhotos;
    @BindView(R.id.tv_take_photos)
    TextView tvTakePhotos;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    private Context context;

    public Icon myIcon;

    public IconDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_icon);
        ButterKnife.bind(this);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;

//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();

        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.BOTTOM);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.tv_photos, R.id.tv_take_photos, R.id.bt_cancel})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_photos:
            case R.id.tv_take_photos:
            case R.id.bt_cancel:
                if (myIcon != null) {
                    myIcon.onClick(view);
                }

                dismiss();
                break;


        }
    }




    public interface Icon {
        void onClick(View view);
    }

    public void setIconLister(Icon myIcon) {
        this.myIcon = myIcon;
    }
}
