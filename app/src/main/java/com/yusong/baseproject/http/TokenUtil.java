package com.yusong.baseproject.http;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;


import com.yusong.baseproject.util.SharePreferenceUtil;

import java.util.UUID;

/**
 * Created by vzhihao on 2016/11/22.
 */
public class TokenUtil {
    /* deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     *
             * 渠道标志为：
             * 1，andriod（a）
             *
             * 识别符来源标志：
             * 1， IMEI（imei）；
             * 2， wifi mac地址（wifi）；
             * 3， 序列号（sn）；
             * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
             *
             * @param context
     * @return
             */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {

            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                System.out.println("getDeviceId : "+deviceId.toString());
                return deviceId.toString();
            }
            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                System.out.println("getDeviceId : "+ deviceId.toString());
                return deviceId.toString();
            }

            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                System.out.println("getDeviceId : "+ deviceId.toString());
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                System.out.println("getDeviceId : "+ deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        System.out.println("getDeviceId : "+ deviceId.toString());
        return deviceId.toString();
    }

    private static boolean isEmpty(String wifiMac) {
        return wifiMac==null||wifiMac.equals("");
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = SharePreferenceUtil.getUUID("uuid");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SharePreferenceUtil.savetUUID("uuid", uuid);
        }
        System.out.println( "getUUID : " + uuid);
        return uuid;
    }
}
