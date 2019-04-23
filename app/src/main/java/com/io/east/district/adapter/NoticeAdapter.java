package com.io.east.district.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.io.east.district.R;
import com.io.east.district.bean.NoticeBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 公告列表
 */
public class NoticeAdapter extends BaseQuickAdapter<NoticeBean.DataBean, BaseViewHolder> {
    public NoticeAdapter(int layoutResId, @Nullable List<NoticeBean.DataBean> data) {
        super(layoutResId, data);
    }





    /*
     * 将时间戳转换为时间
     */
    private static String stampToDate(Long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        long lt = new Long(s);
//        Date date = new Date(lt);
        res = simpleDateFormat.format(s);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate2(Long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        long lt = new Long(s);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    @Override
    protected void convert(BaseViewHolder helper, NoticeBean.DataBean item) {
        helper.setText(R.id.bt_time,item.getCreatetime());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_msg, item.getContent());
    }
}
