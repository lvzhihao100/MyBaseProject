package com.yusong.baseproject.mvchelper;


import com.yusong.baseproject.bean.receiver.BookMvc;
import com.yusong.baseproject.http.HttpMethods;
import com.yusong.baseproject.http.Response;

import java.util.List;

import okhttp3.internal.http.HttpMethod;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by LuckyJayce on 2016/7/21.
 */
public class ModelDataSource extends MRxDataSource<List<BookMvc>> {
    private int mPage = 1;

    @Override
    public Observable<Response<List<BookMvc>>> refreshRXM(DoneActionRegister<List<BookMvc>> register) throws Exception {
        return load(1, register);
    }

    @Override
    public Observable<Response<List<BookMvc>>> loadMoreRXM(DoneActionRegister<List<BookMvc>> register) throws Exception {
        return load(mPage + 1, register);
    }

    private Observable<Response<List<BookMvc>>> load(final int page, DoneActionRegister<List<BookMvc>> register) throws Exception {
        register.addAction(new Action1<List<BookMvc>>() {
            @Override
            public void call(List<BookMvc> bookMvcs) {
                mPage = page;
            }
        });
        return HttpMethods.getInstance().getGankData(page);
    }


    @Override
    public boolean hasMore() {
        return true;
    }


}
