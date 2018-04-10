package com.cai.module.a;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/AModule/AModuleActivity")
public class AModuleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_a);
    }

    public void goToAppModule(View view){
        ARouter.getInstance().build("/AppModule/MainActivity").navigation();
    }
    public void goToBModule(View view){
        ARouter.getInstance().build("/BModule/BModuleActivity").navigation();
    }
}
