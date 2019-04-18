package com.io.east.district;

import android.annotation.SuppressLint;
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


import com.io.east.district.api.UrlDeploy;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import es.dmoral.toasty.MyToast;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;
import retrofit2.converter.gson.GsonConverterFactory;


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
        init();
    }

    private void init() {
        EasyHttp.init(this);//默认初始化
        EasyHttp.getInstance()

                //可以全局统一设置全局URL
                .setBaseUrl(UrlDeploy.BaseUrl)//设置全局URL  url只能是域名 或者域名+端口号

                // 打开该调试开关并设置TAG,不需要就不要加入该行
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug("EasyHttp", true)

                //如果使用默认的60秒,以下三行也不需要设置
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 100)
                .setConnectTimeout(60 * 100)

                //可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
                //不需要可以设置为0
                .setRetryCount(3)//网络不好自动重试3次
                //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
                .setRetryDelay(500)//每次延时500ms重试
                //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
                .setRetryIncreaseDelay(500)//每次延时叠加500ms

                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
                //全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                //全局设置自定义缓存大小，默认50M
                .setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
                //设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
                .setCacheVersion(1)//缓存版本为1
                //.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用

                .addConverterFactory(GsonConverterFactory.create())//本框架没有采用Retrofit的Gson转化，所以不用配置
                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates();                                  //方法一：信任所有证书,不安全有风险
        //.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
        //配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败

//                .addCommonHeaders(headers)//设置全局公共头
//                .addCommonParams(params)//设置全局公共参数
        //.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
        //.setCallFactory()//局设置Retrofit对象Factory
        //.setCookieStore()//设置cookie
        //.setOkproxy()//设置全局代理
        //.setOkconnectionPool()//设置请求连接池
        //.setCallbackExecutor()//全局设置Retrofit callbackExecutor
        //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
        //.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
//                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
        AutoSize.initCompatMultiProcess(this);
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance()

                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true)

                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
//                .setExcludeFontScale(true)

                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)

                //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
                //AutoSize 会将屏幕总高度减去状态栏高度来做适配
                //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//           .setUseDeviceSize(true)

                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(false);

        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())

        customAdaptForExternal();

    }

    private void customAdaptForExternal() {

        AutoSizeConfig.getInstance().getExternalAdaptManager()

                //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
                //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
//                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

                //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
                //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
                //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
                //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
                //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
                //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
                //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
                //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
                //经过测试 DefaultErrorActivity 的设计图宽度在 360dp - 400dp 显示效果都是比较舒服的
                .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo(true, 400));
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
    @SuppressLint("HardwareIds")
    private String getPhoneImei(Context context) {
        context = context.getApplicationContext();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            @SuppressLint("HardwareIds") String imei = telephonyManager.getDeviceId();
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
