package com.cai.work.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

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
    private static final int IMAGE = 1;

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
        mViewBinding.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(SaveActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
//
//                } else {
//
//                }
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        mViewBinding.imgIcon.setImageBitmap(bm);
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
