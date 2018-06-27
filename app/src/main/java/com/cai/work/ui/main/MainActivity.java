package com.cai.work.ui.main;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.MainLayoutBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/MainActivity", name = "主页面")
public class MainActivity extends AppBaseActivity<MainLayoutBinding> implements MainView {

    @Inject
    MainPresenter presenter;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.main_layout;
    }

    public void jump(View view){
        ARouter.getInstance().build("/AppModule/WelcomeActivity").navigation();
        finish();
    }
}
