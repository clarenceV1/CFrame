package com.cai.work.ui.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ForgetPasswordBinding;
import com.cai.work.databinding.SaveBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/SaveActivity", name = "安全设置")
public class SaveActivity extends AppBaseActivity<SaveBinding> implements SaveView {

    @Inject
    SavePresenter savePresenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(savePresenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.save;
    }
}
