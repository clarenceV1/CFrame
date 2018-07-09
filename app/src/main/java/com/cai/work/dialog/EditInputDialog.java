package com.cai.work.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cai.work.R;

/**
 * Created by davy on 2018/3/13.
 */

public class EditInputDialog extends BaseDialog implements View.OnClickListener {

    private TextView titleTV;
    private EditText editText;
    private TextView okBtn;
    private TextView cancelBtn;
    private OnEditDialogClickListener mListener;
    //
    private String defaultText;
    private String title;

    public EditInputDialog(@NonNull Context context, String title, String defaultText ) {
        super(context);
        this.defaultText = defaultText;
        this.title = title;
        init();
    }


    private void init() {
        setContentView(R.layout.layout_edit_input_dialog);
        initView();
        setListener();
    }

    private void initView() {
        titleTV = findViewById(R.id.edit_title_tv);
        editText = findViewById(R.id.edit_input_dialog_et);
        okBtn = findViewById(R.id.edit_input_dialog_ok_btn);
        cancelBtn = findViewById(R.id.edit_input_dialog_cancel_btn);
        //
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }
        //
        if (!TextUtils.isEmpty(defaultText)) {
            editText.setHint(defaultText);
        }
    }

    private void setListener() {
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edit_input_dialog_ok_btn) {
            if (mListener != null) {
                String st = "";
                try {
                    st = editText.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mListener.onOkClick(defaultText, st);
            }
        } else if (id == R.id.edit_input_dialog_cancel_btn) {
            if (mListener != null) {
                mListener.onCancelClick();
            }
        }
        dismiss();
    }

    public void setOnOnEditDialogClickListener(OnEditDialogClickListener listener) {
        mListener = listener;
    }


    public void setTitle(String title) {
        titleTV.setText(title);
    }

    public void setOkBtnText(String text) {
        okBtn.setText(text);
    }

    public void setCancelBtnText(String text) {
        cancelBtn.setText(text);
    }

    public interface OnEditDialogClickListener {
        void onOkClick(String oldStr, String newStr);

        void onCancelClick();
    }
}
