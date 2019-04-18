package com.io.east.district.me;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.io.east.district.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *   关于合伙人
 */
public class AboutPartnersActivity extends AppCompatActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_partners);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }
}
