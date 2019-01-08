package com.h.cheng.chain100.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.h.cheng.chain100.Constant;
import com.h.cheng.chain100.R;
import com.h.cheng.chain100.base.BaseFragmentV2;
import com.h.cheng.chain100.login.LoginActivity;
import com.h.cheng.chain100.login.LoginAndRegsterActivity;
import com.h.cheng.chain100.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 引导页
 */

public class GuidanceFragment extends BaseFragmentV2 {
    @BindView(R.id.iv_guidance)
    ImageView ivGuidance;
    private int page;


    public static GuidanceFragment newInstance(int i) {
        GuidanceFragment guidanceFragment = new GuidanceFragment();
        guidanceFragment.page = i;
        return guidanceFragment;
    }


    @Override
    protected void init() {
        switch (page) {
            case 1:
                ivGuidance.setImageResource(R.mipmap.guidance_01);
                break;
            case 2:
                ivGuidance.setImageResource(R.mipmap.guidance_02);
                break;
            case 3:
                ivGuidance.setImageResource(R.mipmap.guidance_03);
                ivGuidance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, LoginAndRegsterActivity.class));
                        SharedPreferencesUtil.getInstance().saveInfo(Constant.IS_FIRST, true);
                        if (getActivity() != null && !getActivity().isFinishing()) {
                            getActivity().finish();
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_guidance;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
