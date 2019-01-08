package com.h.cheng.chain100.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.h.cheng.chain100.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SP配置
 *
 * @author Administrator
 */
public class SharedPreferencesUtil {
    public final static String USER_SP = "userinfo";
    private static SharedPreferencesUtil mInstance = null;
    private Context mContext;
    private SharedPreferences agPreferences;

    public SharedPreferencesUtil() {
        this.mContext = MyApplication.getInstance();
        agPreferences = mContext.getSharedPreferences(USER_SP,
                Activity.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferencesUtil();
                }
            }
        }
        return mInstance;
    }

    public void saveInfo(String tag, String value) {
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(tag, value);
        editor.apply();
    }

    public void saveInfo(String tag, int value) {
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    public int getInfoInt(String tag) {
        return agPreferences.getInt(tag, 0);
    }

    public int getInfoInt(String tag, int defaultValue) {
        return agPreferences.getInt(tag, defaultValue);
    }

    public String getInfo(String tag) {
        return agPreferences.getString(tag, "");
    }

    public void saveInfo(String tag, boolean value) {
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putBoolean(tag, value);
        editor.apply();
    }

    public boolean getInfo(String tag, boolean defValue) {
        return agPreferences.getBoolean(tag, defValue);
    }


    public void saveFavDate(String tag, String value) {
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(tag, value);
        editor.apply();
    }

    public String getFavDate(String tag) {
        return agPreferences.getString(tag, "");
    }

    /**
     * 将对象转化成字符串
     *
     * @param object
     */
    public String getJsonStringByEntity(Object object) {
        String strJson = "";
        Gson gson = new Gson();
        strJson = gson.toJson(object);
        return strJson;
    }

    /**
     * 将字符串转化成对象
     *
     * @param jonstr
     * @param cls
     * @return
     */
    public Object getEntityByString(String jonstr, Class<?> cls) {
        Object object = null;
        Gson gson = new Gson();
        object = gson.fromJson(jonstr, cls);
        return object;
    }

    /**
     * 保存对象
     */
    public void saveInfo(String tag, Object object) {
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(tag, getJsonStringByEntity(object));
        editor.apply();
    }

    /**
     * 获取对象
     */
    public Object getInfo(String tag, Class<?> cls) {
        String str = agPreferences.getString(tag, "");
        if (str.equals("")) {
            return null;
        }

        return getEntityByString(str, cls);
    }

    public void saveListInfo(String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }

        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(key, mJsonArray.toString());
        editor.apply();
    }

    public List<Map<String, String>> getListInfo(String key) {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        String result = agPreferences.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;
    }

    public void saveMapInfo(String key, LinkedHashMap<String, Integer> data) {
        Iterator<Map.Entry<String, Integer>> iterator = data.entrySet().iterator();
        JSONObject object = new JSONObject();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(key, object.toString());
        editor.apply();
    }

    public LinkedHashMap<String, Integer> getMapInfo(String key) {
        String result = agPreferences.getString(key, "");
        LinkedHashMap<String, Integer> itemMap = new LinkedHashMap<String, Integer>();
        try {
            JSONObject itemObject = new JSONObject(result);

            JSONArray names = itemObject.names();
            if (names != null) {
                for (int j = 0; j < names.length(); j++) {
                    String name = names.getString(j);
                    int value = itemObject.getInt(name);
                    itemMap.put(name, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemMap;
    }


    public void setHistories(String tag, List<String> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString(tag, strJson);
        editor.apply();
    }
}
