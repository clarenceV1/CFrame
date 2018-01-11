package com.cai.work.ui;

import android.os.Bundle;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.apt.TRouter;
import com.cai.framework.BaseActivity;
import com.cai.work.R;
import com.cai.work.base.Jumpter;

@Router(Jumpter.WELCOME)
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void goToMain(View view) {
        TRouter.go(Jumpter.HOME);
        finish();
    }
}
