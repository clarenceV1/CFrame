package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.cai.framework.pull.PullToRefreshBase;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.ForwardAccount;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.StockAccount;
import com.cai.work.bean.StockHold;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainHoldFragmentBinding;
import com.cai.work.socket.SocThread;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

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

    Handler mhandler;
    Handler mhandlerSend;
    private String TAG = "socket thread";
    SocThread socketThread;
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
        DaggerAppComponent.create().inject(this);
    }


    @Override
    public void initView(View view) {
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new MainHoldAdapter(getContext());
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
                    presenter.requestData(isRealTrade, isStock, isHolder, page);
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
//        initSocket();
//        String userID = presenter.getUserId();
//        socketThread.Send("hold|0|" + userID + "|mn");
    }

    @SuppressLint("HandlerLeak")
    private void initSocket() {
        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Log.i(TAG, "mhandler接收到msg=" + msg.what);
                    if (msg.obj != null) {
                        String s = msg.obj.toString();
                        if (s.trim().length() > 0) {
                            Log.i(TAG, "mhandler接收到obj=" + s);
                            Log.i(TAG, "开始更新UI");
                            Log.i(TAG, "Server:" + s);
                            Log.i(TAG, "更新UI完毕");
                        } else {
                            Log.i(TAG, "没有数据返回不更新");
                        }
                    }
                } catch (Exception ee) {
                    Log.i(TAG, "加载过程出现异常");
                    ee.printStackTrace();
                }
            }
        };
        mhandlerSend = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Log.i(TAG, "mhandlerSend接收到msg.what=" + msg.what);
                    String s = msg.obj.toString();
                    if (msg.what == 1) {
                        Log.i(TAG, "\n ME: " + s + "      发送成功");
                    } else {
                        Log.i(TAG, "\n ME: " + s + "     发送失败");
                    }
                } catch (Exception ee) {
                    Log.i(TAG, "加载过程出现异常");
                    ee.printStackTrace();
                }
            }
        };
        startSocket();
    }

    public void startSocket() {
        socketThread = new SocThread(mhandler, mhandlerSend, getContext());
        socketThread.start();
    }

    private void stopSocket() {
        if (socketThread != null) {
            socketThread.isRun = false;
            socketThread.close();
            socketThread = null;
            Log.i(TAG, "Socket已终止");
        }
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
            } else {
                mViewBinding.llHolder.setBackgroundResource(R.drawable.hold_bg);
                mViewBinding.llAccounts.setBackgroundResource(R.drawable.hold_bg_selected);
                hasAccountsData = true;
                page = 1;
                mViewBinding.tvHold.setTextColor(getResources().getColor(R.color.ys_102_102_102));
                mViewBinding.tvAccount.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            }
            this.isHolder = isHolder;
        }

        Logger.e("isRealTrade:" + isRealTrade + " isStock:" + isStock + " isHolder:" + isHolder);
        if (socketInfo != null) {
            isRequestData = false;
            presenter.requestData(isRealTrade, isStock, isHolder, page);
        } else {
            isRequestData = true;
            presenter.getSocketInfo();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "start onStop~~~");
        stopSocket();
    }

    @Override
    public void socketInfo(SocketInfo socketInfo) {
        this.socketInfo = socketInfo;
        if (isRequestData) {
            isRequestData = false;
            presenter.requestData(isRealTrade, isStock, isHolder, page);
        }
    }

    @Override
    public void toast(String msg, int type) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void stockHold(List<StockHold> dataList) {
        adapter.update(dataList);
    }

    @Override
    public void forwardAccount(List<ForwardAccount> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        if (page == 1) {
            adapter.update(dataList);
        }
        if (dataList == null || dataList.size() == 0) {
            hasAccountsData = false;
        } else {
            adapter.addAll(dataList);
        }
    }

    @Override
    public void stockAccount(List<StockAccount> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        adapter.update(dataList);
    }
}
