package com.cai.work.ui.main;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.GodDialog;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

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
    String currentFragmentName;
    Fragment currentFragment;
    FragmentManager fragmentManager;

    @Autowired(name = "position")
    int position = 1;
    private long fistTouchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clickTab(position);
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
        fragmentManager = getSupportFragmentManager();

        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab0, 0));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab1, 1));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab2, 2));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab3, 3));
        tabViewList.add(MainTabView.creatTabView(mViewBinding.mainTab4, 4));

        MainTabView.setOnTabClickListener(new MainTabView.OnTabClickListener() {
            @Override
            public boolean onClick(View view, int position, String fragmentName) {
                if (fragmentName.equals(currentFragmentName)) {
                    return false;// 已经在当前页面不处理
                }
                if (position != 0 && !mainPresenter.isLogin()) {
                    showDialog();
                    return false;
                }
                tabViewReset();
                return tabClick(fragmentName);
            }
        });

        mViewBinding.mainTab0.performClick();
    }

    private void tabViewReset() {
        for (MainTabView mainTabView : tabViewList) {
            mainTabView.reset();
        }
    }

    private void clickTab(int position) {
        switch (position) {
            case 1:
                mViewBinding.mainTab0.performClick();
                break;
            case 2:
                mViewBinding.mainTab1.performClick();
                break;
            case 3:
                mViewBinding.mainTab2.performClick();
                break;
            case 4:
                mViewBinding.mainTab3.performClick();
                break;
            case 5:
                mViewBinding.mainTab4.performClick();
                break;
        }
    }

    private void showDialog() {
        new GodDialog.Builder(this)
                .setTitle(R.string.dialog_titile)
                .setMessage(R.string.please_login)
                .setPositiveButton(R.string.btn_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.btn_commit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ARouter.getInstance().build("/AppModule/LoginActivity").navigation();
                    }
                }).create().show();
    }

    private boolean tabClick(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return false;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        currentFragmentName = fragmentName;
        currentFragment = fragmentManager.findFragmentByTag(fragmentName);
        if (currentFragment == null) {
            currentFragment = creatFragment(fragmentName);
            transaction.add(R.id.flMainContainer, currentFragment, fragmentName);
        } else {
            transaction.show(currentFragment);
        }
        transaction.commitAllowingStateLoss();
        return true;
    }

    public Fragment creatFragment(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return null;
        }
        Bundle bundle = new Bundle();
        return Fragment.instantiate(this, fragmentName, bundle);
    }


    @Override
    public void onBackPressed() {
        long nowTouchTime = System.currentTimeMillis();
        if (nowTouchTime - fistTouchTime < 501) {//
            //sSystem.exit(0);
            super.onBackPressed();
            finish();
        } else {
            fistTouchTime = nowTouchTime;
            ToastUtils.showShort(R.string.quit_app);
            //new MyController().test();
        }
    }
}
