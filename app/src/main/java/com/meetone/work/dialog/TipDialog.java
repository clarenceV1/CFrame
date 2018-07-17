package com.meetone.work.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meetone.work.R;

/**
 * Created by davy on 2018/3/9.
 */

public class TipDialog extends BaseDialog implements View.OnClickListener {
    private LinearLayout tip_two_btn_ll;
    private TextView titleTV;
    private TextView contentTV;
    private TextView okBtn;
    private TextView cancelBtn;
    private TextView one_btn;
    private OnDialogClickListener mListener;
    //
    private String title;
    private String content;

    public TipDialog(@NonNull Context context) {
        this(context, "", "");
    }

    public TipDialog(@NonNull Context context, String title, String content) {
        this(context, title, content, null);
    }

    public TipDialog(@NonNull Context context, String title, String content, OnDialogClickListener listener) {
        super(context);
        this.title = title;
        this.content = content;
        mListener = listener;
        init();
    }

    private void init() {
        setContentView(R.layout.layout_tip_dialog);
        initView();
        setListener();
    }


    private void initView() {
        tip_two_btn_ll = findViewById(R.id.tip_two_btn_ll);
        titleTV = findViewById(R.id.tip_dialog_title_tv);
        contentTV = findViewById(R.id.tip_dialog_content_tv);
        okBtn = findViewById(R.id.tip_dialog_ok_btn);
        cancelBtn = findViewById(R.id.tip_dialog_cancel_btn);
        one_btn = findViewById(R.id.tip_dialog_one_btn);
        //
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            contentTV.setMovementMethod(ScrollingMovementMethod.getInstance());
            contentTV.setText(content);
        }
        //
        one_btn.setVisibility(View.GONE);
    }

    private void setListener() {
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        one_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tip_dialog_ok_btn || id == R.id.tip_dialog_one_btn) {
            if (mListener != null) {
                mListener.onOkClick();
            }
        } else if (id == R.id.tip_dialog_cancel_btn) {
            if (mListener != null) {
                mListener.onCancelClick();
            }
        }
        dismiss();
    }

    public void setOnDialogClickLitener(OnDialogClickListener litener) {
        mListener = litener;
    }

    public void setTitleText(String text) {
        titleTV.setText(text);
    }

    public void setContentText(String text) {
        contentTV.setText(text);
    }

    public void setOkBtnText(String text) {
        okBtn.setText(text);
    }

    public void setOneBtn(String text) {
        tip_two_btn_ll.setVisibility(View.GONE);
        one_btn.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(text)) {
            one_btn.setText(text);
        }
    }

    public void setCancelBtnText(String text) {
        cancelBtn.setText(text);
    }
}
