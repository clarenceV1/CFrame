package com.cai.work.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = "/AppModule/MainActivity", name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainView {
    @Inject
    ILoadImage imageLoader;
    @Inject
    MainPresenter mainPresenter;

    List<MainTabView> tabViewList = new ArrayList<>();
    FragmentManager fragmentManager;
    Map<String, WeakReference<Fragment>> fragmentMap = new HashMap<>();

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
        fragmentManager = getSupportFragmentManager();
    }

    private void initTabView() {
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab0, 0));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab1, 1));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab2, 2));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab3, 3));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab4, 4));

        MainTabView.setOnTabClickListener(new MainTabView.OnTabClickListener() {
            @Override
            public boolean onClick(View view, int position, String fragmentName) {
                return tabClick(position, fragmentName);
            }
        });
    }

    private boolean tabClick(int position, String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return false;
        }
        Fragment fragment;
        WeakReference<Fragment> fragmentWeakReference = fragmentMap.get(fragmentName);
        if (fragmentWeakReference != null && fragmentWeakReference.get() != null) {
            fragment = fragmentWeakReference.get();
        } else {
            fragment = creatFragment(fragmentName);
            fragmentMap.put(fragmentName, new WeakReference<>(fragment));
        }
        return switchFragment(fragment);
    }

    public Fragment creatFragment(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return null;
        }
        Bundle bundle = new Bundle();
        return Fragment.instantiate(this, fragmentName, bundle);
    }

    private boolean switchFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flMainContainer, fragment);
        transaction.commit();
        return true;
    }
}
