package com.io.east.district.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.io.east.district.Constant;
import com.io.east.district.R;
import com.io.east.district.activity.MainActivity;
import com.io.east.district.login.LoginAndRegsterActivity;
import com.io.east.district.statusbar.UtilsStyle;
import com.io.east.district.utils.SharedPreferencesUtil;


public class StartActivity extends AppCompatActivity {
    private Handler handler = new Handler(Looper.getMainLooper());
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsStyle.setStatusBarMode(this, true);
        setContentView(R.layout.start);
        String token = SPUtils.getInstance("login").getString("token");
        if (SharedPreferencesUtil.getInstance().getInfo(Constant.IS_FIRST, false)) {

            if (!TextUtils.isEmpty(token)) {
                mIntent = new Intent(StartActivity.this, MainActivity.class);

            } else {
                mIntent = new Intent(StartActivity.this, LoginAndRegsterActivity.class);
            }
        } else {
            mIntent = new Intent(StartActivity.this, GuidanceActivity.class);

        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(mIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.scale_alpha);

            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
