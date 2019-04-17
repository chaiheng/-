package com.io.east.district.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.io.east.district.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 取消订单
 */
public class CancelOrderDialog extends Dialog {
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private CancelOrder cancelOrder;

    public CancelOrderDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_cancel_order);
        ButterKnife.bind(this);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;


        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.CENTER);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.tv_confirm, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (cancelOrder != null) {
                    cancelOrder.onClick(view);
                }


                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface CancelOrder {
        void onClick(View view);
    }

    public void setCancelOrderLister(CancelOrder cancelOrder) {
        this.cancelOrder = cancelOrder;
    }
}
