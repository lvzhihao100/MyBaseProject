package com.yusong.baseproject;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCSwipeRefreshHelper;
import com.yusong.baseproject.base.BaseActivity;
import com.yusong.baseproject.MainActivityCustom;
import com.yusong.baseproject.bean.receiver.BookMvc;
import com.yusong.baseproject.mvchelper.ModelDataSource;
import com.yusong.baseproject.quickadapter.BaseAdapterHelper;
import com.yusong.baseproject.quickadapter.MultiItemTypeSupport;
import com.yusong.baseproject.quickadapter.QuickAdapter;
import com.yusong.baseproject.transformer.ZoomOutPageTransformer;
import com.yusong.baseproject.util.percentUtil.WindowUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


    private MainActivityCustom binding;
    private QuickAdapter<BookMvc> quickAdapter;
    private MVCHelper swipeRefreshHelper;
    private ArrayList<View> views;
    private MultiItemTypeSupport<BookMvc> multiItemTypeSupport;

    @Override
    public void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initData() {
        views = new ArrayList<View>();
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            views.add(imageView);
        }
        multiItemTypeSupport = new MultiItemTypeSupport<BookMvc>() {
            @Override
            public int getLayoutId(int position, BookMvc msg) {
                if (position == 0) {
                    return R.layout.list_item_main_pager;
                }
                return R.layout.list_item_book;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, BookMvc item) {
                if (postion == 0) {
                    return 0;
                }
                return 1;
            }
        };
    }
    private void changeTitle() {
        if (binding.listView.getFirstVisiblePosition() <= 1) {//viewpager可见
            System.out.println("kejian");
            View firstView = binding.listView.getChildAt(0);
            if (firstView != null) {
                System.out.println("firstView.getTop()" + firstView.getTop());
                if (firstView.getTop() <= 0) {
                    int percent = (-firstView.getTop()) * 255 / (firstView.getHeight() - WindowUtil.csw * 88 / WindowUtil.width - WindowUtil.getStatusBarHeight());
                    if (percent <= 50) {
                        percent = 0;
                    } else if (percent >= 255) {
                        percent = 255;
                    }
                    System.out.println(percent);
                    binding.flTitle.getBackground().setAlpha(percent);
                    binding.tvTitle.setTextColor(Color.argb(percent, 0, 0, 0));
                }
            }
        }
    }

    @Override
    public void setView() {
//        binding.flTitle.getBackground().setAlpha(0);
//        binding.tvTitle.setTextColor(Color.argb(0, 0, 0, 0));
//        if (quickAdapter==null) {
//            quickAdapter = new QuickAdapter<BookMvc>(this, R.layout.list_item_book, new ArrayList<BookMvc>()) {
//
//                @Override
//                protected void convert(BaseAdapterHelper helper, BookMvc item) {
//
//                    helper.setText(R.id.tv_content,item.getTitle());
//                }
//            };
//        }
        // 设置适配器
        if (quickAdapter == null) {

            quickAdapter = new QuickAdapter<BookMvc>(this, new ArrayList<BookMvc>(),
                    multiItemTypeSupport) {
                @Override
                protected void convert(BaseAdapterHelper helper, BookMvc item) {
                    System.out.println(helper.layoutId);
                    System.out.println(item);
                    switch (helper.layoutId) {
                        case R.layout.list_item_main_pager:
                            ViewPager viewPager = (ViewPager) helper.getView(R.id.vp_viewpager);
                            viewPager.setAdapter(new PagerAdapter() {
                                @Override
                                public void destroyItem(ViewGroup container, int position, Object object) {
                                    container.removeView((View) object);
                                }

                                @Override
                                public Object instantiateItem(ViewGroup container, int position) {
                                    System.out.println("position" + position);
                                    int index = position % views.size();
                                    if (views.get(index).getParent() != null) {
                                        ((ViewGroup) views.get(index).getParent()).removeView(views.get(index));
                                    }
                                    container.addView(views.get(index));
                                    return views.get(index);
                                }

                                @Override
                                public int getCount() {
                                    return Integer.MAX_VALUE;
                                }

                                @Override
                                public boolean isViewFromObject(View view, Object object) {
                                    return view == object;
                                }
                            });

                            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                            break;
                        case R.layout.list_item_book:
                            helper.setText(R.id.tv_content, item.getTitle());
                            if (item.equals("2")) {
                                helper.setText(R.id.tv_content, "12312hj312gb3hj12bg3jh12bjh3bg1jh23bg1j2hg3b1jh2b3gjh12b3jh12b3jh12b3jhb12jh3b12hj3b1hj2b3hj12b3hj12b3kjbjh1b2hj3b1h2jb3hj12b3jh12b3jh1b23jhb12j3hb12jh3b1j2h3bjh12b31j");
                            }
                            break;
                    }

                }
            };
            quickAdapter.showIndeterminateProgress(false);
        }
        binding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                System.out.println("1231");
                changeTitle();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                System.out.println("78978");
                changeTitle();
            }
        });
        swipeRefreshHelper = new MVCSwipeRefreshHelper<ArrayList<BookMvc>>(binding.swipeRefreshLayout);
        swipeRefreshHelper.setDataSource(new ModelDataSource());
        swipeRefreshHelper.setAdapter(quickAdapter);
        swipeRefreshHelper.refresh();
    }

    @Override
    public void click(View v) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        swipeRefreshHelper.destory();
    }
}
