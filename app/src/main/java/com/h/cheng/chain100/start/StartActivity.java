package com.h.cheng.chain100.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.activity.MainActivity;
import com.h.cheng.chain100.login.LoginAndRegsterActivity;
import com.h.cheng.chain100.statusbar.UtilsStyle;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {
    private Handler handler = new Handler(Looper.getMainLooper());
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsStyle.setStatusBarMode(this, true);
        if (SharedPreferencesUtil.getInstance().getInfo(Constant.IS_FIRST, false)) {
            if (SharedPreferencesUtil.getInstance().getInfo(Constant.IS_LOGIN, false)) {
//                if (SharedPreferencesUtil.getInstance().getInfo(Constant.IS_SETUSER, false)) {
                mIntent = new Intent(StartActivity.this, MainActivity.class);
//                } else {
//                    mIntent = new Intent(StartActivity.this, PersonalDataActivity.class);
//                }
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
