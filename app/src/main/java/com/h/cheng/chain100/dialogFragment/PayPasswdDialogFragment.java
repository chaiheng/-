package com.h.cheng.chain100.dialogFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.h.cheng.chain100.R;
import com.kenny.separatededittext.SeparatedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 支付密码输入弹窗
 */
public class PayPasswdDialogFragment extends DialogFragment {
    @BindView(R.id.passwd)
    SeparatedEditText passwd;
    Unbinder unbinder;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.pay_btn)
    Button payBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_pay_passwd_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
