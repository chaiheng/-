package com.h.cheng.chain100.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import es.dmoral.toasty.MyToast;

public class ToastUtil {
    public static void show(Activity ac, String info) {
        if ((ac != null) && (!ac.isFinishing())) {
            Toast toast = Toast.makeText(ac, info, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void show(Activity ac, int res) {
        if ((ac != null) && (!ac.isFinishing())) {
            Toast toast = Toast.makeText(ac, res, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    public static void show(Context context, String content) {
        if (content != null) {
            if (content.contains("成功")) {
                MyToast.successBig(content);
            } else {
                MyToast.errorBig(content);
            }
        }

    }

    public static void showError(Context context, String content) {
        if (content != null) {
                MyToast.errorBig(content);
        }


    }




    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null ;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;

    /**
     * 显示Toast, 去重功能
     * @param context
     * @param message
     */
    public static void showSingleToast(Context context,String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }

    /**
     * 显示Toast, 去重功能
     * @param context
     * @param message
     */
    public static void showBmToast(Context context,String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }




}
