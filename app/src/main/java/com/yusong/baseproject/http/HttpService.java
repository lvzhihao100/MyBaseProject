package com.yusong.baseproject.http;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by vzhihao on 2016/11/9.
 */
public interface HttpService {
    /**
     * 用户获取验证码
     *
     * @param account 用户帐号
     * @return
     */
    @POST("authcode/updateAuthCode.do")
    @Multipart
    Observable<Response<String>> getCode(@Part("phoneNumber") RequestBody account);

    @GET("https://api.douban.com/v2/movie/top250?start=0&count=10")
//    @GET("http://192.168.0.103/book")
//    @GET("http://www.baidu.com")
    Observable<String> getGankData();

}


