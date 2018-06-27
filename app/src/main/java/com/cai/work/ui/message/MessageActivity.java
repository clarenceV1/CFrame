package com.cai.work.ui.message;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.framework.widget.dialog.GodDialog;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Message;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MessageBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/MessageActivity", name = "消息中心")
public class MessageActivity extends AppBaseActivity<MessageBinding> implements MessageView {
    @Inject
    MessagePresenter presenter;

    MessageAdapter adapter;
    int page = 1;
    int total = 0;//总数量
    int total_page = 2;//总页数
    ListView listView;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.message_title));
        TextView tvRight = mViewBinding.commonHeadView.tvRight;
        tvRight.setText(getString(R.string.message_clean));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new MessageAdapter(this, presenter);
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
                    presenter.getMessage(page);
                }
            }
        });
        presenter.getMessage(page);
    }

    private void showDialog() {
        new GodDialog.Builder(this)
                .setMessage(R.string.clean_message)
                .setPositiveButton(R.string.btn_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.btn_clean, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanAllMessage();
                    }
                }).create().show();
    }

    private void cleanAllMessage() {
        presenter.deleteMessageAll();
        if (adapter != null) {
            adapter.cleanAll();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.message;
    }

    @Override
    public void refreshMessageList(Message data) {
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
        ToastUtils.showShort(message);
    }
}
