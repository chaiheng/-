package com.h.cheng.chain100;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.MyToast;


/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class MyApplication extends MultiDexApplication {


    private static boolean mIsInChatActivity = false;
    private List<Activity> mList = new LinkedList<Activity>();

    private static MyApplication mInstance = null;
    public static String VOICE_URL = "https://qiniu.henpi.vip/";
    /**
     * 是否是debug 模式
     */
    public static boolean isDebug = BuildConfig.DEBUG;

    private static String imei = "0";

    public static Context appcontext;
    public static String APi_Token;
    public static String nickName;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final String mPackageName = "com.h.cheng.chain100";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mInstance = this;
        appcontext = this.getApplicationContext();
        MyToast.init(this, true, true);
    }



    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null && !activity.isFinishing())
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //注意：次方法用于垃圾回收，如果手机内存小，或使用虚拟机测试，一定要注释掉这段代码
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    public static String getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳

        String str = String.valueOf(time);

        return str;
    }

    public static String getHSTime() {

        String str = dateFormat.format(new Date());
        return str;


    }


    public static void fix() {
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");

            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);

            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);

            method.invoke(field.get(null));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        fix();
    }


    /**
     * check the application process name if process name is not qualified, then
     * we think it is a service process and we will not init SDK
     *
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


    /**
     * 获取imei
     */
    public static String getImei() {
        if (TextUtils.isEmpty(imei)) {
            imei = "860000000000000";
        }
        return imei;
    }

    /**
     * 获取手机IMEI
     *
     * @return
     */
    private String getPhoneImei(Context context) {
        context = context.getApplicationContext();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            if (imei == null) {
                //android.provider.Settings;
                imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return imei;
        } catch (Exception ex) {

        }
        return null;
    }


    public String GetVersion() {
        String pName = mPackageName;
        String versionName = "";
        try {
            PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName, PackageManager.GET_CONFIGURATIONS);
            versionName = pinfo.versionName;
        } catch (Exception e) {
        }
        if (versionName == null) {
            versionName = "";
        }
        return versionName;
    }

    public int GetVersionCode() {
        String pName = mPackageName;
        int versionCode = 0;
        try {
            PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName, PackageManager.GET_CONFIGURATIONS);
            versionCode = pinfo.versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 返回渠道信息
     */
    public static String getApplicationChannel(Context context) {
        if (context == null)
            return "";
        String channel = "coolf";
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeException e) {

        }

        return channel;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isTopActivity(Context context) {
        if (context == null)
            return false;

        String packageName = mPackageName;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


}
