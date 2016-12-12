package com.yusong.baseproject.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by vzhihao on 2016/11/2.
 */
public abstract class BaseFragment extends Fragment {
    private ViewDataBinding fragmentCustom;
    protected IDataBandingHandler myHandler = new MyBaseHandler();



    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentCustom==null) {
            fragmentCustom = initBinding(inflater);
        }
        ViewGroup parent=(ViewGroup) fragmentCustom.getRoot().getParent();
        if (parent!=null){
            parent.removeView(fragmentCustom.getRoot());
        }
        initData();
        setView();

        return fragmentCustom.getRoot();
    }

    protected abstract void setView();

    protected abstract void initData();

    public abstract ViewDataBinding initBinding(LayoutInflater inflater);

    private class MyBaseHandler implements IDataBandingHandler{

        @Override
        public void click(View v) {

            BaseFragment.this.click(v);
        }
    }

    public abstract void click(View v);

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
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
       startActivity(intent);
    }


}
