package com.cai.work.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by clarence on 2018/1/22.
 */

public class CTextView extends TextView {
    public CTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("dispatchTouch: ", "TextView--->dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("dispatchTouch: ", "TextView--->onTouchEvent");
        return super.onTouchEvent(event);
    }
}
