package com.cai.module.b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/BModule/BModuleActivity")
public class BModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_b);
    }

    public void goToAppModule(View view) {
        ARouter.getInstance().build("/AppModule/MainActivity").withString("name", "老王").navigation();
        finish();
    }

    public void goToAModule(View view) {
        ARouter.getInstance().build("/AModule/AModuleActivity").navigation();
        finish();
    }
}
