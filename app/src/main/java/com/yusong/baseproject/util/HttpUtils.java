/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.yusong.baseproject.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static final int READ_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;

    public static String executeGet(String urlString) throws Exception {
        InputStream inputStream = executeGetInputStream(urlString);
//        ByteArrayOutputStream outputStream = null;
//        try {
//            outputStream = new ByteArrayOutputStream(1024);
//            int readLength = 0;
//            byte[] buffer = new byte[4 * 1024];
//            while ((readLength = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, readLength);
//            }
//            String json = outputStream.toString("utf-8");
//            return json;
//        } finally {
//            if (inputStream != null)
//                inputStream.close();
//            if (outputStream != null) {
//                outputStream.close();
//            }
//        }
        String s=inputStream2String(inputStream);
        System.out.println(s);
        return s;

    }


    public static InputStream executeGetInputStream(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 指的是与请求网址的服务器建立连接的超时时间。
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        // 指的是建立连接后如果指定时间内服务器没有返回数据的后超时。
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        if (code >= 200 && code <= 299)// 连接成功,返回结果
        {
            return connection.getInputStream();
        } else if (code == 302) {
            return executeGetInputStream(connection.getHeaderField("location"));
        } else {
            String info = "executeGet network error urlString:" + urlString + " code:" + code;
            throw new Exception(info);
        }
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();



        String line = null;

        try {

            while ((line = reader.readLine()) != null) {

                sb.append(line + "/n");

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



        return sb.toString();

    }





    public static  String   inputStream2String   (InputStream   in)   throws   IOException   {
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
            out.append(new String(b,0,n));
        }
        return   out.toString();
    }


    public  static  String   inputStreamToString(InputStream   is)   throws   IOException{
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }


}
