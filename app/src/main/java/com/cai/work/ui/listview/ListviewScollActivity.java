package com.cai.work.ui.listview;

import android.os.Bundle;
import android.text.Layout;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.annotation.aspect.SingleClick;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ListViewScollBinding;
import com.cai.work.ui.adapter.ScrollAdapter;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/ListViewActivity", name = "列表")
public class ListviewScollActivity extends AppBaseActivity<ListViewScollBinding> implements ListViewForRTB {
    @Inject
    ListViewPresenterForRTB presenterForRTB;

    AbsListView.OnScrollListener onScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_view_scoll;
    }

    @Override
    public void initView() {
        TextView textView = new TextView(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(DeviceUtils.getScreenWidth(this), DimensUtils.dp2px(this, 100));
        textView.setLayoutParams(layoutParams);
        mViewBinding.lvContainer.addHeaderView(textView);
        ScrollAdapter scrollAdapter = new ScrollAdapter(this, presenterForRTB.getDatas());
        mViewBinding.lvContainer.setAdapter(scrollAdapter);
        onScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        };

        mViewBinding.videoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                onScrollListener.onScrollStateChanged(view,scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenterForRTB);
    }


    @SingleClick
    public void goToMainActivity(View view) {
        presenterForRTB.goMainActivity();
        finish();
    }

}
