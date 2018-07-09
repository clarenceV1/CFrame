package com.cai.work.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.cai.work.R;


/**
 * Created by davy on 2018/3/9.
 */

public class BaseDialog extends Dialog {

    protected FrameLayout rootView;
    private int screenHeight;

    public BaseDialog(@NonNull Context context) {
        super(context);
        baseInit();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        baseInit();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        baseInit();
    }

    protected void baseInit() {
        screenHeight = getScreenHeight(getContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //
        rootView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_base_dialog, null);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(rootView);
        try {
            View contentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
            rootView.addView(contentView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        //如果需要状态栏透明，就使用下面的方法
         layoutParams.height = screenHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : screenHeight;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public interface OnDialogClickListener {
        void onOkClick();

        void onCancelClick();
    }
}
