package com.cai.work.ui.welcome;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WelcomeBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WelcomeActivity", name = "欢迎页面")
public class WelcomeActivity extends AppBaseActivity<WelcomeBinding> implements WelcomeView {

    @Inject
    WelcomePresenter presenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome;
    }

    @Override
    public void initView() {
        presenter.loadData();
    }

    @Override
    public void goMainActivity() {
        ARouter.getInstance().build("/AppModule/MainActivity").navigation();
        finish();
    }
}
