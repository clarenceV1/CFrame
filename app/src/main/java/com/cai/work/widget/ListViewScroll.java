package com.cai.work.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by clarence on 2018/1/22.
 */

public class ListViewScroll extends ListView {
    private boolean mIsOnMeasure;
    int mLastX, mLastY;

    public ListViewScroll(Context context) {
        super(context);
    }

    public ListViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mIsOnMeasure = true;
        @SuppressLint("WrongConstant") int expandSpec = MeasureSpec.makeMeasureSpec(536870911, -2147483648);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mIsOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

    public boolean isOnMeasure() {
        return this.mIsOnMeasure;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //子View的所有父ViewGroup都会跳过onInterceptTouchEvent的回调
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaY) > Math.abs(deltaX) + 5) {
//                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
