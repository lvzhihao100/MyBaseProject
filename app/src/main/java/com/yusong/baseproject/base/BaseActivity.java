package com.yusong.baseproject.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.yusong.baseproject.util.ToastUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by vzhihao on 2016/11/2.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    // 可以把常量单独放到一个Class中
    public static final String ACTION_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    protected IDataBandingHandler bandingHandler = new BaseBandingHandler();
    public View root;
    private int wheelProgress;
    public WheelProgressDialog wheelProgressDialog;
    private String requestMsg;
    private String loadingMsg;
    private long oldTime = 0;
    private long lastClick = 0;
    public BasePresenter basePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        wheelProgressDialog = new WheelProgressDialog(this);
        wheelProgress = 0;
        basePresenter = new BasePresenter();
        initBinding();
        initData();
        setView();
        BaseApplication.getApplication().addActivity(this);
    }


    /**
     * 绑定初始化,加载网络数据时，root要赋值
     */
    public abstract void initBinding();

    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 设置各种view
     */
    public abstract void setView();


    private void showBinner() {
        BaseApplication.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (++wheelProgress <= 110) {
                    if (wheelProgress > 90 && wheelProgress < 100) {
                        wheelProgress = 90;
                    }
                    wheelProgressDialog.progress(wheelProgress >= 100 ? 100 : wheelProgress).message(wheelProgress >= 100 ? requestMsg : loadingMsg);
                    showBinner();
                } else {
                    if (wheelProgressDialog.isShowing()) {
                        wheelProgressDialog.dismiss();
                    }
                }
            }
        }, 100);
    }
    public void setWheelOverMsg(String overMsg) {
        wheelProgress = 100;
        requestMsg = overMsg;
    }

    public void setWheelLoadingMsg(String loadingMsg) {
        this.loadingMsg = loadingMsg;
    }

    public int getWheelProgress() {
        return wheelProgress;
    }

    public void setWheelProgress(int progress) {
        this.wheelProgress = progress;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    ;

    @Override
    public void onBackPressed() {
        List<Activity> activities = BaseApplication.getApplication().getActivities();
        if (activities.size() <= 1) {
            long nowTime = new Date().getTime();
            if (nowTime - oldTime <= 2000) {
                BaseApplication.getApplication().exit();
            } else {
                oldTime = nowTime;
                ToastUtil.showShort("再次点击退出程序");
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basePresenter.onUnsubscribe();
        BaseApplication.getApplication().removeActivity(this);
        if (wheelProgressDialog != null && wheelProgressDialog.isShowing()) {
            wheelProgressDialog.dismiss();
        }
    }

    public void showWheelProgress() {
        wheelProgress = 0;
        wheelProgressDialog.show();
        showBinner();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle == null) {
            bundle=new Bundle();
        }
        bundle.putInt("requestCode",requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    private class BaseBandingHandler implements IDataBandingHandler{

        @Override
        public void click(View v) {

            System.out.println("click");
            BaseActivity.this.click(v);
        }
    }

    public abstract void click(View v);



    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 你可以添加多个Action捕获
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NETWORK_CHANGE);
        registerReceiver(receiver, filter);
        //还可能发送统计数据，比如第三方的SDK 做统计需求
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        //还可能发送统计数据，比如第三方的SDK 做统计需求
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 处理各种情况
            String action = intent.getAction();
            if (ACTION_NETWORK_CHANGE.equals(action)) { // 网络发生变化
                // 处理网络问题

            }
        }
    };

}
