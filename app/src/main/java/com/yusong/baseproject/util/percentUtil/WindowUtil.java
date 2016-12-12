package com.yusong.baseproject.util.percentUtil;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vzhihao on 2016/7/13.
 */
public class WindowUtil {
    public static final int width = 640;
    public static final String dp = "200dp";
    public static final int height = 1136;
    private static Context context;
    public static int csw;
    private static boolean isfirst = true;
    private static int statusBarHeight;
    public static  int csh;
    public static  int density;

    public WindowUtil(Context context) {
        this.context = context;
    }

    public static int getStatusBarHeight() {
        if (isfirst) {
            isfirst = false;
            return statusBarHeight= Resources.getSystem().getDimensionPixelSize(
                    Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        } else {
            return statusBarHeight;
        }

    }

    public static void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        csw = dm.widthPixels;
        csh =dm.heightPixels;
        density=(int)dm.density;

    }
    public static int getTop(View view) {
        int[] ints = new int[2];
        view.getLocationInWindow(ints);
        return  ints[1];
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }
}
