package com.cai.work.ui.invite;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.annotation.aspect.Permission;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.QRCodeUtils;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.InviteWayBinding;
import com.example.clarence.utillibrary.ClipBoardUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.ImageUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/InviteWayActivity", name = "邀请方式")
public class InviteWayActivity extends AppBaseActivity<InviteWayBinding> implements InviteWayView {

    @Autowired(name = "inviteUrl")
    String inviteUrl;

    @Inject
    InviteWayPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
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
    public void initView() {
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.invite_way_titile));

        mViewBinding.btnSaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQRcode();
            }
        });
        mViewBinding.btnCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inviteUrl != null) {
                    ClipBoardUtils.copyToClipBoard(InviteWayActivity.this, "链接", inviteUrl);
                    ToastUtils.showShort(getString(R.string.invite_link_toast));
                }
            }
        });
        mViewBinding.tvInviteLink.setText(inviteUrl);

        int witd = DimensUtils.dp2px(this, 100);
        mViewBinding.imgQRCode.setImageBitmap(QRCodeUtils.createQRCodeBitmap(inviteUrl, witd, witd));
    }

    @Permission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void saveQRcode() {
        int witd = DimensUtils.dp2px(this, 100);
        Bitmap imag = QRCodeUtils.createQRCodeBitmap(inviteUrl, witd, witd);
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String imageName = format.format(new Date()) + ".JPEG";
        ImageUtils.saveBitmap(this, imag, imageName);
        ToastUtils.showShort(getString(R.string.invite_save_qrcode_success_toast));
    }

    @Override
    public int getLayoutId() {
        return R.layout.invite_way;
    }
}
