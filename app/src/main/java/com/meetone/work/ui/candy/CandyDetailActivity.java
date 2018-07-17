package com.meetone.work.ui.candy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.framework.event.WebViewEvent;
import com.cai.framework.web.WebViewFragment;
import com.meetone.work.R;
import com.meetone.work.databinding.CandyDetailBinding;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseActivity;
import com.meetone.work.bean.CandyDetailModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/CandyDetailActivity", name = "糖果详情页")
public class CandyDetailActivity extends AppBaseActivity<CandyDetailBinding> implements CandyDetailView {
    @Inject
    CandyDetailPresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    @Autowired(name = "tokenId")
    int tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        mViewBinding.titleBar.hideTitle();
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.titleBar.setRightText(getString(R.string.record));
        mViewBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.isLogin()) {
                    presenter.getStatistics().tgxq_jl();
                    ARouter.getInstance().build("/MeetOne/RecordActivity").withInt("tokenId", tokenId).navigation();
                } else {
                    ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
                }
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {
            @Override
            public void onLoadViewClick(int status) {
                presenter.loadCandyDetail(tokenId);
            }
        });
        presenter.loadCandyDetail(tokenId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.candy_detail;
    }

    @Override
    public void callback(String message, int type) {
        if (type == 1) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        }
        ToastUtils.showShort(message);
    }

    @Override
    public void callback(CandyDetailModel candyDetailModel) {
        refreshHead(candyDetailModel);
    }

    private void refreshHead(CandyDetailModel candyDetailModel) {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(candyDetailModel.getToken_icon())
                .error(R.drawable.default_avatar)
                .placeholder(R.drawable.default_avatar)
                .transformation(new GlideCircleTransform(this))
                .build();
        imageParams.setImageView(mViewBinding.imgHead);
        iLoadImage.loadImage(this, imageParams);

        mViewBinding.tvTitle.setText(candyDetailModel.getToken_symbol());
        mViewBinding.tvTotal.setText(candyDetailModel.getCandy_total());

        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.KEY_RUL, candyDetailModel.getToken_html());
        WebViewFragment webViewFragment = (WebViewFragment) Fragment.instantiate(this, WebViewFragment.class.getName(), bundle);
        webViewFragment.setLoadNewActivity(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.webViewcontain, webViewFragment);
        fragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWebViewEvent(WebViewEvent event) {
        if (event.type == WebViewEvent.TYPE_LOAD_FINISH) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }
}
