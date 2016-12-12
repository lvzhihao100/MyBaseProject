package com.yusong.baseproject.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


import com.yusong.baseproject.util.percentUtil.WindowUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vzhihao on 2016/11/1.
 */
public class BaseApplication extends Application {
    //运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static BaseApplication application;
    //应用程序启动就调用，代表整个应用
    public static Handler handler;

    public static Context context;

    public static int mainTid;

    @Override
    public void onCreate() {
        super.onCreate();

        context = application = this;
        handler = new Handler();
        mainTid = android.os.Process.myTid();

        WindowUtil.init(this);
//        //        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
//        PlatformConfig.setSinaWeibo("2018732510", "4bbafa6d903ec3691398b8dfddf8a356");
//        PlatformConfig.setQQZone("1105794310", "BUFTHWGngw7eqNFP");
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
//        UMShareAPI.get(this);

    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }

    public synchronized static BaseApplication getApplication() {
        return application;
    }

    public List<Activity> getActivities() {
        return mList;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // remove Activity
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mList.remove(activity);
        }
    }

    //关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    //杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public static int getMainTid() {
        return mainTid;
    }
}
