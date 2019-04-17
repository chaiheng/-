package com.io.east.district.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class CountDownTimerUtils {


    public static void startCount(final TextView textView) {
        /**
         * 发送验证码倒计时
         */
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText("发送验证码");
            }
        };

        textView.setEnabled(false);
        countDownTimer.start();
    }

}
