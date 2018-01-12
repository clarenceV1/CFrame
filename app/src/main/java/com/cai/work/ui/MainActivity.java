package com.cai.work.ui;

import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.apt.TRouter;
import com.cai.framework.base.BaseActivity;
import com.cai.work.Animal;
import com.cai.work.R;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.presenter.MainPresenter;
import com.cai.work.ui.presenter.MainView;
import com.cai.work.utils.InstanceUtil;

@Router(Jumpter.HOME)
public class MainActivity extends BaseActivity<MainPresenter, MainBinding> implements MainView {

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    @CostTime
    public void initView() {
        Animal animal = InstanceUtil.getInstance(Animal.class);
        animal.fly();
    }

    public void goToWelcome(View view) {
        TRouter.go(Jumpter.WELCOME);
    }

    @Override
    public void setMainContent(String content) {
        mViewBinding.btn.setText(content);
    }
}
