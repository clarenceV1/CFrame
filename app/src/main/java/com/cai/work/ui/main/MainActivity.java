package com.cai.work.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/MainActivity", name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainView {
    @Inject
    ILoadImage imageLoader;

    @Inject
    MainPresenter mainPresenter;

    @Inject
    TabManager tabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(mainPresenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    public void initView() {
        initTabView();
    }

    private void initTabView() {
        ViewPager viewPager = mViewBinding.mViewPager;
        viewPager.setOffscreenPageLimit(5);
        MainAdapter mPagerAdapter = new MainAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        mViewBinding.mTabLayout.setupWithViewPager(mViewBinding.mViewPager);
        tabManager.addTab(this, mViewBinding.mTabLayout,viewPager);
        mViewBinding.mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabManager.changeTabStatus(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabManager.changeTabStatus(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
