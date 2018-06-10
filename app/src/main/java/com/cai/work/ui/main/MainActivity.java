package com.cai.work.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.cai.work.event.LoginOutEvent;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    Map<String, WeakReference<Fragment>> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab0, 0));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab1, 1));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab2, 2));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab3, 3));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab4, 4));

        MainTabView.setOnTabClickListener(new MainTabView.OnTabClickListener() {
            @Override
            public boolean onClick(View view, int position, String fragmentName) {
                if (!mainPresenter.isLogin()) {
                    ToastUtils.showShort("你还没弹对话框呢");
                    return false;
                }
                return tabClick(fragmentName);
            }
        });
        mViewBinding.mainTab0.performClick();
    }

    private boolean tabClick(String fragmentName) {
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMainContainer, fragment);
        transaction.commit();
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginOut(LoginOutEvent event) {
        tabClick(tabViewList.get(0).getFragmentName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
