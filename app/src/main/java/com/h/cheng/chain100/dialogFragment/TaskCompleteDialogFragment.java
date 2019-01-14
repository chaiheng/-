package com.h.cheng.chain100.dialogFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.h.cheng.chain100.R;

/**
 * 任务完成弹窗
 */
public class TaskCompleteDialogFragment extends DialogFragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view=inflater.inflate(R.layout.fragment_task_complete_dialog, container, false);
        return view;
    }

}