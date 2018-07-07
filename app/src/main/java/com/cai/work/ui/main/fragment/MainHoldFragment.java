package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cai.framework.pull.PullToRefreshBase;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.ForwardAccount;
import com.cai.work.bean.ForwardHold;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.StockAccount;
import com.cai.work.bean.StockHold;
import com.cai.work.databinding.MainHoldFragmentBinding;
import com.cai.work.event.BankCardChooseEvent;
import com.cai.work.event.ForwardHoldEvent;
import com.cai.work.socket.SocketManager;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * 持仓
 */
public class MainHoldFragment extends AppBaseFragment<MainHoldFragmentBinding> implements HoldView {


    @Inject
    ILoadImage imageLoader;

    @Inject
    MainHoldPresenter presenter;

    boolean isRealTrade;//是否是实盘交易
    boolean isStock;//是股票还是期货
    boolean isHolder;//是否是持仓 还是结算

    SocketInfo socketInfo;
    boolean isRequestData = true;//是否要重新请求数据
    int page = 1;
    ListView listView;
    MainHoldAdapter adapter;
    boolean hasAccountsData = true;

    @Override
    public int getLayoutId() {
        return R.layout.main_hold_fragment;
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView(View view) {
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new MainHoldAdapter(getContext(), presenter);
        listView.setAdapter(adapter);
        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        // Add an end-of-list listener
        mViewBinding.pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (hasAccountsData && !isHolder) {
                    ++page;
                    presenter.requestData(isRealTrade, isStock, isHolder, page, socketInfo);
                }
            }
        });
        mViewBinding.tvTradeTabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData(true, true, true);
            }
        });
        mViewBinding.tvTradeTabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData(false, true, true);
            }
        });
        mViewBinding.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStock) {
                    return;
                }
                freshData(isRealTrade, true, isHolder);
            }
        });
        mViewBinding.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStock) {
                    return;
                }
                freshData(isRealTrade, false, isHolder);
            }
        });
        mViewBinding.llHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHolder) {
                    return;
                }
                freshData(isRealTrade, isStock, true);
            }
        });
        mViewBinding.llAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHolder) {
                    return;
                }
                freshData(isRealTrade, isStock, false);
            }
        });
        freshData(true, true, true);

    }

    private void freshData(boolean isRealTrade, boolean isStock, boolean isHolder) {
        if (this.isRealTrade != isRealTrade) {
            if (isRealTrade) {
                mViewBinding.tvHomeTabLeft.setText(getString(R.string.main_hold_stock_real));
                mViewBinding.tvHomeTabRight.setText(getString(R.string.main_hold_forward_real));

                mViewBinding.tvHold.setText(getString(R.string.main_hold_holder));
                mViewBinding.tvAccount.setText(getString(R.string.main_hold_accounts));

                mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.ys_255_255_255));
                mViewBinding.tvTradeTabLeft.setBackgroundResource(R.drawable.trade_tab_bg_selected_left);
                mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.ys_219_183_108));
                mViewBinding.tvTradeTabRight.setBackgroundColor(getResources().getColor(R.color.touming));
            } else {
                mViewBinding.tvHomeTabLeft.setText(getString(R.string.main_hold_stock_fake));
                mViewBinding.tvHomeTabRight.setText(getString(R.string.main_hold_forward_fake));

                mViewBinding.tvHold.setText(getString(R.string.main_hold_holder_fake));
                mViewBinding.tvAccount.setText(getString(R.string.main_hold_accounts_fake));

                mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.ys_219_183_108));
                mViewBinding.tvTradeTabLeft.setBackgroundColor(getResources().getColor(R.color.touming));
                mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.ys_255_255_255));
                mViewBinding.tvTradeTabRight.setBackgroundResource(R.drawable.trade_tab_bg_selected_right);
            }
            this.isRealTrade = isRealTrade;
        }
        if (this.isStock != isStock) {
            if (isStock) {
                mViewBinding.bottomLine1.setVisibility(View.VISIBLE);
                mViewBinding.bottomLine2.setVisibility(View.GONE);
                mViewBinding.tvHomeTabLeft.setTextColor(getResources().getColor(R.color.ys_202_169_101));
                mViewBinding.tvHomeTabRight.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            } else {
                mViewBinding.bottomLine1.setVisibility(View.GONE);
                mViewBinding.bottomLine2.setVisibility(View.VISIBLE);
                mViewBinding.tvHomeTabLeft.setTextColor(getResources().getColor(R.color.ys_255_255_255));
                mViewBinding.tvHomeTabRight.setTextColor(getResources().getColor(R.color.ys_202_169_101));
            }
            this.isStock = isStock;
        }
        if (this.isHolder != isHolder) {
            if (isHolder) {
                mViewBinding.llHolder.setBackgroundResource(R.drawable.hold_bg_selected);
                mViewBinding.llAccounts.setBackgroundResource(R.drawable.hold_bg);
                mViewBinding.tvHold.setTextColor(getResources().getColor(R.color.ys_255_255_255));
                mViewBinding.tvAccount.setTextColor(getResources().getColor(R.color.ys_102_102_102));

                mViewBinding.ivChiCang.setBackgroundResource(R.drawable.cc_chicangdianji);
                mViewBinding.ivJieSuan.setBackgroundResource(R.drawable.cc_jiesuanweidianji);
            } else {
                mViewBinding.llHolder.setBackgroundResource(R.drawable.hold_bg);
                mViewBinding.llAccounts.setBackgroundResource(R.drawable.hold_bg_selected);
                hasAccountsData = true;
                page = 1;
                mViewBinding.tvHold.setTextColor(getResources().getColor(R.color.ys_102_102_102));
                mViewBinding.tvAccount.setTextColor(getResources().getColor(R.color.ys_255_255_255));

                mViewBinding.ivChiCang.setBackgroundResource(R.drawable.cc_chicangweidianji);
                mViewBinding.ivJieSuan.setBackgroundResource(R.drawable.cc_jiesuandianji);
            }
            this.isHolder = isHolder;
        }

        Logger.e("isRealTrade:" + isRealTrade + " isStock:" + isStock + " isHolder:" + isHolder);
        if (socketInfo != null) {
            isRequestData = false;
            presenter.requestData(isRealTrade, isStock, isHolder, page, socketInfo);
        } else {
            isRequestData = true;
            presenter.getSocketInfo();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            SocketManager.pauseSocket();
        } else {
            SocketManager.resumeSocket();
        }
    }

    @Override
    public void socketInfo(SocketInfo socketInfo) {
        this.socketInfo = socketInfo;
        if (isRequestData) {
            isRequestData = false;
            presenter.requestData(isRealTrade, isStock, isHolder, page, socketInfo);
        }
    }

    @Override
    public void toast(String msg, int type) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void stockHold(List<StockHold> dataList) {
        adapter.update(dataList,isRealTrade);
    }

    @Override
    public void forwardAccount(List<ForwardAccount> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        if (dataList == null || dataList.size() == 0) {
            hasAccountsData = false;
        } else if (page == 1) {
            adapter.update(dataList, isRealTrade);
        } else {
            adapter.addAll(dataList);
        }
    }

    @Override
    public void stockAccount(List<StockAccount> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        adapter.update(dataList, isRealTrade);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chooseBankCard(ForwardHoldEvent event) {
        List<ForwardHold> data = event.data;
        adapter.update(data, isRealTrade);
    }
}
