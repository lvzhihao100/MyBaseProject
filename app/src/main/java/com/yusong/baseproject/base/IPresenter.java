package com.yusong.baseproject.base;

import rx.Subscription;

/**
 * Created by jiangzn on 16/9/26.
 */
public interface IPresenter {
    void onUnsubscribe();
    void addSubscription(Subscription subscriber);
}
