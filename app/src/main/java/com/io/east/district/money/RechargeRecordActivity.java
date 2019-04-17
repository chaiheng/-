package com.io.east.district.money;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.east.district.R;
import com.io.east.district.adapter.RecordAdapter;
import com.io.east.district.base.BaseActivity;
import com.io.east.district.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeRecordActivity extends BaseActivity {

    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    private List<String> mData  =  new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_record;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecord.setLayoutManager(layoutManager);
        RecordAdapter  recordAdapter  = new RecordAdapter(R.layout.item_record,mData);
        rvRecord.setAdapter(recordAdapter);
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked() {
        finish();
    }
}
