package com.example.administrator.mydemo.Utils_Tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //事件是否攔截，返回false表示不攔截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    //重寫onTouchEvent，什麼都不做
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
