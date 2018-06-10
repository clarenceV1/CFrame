package com.cai.work.ui.redPacket;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RedPacket;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RedPacketBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RedPacketActivity", name = "我的红包")
public class RedPacketActivity extends AppBaseActivity<RedPacketBinding> implements RedPacketView {
    @Inject
    RedPacketPresenter presenter;

    RedPacketAdapter adapter;
    int page = 1;
    int total = 0;//总数量
    int total_page = 2;//总页数
    ListView listView;

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
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.red_packet_titile));
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new RedPacketAdapter(this);
        listView.setAdapter(adapter);
        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mViewBinding.pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page < total_page) {
                    ++page;
                    presenter.getRedPacket(page);
                }
            }
        });
        presenter.getRedPacket(page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.red_packet;
    }

    @Override
    public void refreshData(RedPacket data) {
        mViewBinding.pullListView.onRefreshComplete();
        page = data.getCurrent();
        total = data.getTotal();
        total_page = data.getTotal_page();
        adapter.update(data.getData());

        if (page == total_page) {
            mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            View view = LayoutInflater.from(this).inflate(R.layout.listview_foot, null);
            listView.addFooterView(view);
        }
    }

    @Override
    public void showToast(String message) {

    }
}
