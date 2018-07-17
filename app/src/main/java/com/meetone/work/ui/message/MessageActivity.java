package com.meetone.work.ui.message;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.meetone.work.R;
import com.meetone.work.databinding.MessageBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseActivity;
import com.meetone.work.bean.Message;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/MessageActivity", name = "消息")
public class MessageActivity extends AppBaseActivity<MessageBinding> implements MessageView {

    @Inject
    MessagePresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    PtrRecyclerView recyclerView;
    MessageAdapter adapter;

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
        initTitleBar();

        recyclerView = (PtrRecyclerView) mViewBinding.pullRecyclerView.getRecyclerView();
        mViewBinding.pullRecyclerView.setCloseLoadMore(true);
        adapter = new MessageAdapter(this, iLoadImage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mViewBinding.pullRecyclerView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(PtrFrameLayout frame) {
                presenter.loadMsgData(false);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {
            @Override
            public void onLoadViewClick(int status) {
                presenter.loadMsgData(false);
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.loadMsgData(true);
    }

    private void initTitleBar() {
        mViewBinding.titleBar.setTitleText(getString(R.string.message));
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.message;
    }

    @Override
    public void callBack(List<Message> messageList) {
        if (messageList != null && !messageList.isEmpty()) {
            adapter.setDatas(messageList);
        }
        mViewBinding.pullRecyclerView.refreshOrLoadMoreComplete(false);
        if (adapter.getDatas().isEmpty()) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        } else {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }
}
