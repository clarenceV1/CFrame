package com.cai.work.ui.candy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.framework.web.WebViewFragment;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.CandyDetailModel;
import com.cai.work.databinding.CandyDetailBinding;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
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
                    ARouter.getInstance().build("/MeetOne/RecordActivity").withInt("tokenId", tokenId).navigation();
                } else {
                    ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
                }
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
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
        mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.webViewcontain, webViewFragment);
        fragmentTransaction.commit();
    }
}
