package com.cai.work.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.PhotoUtils;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.User;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.SaveBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.save_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        User user = presenter.getUserInfo();
        mViewBinding.tvMobile.setText(user.getMobile());

//        showHead(user.getAvatarUrl());

        mViewBinding.rlFixLoginPss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getInstance().choosePhone(SaveActivity.this);
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
        mViewBinding.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PhotoUtils.getInstance().setCrop(true);
                PhotoUtils.getInstance().takePhoto(SaveActivity.this);
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

    @Override
    public void showHeadImg(String image) {
        showHead(image);
    }

    private void showHead(String image) {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(image).build();
        imageParams.setImageView(mViewBinding.imgIcon);
        imageLoader.loadImage(this, imageParams);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        String path = PhotoUtils.getInstance().onActivityResult(this, requestCode, resultCode, data);
        if (!TextUtils.isEmpty(path)) {
            presenter.uploadImage(path);
        }
    }
}
