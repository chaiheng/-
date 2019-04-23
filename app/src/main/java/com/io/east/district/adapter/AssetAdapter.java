package com.io.east.district.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.io.east.district.R;
import com.io.east.district.bean.AssetsListBean;

import java.util.List;

/**
 *   资产列表
 */
public class AssetAdapter  extends BaseQuickAdapter<AssetsListBean.DataBeanX.DataBean, BaseViewHolder> {

    public AssetAdapter(int layoutResId, @Nullable List<AssetsListBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetsListBean.DataBeanX.DataBean item) {
        helper.setText(R.id.tv_phone,item.getCommission_user().getMobile());
        int type = item.getType();
        if (type==1){
            helper.setText(R.id.tv_type,"提成奖励收入");
        }else {
            helper.setText(R.id.tv_type,"提现购物支出");
        }

        helper.setText(R.id.tv_deposit,item.getMoney());
        helper.setText(R.id.tv_award,item.getCommission()+"D值");
        helper.setText(R.id.tv_award2,item.getCommission()+"D值");
        int status = item.getStatus();
        if (status==0){
            helper.setText(R.id.tv_status,"在途");
        }else {
            helper.setText(R.id.tv_status,"已收");
        }
        helper.setText(R.id.tv_time,item.getRecorded_time());
        helper.setText(R.id.tv_prepaid_time,item.getCreate_time());

    }
}
