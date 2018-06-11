package com.cai.work.ui.login;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.User;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.SaveBinding;
import com.cai.work.event.LoginStateEvent;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/SaveActivity", name = "安全设置")
public class SaveActivity extends AppBaseActivity<SaveBinding> implements SaveView {

    @Inject
    SavePresenter presenter;
    @Inject
    ILoadImage imageLoader;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        User user = presenter.getUserInfo();
        mViewBinding.tvMobile.setText(user.getMobile());

        ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(user.getAvatarUrl()).build();
        imageParams.setImageView(mViewBinding.imgIcon);
        imageLoader.loadImage(this, imageParams);


        mViewBinding.rlFixLoginPss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dd
            }
        });
        mViewBinding.rlCashPss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dd
            }
        });
        mViewBinding.btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.quit();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.save;
    }

    @Override
    public void loginOut() {
        ARouter.getInstance().build("/AppModule/MainActivity").withInt("position", 1).navigation();
        ToastUtils.showShort(getResources().getString(R.string.login_out));
    }
}
