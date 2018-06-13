package com.cai.work.ui.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.web.WebViewFragment;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WebBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WebActivity", name = "web")
public class WebActivity extends AppBaseActivity<WebBinding> implements WebForRTB {

    @Autowired(name = "paymentWay")
    int paymentWay = 1;//支付方式： 1 微信 ； 2 支付宝

    @Inject
    WebPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.save_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFragment();
    }

    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.KEY_RUL, presenter.getUrl(paymentWay));
        Fragment fragment = Fragment.instantiate(this, WebViewFragment.class.getName(), bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containLayout, fragment);
        fragmentTransaction.commit();
    }
}
