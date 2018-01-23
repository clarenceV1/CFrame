package com.cai.work.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by clarence on 2018/1/22.
 */

public class CRelativeLayout extends RelativeLayout {
    public CRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("dispatchTouch: ", "RelativeLayout--->dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("dispatchTouch: ", "RelativeLayout--->onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("dispatchTouch: ", "RelativeLayout--->onTouchEvent");
        return super.onTouchEvent(event);
    }
}
