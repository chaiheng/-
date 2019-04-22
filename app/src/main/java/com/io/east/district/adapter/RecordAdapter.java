package com.io.east.district.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.io.east.district.R;
import com.io.east.district.bean.RechargeRecordBean;

import java.util.List;

/**
 * 充值记录
 */
public class RecordAdapter extends BaseQuickAdapter<RechargeRecordBean.DataBean, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List<RechargeRecordBean.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordBean.DataBean item) {
        int pay_status = item.getPay_status();
        if (pay_status==1){
            helper.setText(R.id.tv_status,"充值成功");
            helper.setTextColor(R.id.tv_total,mContext.getResources().getColor(R.color.red));
        }else {
            helper.setText(R.id.tv_status,"交易取消");
            helper.setTextColor(R.id.tv_total,mContext.getResources().getColor(R.color.hint_color));
        }
        helper.setText(R.id.tv_time,item.getCreate_time_short());
        helper.setText(R.id.tv_price,item.getMoney());
        helper.setText(R.id.tv_sum,"x"+item.getNum());
        helper.setText(R.id.tv_total,"合计:"+item.getMoney());
        helper.setText(R.id.tv_convert,"≈"+item.getAmount()+" "+"BTA");

    }
}
