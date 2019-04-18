package com.io.east.district.adapter;

import android.annotation.SuppressLint;
import android.text.Html;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.io.east.district.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 公告列表
 */
public class NoticeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public NoticeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    /*public NoticeAdapter(int layoutResId, @Nullable List<ProclamationBean.DataBean.ArticlesBean.ValueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProclamationBean.DataBean.ArticlesBean.ValueBean item) {
        helper.setText(R.id.bt_time, stampToDate(item.getFcreatedate()));
        helper.setText(R.id.tv_title, item.getFtitle());
        helper.setText(R.id.tv_msg, Html.fromHtml(item.getFcontent()));
//        helper.setText(R.id.tv_name, item.getKey().getFname());
//        helper.setText(R.id.tv_min, stampToDate2(item.getKey().getFcreatetime()));
    }*/


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
    protected void convert(BaseViewHolder helper, String item) {

    }
}
