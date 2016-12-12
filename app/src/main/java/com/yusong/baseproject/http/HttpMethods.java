package com.yusong.baseproject.http;



import android.os.Build;

import com.google.gson.Gson;
import com.yusong.baseproject.base.BaseApplication;
import com.yusong.baseproject.bean.receiver.BookMvc;
import com.yusong.baseproject.util.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vzhihao on 2016/11/1.
 */
public class HttpMethods {

//    public static final String BASE_URL = "http://192.168.1.110/GoitGuess/";
//    public static final String BASE_URL = "http://192.168.1.106:8080/YiZiQianJin/";
    public static final String BASE_URL = "http://192.168.1.5:8080/YiZiQianJin/";
    private static final int RONG_CLOUD_HEAD = 0;
    private static final int COMMON_HEAD = 1;

    private static Retrofit commonClient = null;
    private static Retrofit rongClouldClient = null;
    private static final int DEFAULT_TIMEOUT = 5;
    private static final MediaType textType = MediaType.parse("text/plain");

    private Retrofit retrofit;
    private HttpService httpService;

    //构造方法私有
    private HttpMethods() {
        Interceptor okhttpLogInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                System.out.println("okhttpLogInterceptor");
                System.out.println(request.toString());
//                add headers
                request = addHeaders(0, request);

//                if (BuildConfig.DEBUG) {
                    long t1 = System.nanoTime();
                    okhttp3.Response response = chain.proceed(request);
                    long t2 = System.nanoTime();

                    double time = (t2 - t1) / 1e6d;
                String msg = "%s\nurl->" + request.url()
                        + "\ntime->" + time
                        + "ms\nheaders->" + request.headers()
                        + "\nresponse code->" + response.code()
                        + "\nresponse headers->" + response.headers();
//                        + "\nresponse body->" + response.body().string();

                System.out.println(msg);
//                System.out.println( response.body().string());

                if (request.body()==null){
                    System.out.println("request body->null");
                }else {
                    System.out.println("request body->" + request.body());
                }
                    if (request.method().equals("GET")) {
                        System.out.println( "GET");
                    } else if (request.method().equals("POST")) {
                        Request copyRequest = request.newBuilder().build();
                        if (copyRequest.body() instanceof FormBody) {
                            Buffer buffer = new Buffer();
                            copyRequest.body().writeTo(buffer);
                            System.out.println("request params:" + buffer.readUtf8());
                        }
                        System.out.println( "POST");
                    } else if (request.method().equals("PUT")) {
                        System.out.println( "PUT");
                    } else if (request.method().equals("DELETE")) {
                        System.out.println( "DELETE");
                    }

                return response;
            }
        };
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(okhttpLogInterceptor)
                .cookieJar(new CookiesManager())
                .build();


                retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        httpService = retrofit.create(HttpService.class);
    }

    /**
     * 添加请求头
     * @param type 区分不同的API
     * @param request
     * @return
     */
    private static Request addHeaders(int type, Request request){
        Request.Builder requestBuild = request.newBuilder();
        switch (type) {
            case RONG_CLOUD_HEAD:
                String nonce = (int)(Math.random()*1000000000)+"";
                String timestamp = System.currentTimeMillis()+"";
//                String signature = SHA1.encode(BuildConfig.RONG_CLOUD_SECRET+nonce+timestamp);
                requestBuild.addHeader("App-Key",TokenUtil.getDeviceId(BaseApplication.getContext()))
                        .addHeader("Nonce",nonce)
                        .addHeader("Timestamp",timestamp);
                if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
//                    requestBuild.addHeader("Connection", "close");
                }
//                        .addHeader("Signature",signature);
                break;
        }

        return requestBuild.build();
    }

    public Observable<Response<List<BookMvc>>> getGankData(int page) {


        return httpService.getGankData()
                .map(new Func1<String, Response<List<BookMvc>>>() {
                    @Override
                    public Response<List<BookMvc>> call(String s) {
                        System.out.println("1231231");
                        System.out.println(s);
                        ArrayList<BookMvc> bookMvcs = new ArrayList<>();
                        Response<List<BookMvc>> bookMvcResponse = new Response<>();
                        bookMvcResponse.setStatus(1);
                        bookMvcResponse.setMsg("自己设置");
                        bookMvcs.add(new Gson().fromJson(s,BookMvc.class));
                        bookMvcResponse.setData(bookMvcs);
                        return bookMvcResponse;
                    }
                });
    }

    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.getApplication());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }



    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

//    /**
//     * 用于获取豆瓣电影Top250的数据
//     * @param subscriber 由调用者传过来的观察者对象
//     *
//     */
//    public Observable<Book> getAllBooks(Subscriber<Book> subscriber){
//        Map<String, String> stringStringMap = new HashMap<>();
//        stringStringMap.put("q","小王子");
//        stringStringMap.put("start", "0");
//        stringStringMap.put("count", "10");
//        return movieService.getData(stringStringMap)
//                .map(new ServerResponseFunc<Book>())
//                //HttpResultFunc（）为拦截onError事件的拦截器，后面会讲到，这里先忽略
//                .onErrorResumeNext(new HttpResponseFunc<Book>());
//
//    }

    //拦截固定格式的公共数据类型Response<T>,判断里面的状态码
    private class ServerResponseFunc<T> implements Func1<Response<T>, Response<T>> {
        @Override
        public Response<T> call(Response<T> reponse) {
            //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常
            if (reponse.getStatus() == 0) {
                //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                throw new ServerException();

            }
            //服务器请求数据成功，返回里面的数据实体
            return reponse;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;

    }

    private class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
//            ExceptionEngine为处理异常的驱动器
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }
    public Observable<Response<String>> getCode(String account){

        return httpService.getCode(getBody(account));
    }

    private RequestBody getBody(String body){
        return RequestBody.create(textType, body);
    }
}