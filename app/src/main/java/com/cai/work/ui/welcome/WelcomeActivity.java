package com.cai.work.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.WelcomeBinding;
import com.example.clarence.utillibrary.DeviceUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WelcomeActivity", name = "欢迎页面")
public class WelcomeActivity extends AppBaseActivity<WelcomeBinding> implements WelcomeView {

    @Inject
    WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
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
        fitImage();
        presenter.createShortCut();
        presenter.loadUpgrade();
        presenter.loadMineData();
        installShortCut();
        goMainActivity();
    }

    public void installShortCut() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // 不允许重复创建

        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(mContext, R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setAction("android.intent.action.MAIN");// 桌面图标和应用绑定，卸载应用后系统会同时自动删除图标
        intent.addCategory("android.intent.category.LAUNCHER");// 桌面图标和应用绑定，卸载应用后系统会同时自动删除图标
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        mContext.sendBroadcast(shortcut);
    }


    private void goMainActivity() {
        mViewBinding.imgTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/AppModule/MainActivity").navigation();
                finish();
            }
        }, 3000);
    }

    private void fitImage() {
        ViewTreeObserver vto = mViewBinding.imgTitle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewBinding.imgTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int screenHeight = DeviceUtils.getScreenHeight(WelcomeActivity.this);
                float height = screenHeight * 0.46f;
                int imgeHeight = mViewBinding.imgTitle.getHeight();
                mViewBinding.imgTitle.setY(height - imgeHeight / 2);
            }
        });
    }
}
