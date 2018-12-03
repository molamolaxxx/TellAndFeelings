package com.mola.tellandfeelings.control.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class MyFragmentViewPager extends ViewPager {
    private float preX;
    public MyFragmentViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res=super.onInterceptTouchEvent(ev);
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            preX=ev.getX();
        }else {
            if(Math.abs(ev.getX()-preX)>4){
                return true;
            }else {
                preX=ev.getX();
            }
        }
        return res;
    }
}
