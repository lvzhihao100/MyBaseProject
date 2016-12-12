package com.yusong.baseproject.util.percentUtil;

import android.databinding.BindingAdapter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vzhihao on 2016/11/1.
 */
public class LayoutUtil {
    public LayoutUtil() {
    }


    @BindingAdapter({"android:layout_margin"})
    public static void setMargin(View view, int margin) {
        if(view != null) {
            setMargin(view, margin,margin,margin, margin);
        }

    }
    @BindingAdapter({"android:padding"})
    public static void setPadding(View view, int padding) {
        if(view != null) {
            setPadding(view, padding,padding,padding, padding);
        }

    }

    public static void setMargin(View view, int left, int right, int top, int bottom) {
        if(view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            layoutParams.setMargins(left,right,top, bottom);
            view.setLayoutParams(layoutParams);

        }

    }
    @BindingAdapter({"textSize"})
    public static void setTextSize(TextView view, int size) {
        if(view != null) {
            System.out.println("_____set textsize");
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }
    }


    @BindingAdapter({"size"})
    public static void setSize(View view, int[] size) {
        if(view != null && size != null && size.length == 2) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = size[0];
            layoutParams.height = size[1];
            view.setLayoutParams(layoutParams);
        }

    }

    @BindingAdapter({"layout_width"})
    public static void setLayoutWidth(View view, int width) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }

    }

    @BindingAdapter({"layout_height"})
    public static void setLayoutHeight(View view, int height) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }

    }

    @BindingAdapter({"android:paddingLeft"})
    public static void setPaddingLeft(View view, int padding) {
        setPadding(view, padding, view.getPaddingRight(), view.getPaddingTop(), view.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingRight"})
    public static void setPaddingRight(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), padding, view.getPaddingTop(), view.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingTop"})
    public static void setPaddingTop(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), view.getPaddingRight(), padding, view.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingBottom"})
    public static void setPaddingBottom(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), view.getPaddingRight(), view.getPaddingTop(), padding);
    }

    public static void setPadding(View view, int left, int right, int top, int bottom) {
        if(view != null) {
            view.setPadding(left, top, right, bottom);
        }

    }

    @BindingAdapter({"paddingLeft"})
    public static void setAppPaddingLeft(View view, int padding) {
        setPadding(view, padding, view.getPaddingRight(), view.getPaddingTop(), view.getPaddingBottom());
    }

    @BindingAdapter({"paddingRight"})
    public static void setAppPaddingRight(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), padding, view.getPaddingTop(), view.getPaddingBottom());
    }

    @BindingAdapter({"paddingTop"})
    public static void setAppPaddingTop(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), view.getPaddingRight(), padding, view.getPaddingBottom());
    }

    @BindingAdapter({"paddingBottom"})
    public static void setAppPaddingBottom(View view, int padding) {
        setPadding(view, view.getPaddingLeft(), view.getPaddingRight(), view.getPaddingTop(), padding);
    }

    @BindingAdapter({"android:layout_marginLeft"})
    public static void setMarginLeft(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.leftMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"android:layout_marginRight"})
    public static void setMarginRight(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.rightMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"android:layout_marginTop"})
    public static void setMarginTop(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.topMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"android:layout_marginBottom"})
    public static void setMarginBottom(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.bottomMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"layout_marginLeft"})
    public static void setAppMarginLeft(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.leftMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"layout_marginRight"})
    public static void setAppMarginRight(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.rightMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"layout_marginTop"})
    public static void setAppMarginTop(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.topMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }

    @BindingAdapter({"layout_marginBottom"})
    public static void setAppMarginBottom(View view, int margin) {
        if(view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                marginLayoutParams.bottomMargin = margin;
                view.setLayoutParams(marginLayoutParams);
            }
        }

    }
}
