package com.cai.work.ui.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RecordDataModel;
import com.cai.work.bean.RecordModel;
import com.cai.work.databinding.RecordBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/RecordActivity", name = "记录")
public class RecordActivity extends AppBaseActivity<RecordBinding> implements RecordView {

    @Inject
    RecordPresent present;
    @Autowired(name = "tokenId")
    int tokenId;
    PtrRecyclerView mPtrRecyclerView;
    RecordAdapter mRecordAdapter;
    private int lastId;

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
        observerList.add(present);
    }

    @Override
    public void initView() {
        mViewBinding.titleBar.setTitleText(getString(R.string.record));
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.recyclerView.getRecyclerView();
        //
        mRecordAdapter = new RecordAdapter(this);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPtrRecyclerView.setAdapter(mRecordAdapter);

        mViewBinding.recyclerView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(PtrFrameLayout frame) {
                present.loadRecord(0, tokenId);
            }

            @Override
            public void onLoadMore() {
                present.loadRecord(lastId, tokenId);
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        present.loadRecord(lastId, tokenId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.record;
    }

    @Override
    public void callBack(RecordDataModel data, int lastid) {
        mViewBinding.recyclerView.refreshOrLoadMoreComplete(true);
        lastId = data.getLastid();
        List<RecordModel> records = data.getRecord();
        if (records != null && !records.isEmpty()) {
            if (lastid == 0) {
                mRecordAdapter.setDatas(records);
            } else {
                mRecordAdapter.addDatas(records);
            }
        }
        if (mRecordAdapter.getDatas().isEmpty()) {
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
