package com.yusong.baseproject.mvchelper;



import com.yusong.baseproject.http.Response;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by LuckyJayce on 2016/7/22.
 */
public abstract class MRxDataSource<DATA> extends RxDataSource<DATA> {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;
//    private static HttpServices gankApi;

//    protected HttpService getGankApi() {
//        if (gankApi == null) {
//            synchronized (this) {
//                if (gankApi == null) {
//                    //手动创建一个OkHttpClient并设置超时时间
//                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                    builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//
//                    retrofit = new Retrofit.Builder()
//                            .client(builder.build())
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                            .baseUrl("http://gank.io/api/")
//                            .build();
//                    gankApi = retrofit.create(HttpService.class);
//                }
//            }
//        }
//        return gankApi;
//    }

    @Override
    public Observable<DATA> refreshRX(DoneActionRegister<DATA> register) throws Exception {
        return load(refreshRXM(register));
    }

    @Override
    public Observable<DATA> loadMoreRX(DoneActionRegister<DATA> register) throws Exception {
        return load(loadMoreRXM(register));
    }

    private Observable<DATA> load(Observable<Response<DATA>> observableAction) {
        return observableAction.flatMap(new Func1<Response<DATA>, Observable<DATA>>() {
            @Override
            public Observable<DATA> call(Response<DATA> response) {
                if (response.getStatus()==0) {
                    return Observable.error(new BizException("业务出错"));
                }
                return Observable.just(response.getData());
            }
        });
    }

    public abstract Observable<Response<DATA>> refreshRXM(DoneActionRegister<DATA> register) throws Exception;

    public abstract Observable<Response<DATA>> loadMoreRXM(DoneActionRegister<DATA> register) throws Exception;
}
