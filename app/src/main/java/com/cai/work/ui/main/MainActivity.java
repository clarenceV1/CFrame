package com.cai.work.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.annotation.aspect.Permission;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.GodDialog;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.AppUpdate;
import com.cai.work.databinding.MainLayoutBinding;
import com.cai.work.widget.TabItem;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/MainActivity", name = "主页面")
public class MainActivity extends AppBaseActivity<MainLayoutBinding> implements MainView {

    public final String FRAGMENT_TAG = "main_fragment_";
    @Inject
    MainPresenter presenter;
    @Inject
    TabManager tabManager;

    private long fistTouchTime;
    private FragmentManager mFragmentManager;
    int oldPosition = 0;

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
        mFragmentManager = getSupportFragmentManager();
        initTab();
        requestPermission();
        showUpdateAppDialog();
    }

    private void initTab() {
        tabManager.initTab(mViewBinding.tab1, mViewBinding.tab2, mViewBinding.tab3);
        tabManager.setTabClickListener(new TabItem.TabClickListener() {
            @Override
            public void clickListener(TabItem view, int currentPosition) {
                if(currentPosition == 0){
                    presenter.getStatistics().tab_home();
                }else if(currentPosition == 1){
                    presenter.getStatistics().tab_fx();
                }else if(currentPosition == 2){
                    presenter.getStatistics().tab_wd();
                }
                switchFragment(currentPosition);
                oldPosition = currentPosition;
            }
        });
        switchFragment(0);
    }

    public void switchFragment(int currentPosition) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment oldFragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG + oldPosition);
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        Fragment currentFragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG + currentPosition);
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = creatFragment(currentPosition);
            if (currentFragment != null) {
                transaction.add(R.id.mainContainer, currentFragment, FRAGMENT_TAG + currentPosition);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    public Fragment creatFragment(int position) {
        switch (position) {
            case 0:
                return Fragment.instantiate(this, CandyFragment.class.getName());
            case 1:
                return Fragment.instantiate(this, DiscoverFragment.class.getName());
            case 2:
                return Fragment.instantiate(this, MineFragment.class.getName());
        }
        return null;
    }

    private void showUpdateAppDialog() {
        final AppUpdate appUpdate = presenter.isNewVersion();
        if (appUpdate != null && (appUpdate.getIs_remind() == 1 || appUpdate.getIs_force() == 1)) {
            GodDialog.Builder builder = new GodDialog.Builder(this)
                    .setTitle(getString(R.string.update_title))
                    .setMessage(appUpdate.getMessage());
            if (appUpdate.getIs_force() == 1) {
                builder.setOneButton(getString(R.string.update_title), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ARouter.getInstance().build("/moreOne/WebActivity").withCharSequence("url", appUpdate.getDownload_url()).navigation();
                    }
                });
            } else {
                builder.setPositiveButton(getString(R.string.update_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ARouter.getInstance().build("/moreOne/WebActivity").withCharSequence("url", appUpdate.getDownload_url()).navigation();
                    }
                }).setNegativeButton(getString(R.string.update_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            builder.build().show();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_layout;
    }

    @Permission(value = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE})
    private void requestPermission() {

    }

    @Override
    public void onBackPressed() {
        long nowTouchTime = System.currentTimeMillis();
        if (nowTouchTime - fistTouchTime < 501) {//
            super.onBackPressed();
            finish();
        } else {
            fistTouchTime = nowTouchTime;
            ToastUtils.showShort(R.string.quit_app);
        }
    }
}
