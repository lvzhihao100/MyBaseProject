
package com.yusong.baseproject.util;
import android.os.Looper;
import android.widget.Toast;

import com.yusong.baseproject.base.BaseApplication;


/**
 * Created by vzhihao on 2016/8/24.
 */
public class ToastUtil {

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
       if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
           Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG).show();
       }else {
           Looper.prepare();
           Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG).show();
           Looper.loop();
       }
    }
    public static void showShort(CharSequence message) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
        }else {
            Looper.prepare();
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
