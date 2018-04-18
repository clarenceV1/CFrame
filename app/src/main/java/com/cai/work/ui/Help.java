package com.cai.work.ui;

import android.content.Context;

/**
 * Created by clarence on 2018/4/17.
 */

public class Help {
    private Context context;

    private static class Holder {
        public static Help help = new Help();
    }

    private Help() {

    }

    public static Help install() {
        return Holder.help;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
