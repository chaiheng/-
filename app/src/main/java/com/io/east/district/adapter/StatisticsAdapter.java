package com.io.east.district.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.io.east.district.R;
import com.io.east.district.bean.GroupManageBean;

import java.util.List;

/**
 *    小组管理统计
 */
public class StatisticsAdapter   extends BaseQuickAdapter<GroupManageBean.DataBeanX.DataBean, BaseViewHolder> {

    public StatisticsAdapter(int layoutResId, @Nullable List<GroupManageBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupManageBean.DataBeanX.DataBean item) {
        int group_count = item.getGroup_count();
        int level = item.getLevel();
        int partner_count = item.getPartner_count();
        double total_amount_sum = item.getTotal_amount_sum();
        if (level==1){
            helper.setBackgroundRes(R.id.iv_ranking,R.mipmap.first);
            helper.setVisible(R.id.tv_grade,false);

        }else if (level==2){
            helper.setBackgroundRes(R.id.iv_ranking,R.mipmap.second);
            helper.setVisible(R.id.tv_grade,false);

        }else if (level==3){
            helper.setBackgroundRes(R.id.iv_ranking,R.mipmap.third);
            helper.setVisible(R.id.tv_grade,false);

        }else {
            helper.setVisible(R.id.tv_grade,true);
            helper.setText(R.id.tv_grade,""+level);
            helper.setVisible(R.id.iv_ranking,false);
        }
        int is_partner = item.getUser().getIs_partner();
        if (is_partner==1){
         helper.setBackgroundRes(R.id.iv_partnership,R.mipmap.join);
        }
        helper.setText(R.id.tv_phone,item.getUser().getNickname());
        helper.setText(R.id.tv_crew,group_count+"人");
        helper.setText(R.id.tv_partner_number,partner_count+"人");
        helper.setText(R.id.tv_total_sales_volume,""+total_amount_sum);
    }
}
