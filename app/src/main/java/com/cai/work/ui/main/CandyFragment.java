package com.cai.work.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.framework.widget.dialog.LoadDialog;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.CandyList;
import com.cai.work.databinding.CandyBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class CandyFragment extends AppBaseFragment<CandyBinding> implements CandyView {
    @Inject
    CandyPresenter presenter;
    ListView listView;
    CandyAdapter adapter;
    @Inject
    ILoadImage iLoadImage;
    LoadDialog loadDialog;

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
        return R.layout.candy;
    }

    @Override
    public void initView(View view) {
        initHead();

        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view,null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        listView = mViewBinding.pullListView.getRefreshableView();
        mViewBinding.pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestCandyList(false);
            }
        });
        adapter = new CandyAdapter(getContext(), iLoadImage, presenter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CandyList candyList = adapter.getItem(position);
                if (candyList.getType() == 2) {
                    ARouter.getInstance().build("/MeetOne/WebActivity").withCharSequence("url", candyList.getUri()).navigation();
                } else {
                    ARouter.getInstance().build("/MeetOne/CandyDetailActivity").withInt("tokenId", candyList.getToken_id()).navigation();
                }
            }
        });
        presenter.requestCandyList(true);
    }

    private void initHead() {
        mViewBinding.titleBar.setTitleText(getString(R.string.candy));
        mViewBinding.titleBar.hideBackBtn();
    }

    @Override
    public void callBack(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(List<CandyList> data) {
        mViewBinding.pullListView.onRefreshComplete();
        adapter.updateAll(data);
    }

    @Override
    public void showDialog() {
        loadDialog = new LoadDialog();
        loadDialog.show();
    }

    public void closeDialog() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }
}
