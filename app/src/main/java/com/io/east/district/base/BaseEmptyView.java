package com.io.east.district.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.io.east.district.R;


/**
 * Created by mayn on 2018/6/27.
 */

public class BaseEmptyView extends FrameLayout {

    private String msg;

    public BaseEmptyView(@NonNull Context context, String msg) {
        super(context);
        this.msg = msg;
        initView(context);
    }

    public BaseEmptyView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_no_content, null);

        addView(view);
    }

    public BaseEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseEmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
}
