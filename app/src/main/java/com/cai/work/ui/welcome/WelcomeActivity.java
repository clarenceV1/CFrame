package com.cai.work.ui.welcome;

import android.Manifest;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.annotation.aspect.Permission;
import com.cai.annotation.aspect.SingleClick;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WelcomeBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    @SingleClick
    @Permission(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA})
    public void testClick(View view) {
        toastNotice("点击了");
    }

    @Override
    public void toastNotice(String txt) {
        ToastUtils.showShort(txt);
    }
}
