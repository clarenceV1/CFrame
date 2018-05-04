package com.cai.module.a;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * a页面.
 */
@Route(path = "/AModule/AModuleActivity")
public class AModuleActivity extends Activity {
    /**
     * 创建.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_a);
    }

    /**
     * 调转.
     *
     * @param view 视图
     */
    public void goToAppModule(final View view) {
        ARouter.getInstance().build("/AppModule/MainActivity").navigation();
        finish();
    }

    /**
     * 调转.
     *
     * @param view 视图
     */
    public void goToBModule(final View view) {
        ARouter.getInstance().build("/BModule/BModuleActivity").navigation();
        finish();
    }
}
