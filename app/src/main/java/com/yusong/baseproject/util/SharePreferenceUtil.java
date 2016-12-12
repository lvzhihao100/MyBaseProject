package com.yusong.baseproject.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yusong.baseproject.base.BaseApplication;


public class SharePreferenceUtil {

    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String FIRST = "first";
    private static final String UUID = "uuid";


    public static void savetUUID(String key, String uuid) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(UUID, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, uuid);
        edit.commit();
    }

    public static String getUUID(String key) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(UUID, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public static void savetFirst( String key) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(FIRST, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, false);
        edit.commit();
    }

    public static boolean getFirst( String key) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(FIRST, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    public static void saveAccount(String key, String account) {
        clearAccount();
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, account);
        edit.commit();
    }

    public static String getPassword(String key, String defalut) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
        return preferences.getString(key, defalut);
    }

    public static void savetPassword(String key, String password) {
        clearPassword();
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, password);
        edit.commit();
    }

    public static String getAccount(String key, String defalut) {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return preferences.getString(key, defalut);
    }


    public static void clearAccount() {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear().commit();
    }
    public static void clearPassword() {
        SharedPreferences preferences = BaseApplication.getApplication().getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear().commit();
    }
}

