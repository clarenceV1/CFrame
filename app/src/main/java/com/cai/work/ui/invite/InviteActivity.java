package com.cai.work.ui.invite;

import android.Manifest;
import android.graphics.Bitmap;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.annotation.aspect.Permission;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.QRCodeUtils;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Invite;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.InviteBinding;
import com.example.clarence.utillibrary.ClipBoardUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.ImageUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/InviteActivity", name = "邀请中心")
public class InviteActivity extends AppBaseActivity<InviteBinding> implements InviteView {

    @Inject
    InvitePresenter presenter;
    Invite invite;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.invite_titile));
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.invite_rule));
        mViewBinding.commonHeadView.tvRight.setVisibility(View.VISIBLE);
        mViewBinding.commonHeadView.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = presenter.getActivityH5();
                ARouter.getInstance().build("/AppModule/WebActivity").withCharSequence("url", url).withCharSequence("title", "邀请规则").navigation();

            }
        });

        mViewBinding.tvRebateCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RebateActivity").navigation();
            }
        });
        mViewBinding.tvCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invite != null) {
                    ClipBoardUtils.copyToClipBoard(InviteActivity.this, "链接", invite.getInvited_url());
                    ToastUtils.showShort(getString(R.string.invite_link_toast));
                }
            }
        });
        mViewBinding.tvSavePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQRcode();
            }
        });
        mViewBinding.rlMyInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/MyInviteActivity")
                        .withCharSequence("inviteOne", JSON.toJSONString(invite.getOne_invite()))
                        .withCharSequence("inviteTwo", JSON.toJSONString(invite.getTwo_invite()))
                        .navigation();
            }
        });
        mViewBinding.rlInviteWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/InviteWayActivity").withCharSequence("inviteUrl", invite.getInvited_url()).navigation();
            }
        });

        mViewBinding.rlWithdrawalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/WithdrawalDetailActivity").navigation();
            }
        });

        mViewBinding.rlRebateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RebateActivity").navigation();
            }
        });

        presenter.requestInvite();


    }

    @Permission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void saveQRcode() {
        int witd = DimensUtils.dp2px(this, 80);
        Bitmap imag = QRCodeUtils.createQRCodeBitmap(invite.getInvited_url(), witd, witd);
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String imageName = format.format(new Date()) + ".JPEG";
        ImageUtils.saveBitmap(this, imag, imageName);
        ToastUtils.showShort(getString(R.string.invite_save_qrcode_success_toast));
    }

    @Override
    public int getLayoutId() {
        return R.layout.invite;
    }

    @Override
    public void refreshView(Invite invite) {
        this.invite = invite;
        mViewBinding.tvSuccessRebate.setText("¥" + invite.getAlready_withdraw_money());
        mViewBinding.tvCanRebate.setText("¥" + invite.getCan_withdraw_money());
        mViewBinding.tvLinkAddress.setText(invite.getInvited_url());
        if (invite.getOne_invite() != null && invite.getOne_invite().size() > 0) {
            mViewBinding.tvInviteNum.setText(getString(R.string.invite_my_invite) + "(" + invite.getOne_invite().size() + ")");
        }
        int witd = DimensUtils.dp2px(this, 80);
        mViewBinding.imgQRCode.setImageBitmap(QRCodeUtils.createQRCodeBitmap(invite.getInvited_url(), witd, witd));
    }
}
