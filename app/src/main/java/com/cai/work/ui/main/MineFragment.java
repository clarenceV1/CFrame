package com.cai.work.ui.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.pullrefresh.swipemenulistview.BasePtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.MineModel;
import com.cai.work.bean.User;
import com.cai.work.databinding.MineBinding;
import com.cai.work.utils.ShareUtil;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MineFragment extends AppBaseFragment<MineBinding> implements MineView, View.OnClickListener {

    @Inject
    MinePresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    InviteAdapter adapter;

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine;
    }

    @Override
    public void initView(View view) {
        adapter = new InviteAdapter(getContext(), presenter.getDefaultData());
        mViewBinding.gridInvite.setAdapter(adapter);

        initLinster();
        presenter.loadMineData(true);
    }

    private void initLinster() {
        mViewBinding.btnMessage.setOnClickListener(this);
        mViewBinding.btnHelp.setOnClickListener(this);
        mViewBinding.btnAbout.setOnClickListener(this);
        mViewBinding.rlHeadView.setOnClickListener(this);
        mViewBinding.btnSetting.setOnClickListener(this);
        mViewBinding.ivShareWX.setOnClickListener(this);
        mViewBinding.ivShareWxq.setOnClickListener(this);
        mViewBinding.ivShareSina.setOnClickListener(this);
        mViewBinding.btnShareCopy.setOnClickListener(this);
        mViewBinding.pullToRefresh.setOnPullLoadListener(new BasePtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(PtrFrameLayout frame) {
                presenter.loadMineData(false);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    public void setDefultShow(User user) {
        if (user != null && !TextUtils.isEmpty(user.getToken())) {//login
            mViewBinding.tvNickname.setVisibility(View.VISIBLE);
            mViewBinding.tvPhone.setVisibility(View.VISIBLE);
            mViewBinding.btnLogin.setVisibility(View.GONE);

            mViewBinding.tvNickname.setText(user.getNickname());
            mViewBinding.tvPhone.setText(user.getPhone());

            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url(user.getAvatar())
                    .build();
            imageParams.setImageView(mViewBinding.imgIcon);
            iLoadImage.loadImage(this, imageParams);
        } else { //loginout
            mViewBinding.tvNickname.setVisibility(View.GONE);
            mViewBinding.tvPhone.setVisibility(View.GONE);
            mViewBinding.btnLogin.setVisibility(View.VISIBLE);
            mViewBinding.tvMyInvite.setText(getInvite("0", 0));
            mViewBinding.tvShareTip.setText(R.string.share_tip);

            Glide.with(this).load(R.drawable.default_avatar).into(mViewBinding.imgIcon);
        }
    }

    private Spanned getInvite(String people, int grand_total) {
        return Html.fromHtml(getString(R.string.my_invite
                , StringUtils.formatColorText(people, Color.parseColor("#D0021B"))
                , StringUtils.formatColorText(grand_total + "", Color.parseColor("#D0021B"))));
    }

    @Override
    public void updataMineData(MineModel data) {
        User user = data.getUser();
        setDefultShow(user);
        if (data.getInviteList() != null) {
            adapter.update(data.getInviteList());
        }
        if (!TextUtils.isEmpty(data.getInviterule())) {
            mViewBinding.tvShareTip.setText(data.getInviterule().replaceAll("\\r", ""));
        }
        if (data.getInvite() != null && data.getBonus() != null) {
            mViewBinding.tvMyInvite.setText(getInvite(data.getInviteTotal() + "", data.getBonus().getGrand_total()));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnMessage:
                // MessageActivity.entryActivity();
                break;
            case R.id.btnHelp:
                Map<String, String> params = new HashMap<>();
                params.put("timestamp", System.currentTimeMillis() + "");
                ARouter.getInstance().build("/MeetOne/WebActivity")
                        .withCharSequence("title", getString(R.string.help))
                        .withCharSequence("url", App.H5_NAME + "/help.html")
                        .withCharSequence("paramMap", JSON.toJSONString(params))
                        .navigation();
                break;
            case R.id.btnAbout:
//                AboutActivity.entryActivity();
                break;
            case R.id.rlHeadView:
            case R.id.btnSetting:
                if (presenter.isLogin()) {
//                    UserActivity.entryActivity();
                } else {
//                    LoginActivity.entryActivity(false);
                }
                break;
            case R.id.ivShareWX:
                if (presenter.isLogin()) {
                    ShareUtil.shareToWeiXin(getContext(),presenter.getShareText());
                } else {
//                    LoginActivity.entryActivity(false);
                }
                break;
            case R.id.ivShareWxq:
                if (presenter.isLogin()) {
                    ShareUtil.shareToWeiXin(getContext(),presenter.getShareText());
                } else {
//                    LoginActivity.entryActivity(false);
                }
                break;
            case R.id.ivShareQq:
                if (presenter.isLogin()) {
                    ShareUtil.shareToQQ(getContext(),presenter.getShareText());
                } else {
//                    LoginActivity.entryActivity(false);
                }

            case R.id.ivShareSina:
                if (presenter.isLogin()) {
                    ShareUtil.shareToSina(getContext(),presenter.getShareText());
                } else {
//                    LoginActivity.entryActivity(false);
                }
            case R.id.btnShareCopy:
                if (presenter.isLogin()) {
                    String share = presenter.copyShareText();
                    if (StringUtils.isEmpty(share)) {
                        ToastUtils.showShort(R.string.shareContentFailed);
                    } else {
                        ToastUtils.showShort(R.string.shareContentSuccessfully);
                    }
                    try {
                        ClipData mClipData = ClipData.newPlainText("share", share);
                        ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        if (cmb != null) {
                            cmb.setPrimaryClip(mClipData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    LoginActivity.entryActivity(false);
                }
                break;
        }
    }
}
