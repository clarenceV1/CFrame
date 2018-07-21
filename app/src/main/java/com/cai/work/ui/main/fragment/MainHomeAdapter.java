package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.widget.CircleView;
import com.cai.framework.widget.ListViewEx;
import com.cai.framework.widget.VerticalScrollTextView;
import com.cai.framework.widget.dialog.GodDialog;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.work.R;
import com.cai.work.bean.Forward;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.bean.home.HomeNoticeData;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeRangeData;
import com.cai.work.bean.home.HomeStockData;
import com.cai.work.bean.home.HomeWphyData;
import com.cai.work.event.ListViewScrollEvent;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainHomeAdapter extends BasePtrAdapter<HomeItemData, BasePtrViewHold> {
    public static final int NOTICE = 5;
    public static final int STOCK = 1;
    public static final int FORWARD = 2;
    public static final int RANGE = 3;
    public static final int Help = 4;

    Context context;
    ILoadImage imageLoader;
    FragmentManager fragmentManager;
    int selectedTabType = 1;
    MainHomePresenter presenter;
    //    ListViewEx rangeListView;
    HomeRangeAdapter mRangeAdapter;
    List<HomeRangeData> mRangeData;

    public MainHomeAdapter(Context context, ILoadImage imageLoader, FragmentManager fragmentManager, MainHomePresenter presenter) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.fragmentManager = fragmentManager;
        this.presenter = presenter;
        EventBus.getDefault().register(this);
    }

    @Override
    public BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        BasePtrViewHold viewHolder;
        if (viewType == NOTICE) {
            View view = inflateItemView(parent, R.layout.home_notice);
            viewHolder = new MainHomeAdapter.NoticeViewHolder(view, null);
        } else if (viewType == STOCK) {
            View view = inflateItemView(parent, R.layout.home_stock);
            viewHolder = new MainHomeAdapter.StockViewHolder(view, null);
        } else if (viewType == FORWARD) {
            View view = inflateItemView(parent, R.layout.home_forward);
            viewHolder = new MainHomeAdapter.ForwardViewHolder(view, null);
        } else if (viewType == RANGE) {
            View view = inflateItemView(parent, R.layout.home_range);
            viewHolder = new MainHomeAdapter.RangeViewHolder(view, null);
        } else/* if (viewType == Help)*/ {
            View view = inflateItemView(parent, R.layout.home_help);
            viewHolder = new MainHomeAdapter.HelpViewHolder(view, null);
        }
        return viewHolder;
    }

    @Override
    public void onPtrBindViewHolder(BasePtrViewHold holder, final HomeItemData data, int position) {
        if (holder instanceof NoticeViewHolder) {
            NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
            List<HomeNoticeData> noticeDataList = data.getNotice();
            onBindNoticeView(noticeViewHolder, noticeDataList);
        } else if (holder instanceof StockViewHolder) {
            StockViewHolder stockViewHolder = (StockViewHolder) holder;
            HomeStockData stockData = data.getStock();
            onBindStockView(stockViewHolder, stockData);
        } else if (holder instanceof ForwardViewHolder) {
            ForwardViewHolder forwardViewHolder = (ForwardViewHolder) holder;
            List<HomeNphyData> nphyData = data.getNphy();
            List<HomeWphyData> wphyData = data.getWphy();
            onBindForwardView(forwardViewHolder, nphyData, wphyData);
        } else if (holder instanceof RangeViewHolder) {
            RangeViewHolder rangeViewHolder = (RangeViewHolder) holder;
            List<HomeRangeData> rangeData = data.getRange();
            onBindRangeView(rangeViewHolder, rangeData);
        } else if (holder instanceof HelpViewHolder) {
            HelpViewHolder helpViewHolder = (HelpViewHolder) holder;
            onBindHelpView(helpViewHolder);
        }
    }

    private void onBindHelpView(HelpViewHolder helpViewHolder) {
        helpViewHolder.tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("帮助我们");
            }
        });
        helpViewHolder.tvAskOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("在线资讯");
            }
        });
        helpViewHolder.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("安全保障");
            }
        });
        helpViewHolder.tvHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("帮助中心");
            }
        });
    }

    private void onBindRangeView(RangeViewHolder rangeViewHolder, final List<HomeRangeData> rangeData) {
        HomeRangeAdapter rangeAdapter = new HomeRangeAdapter(context, rangeData);
        rangeViewHolder.listViewEx.setAdapter(rangeAdapter);
        mRangeAdapter = rangeAdapter;
        mRangeData = rangeData;

//        ARouter.getInstance().build("/AppModule/RankActivity")
//                .withCharSequence("dataList", JSON.toJSONString(rangeData))
//                .navigation();
    }

    private void onBindForwardView(final ForwardViewHolder forwardViewHolder, final List<HomeNphyData> nphyData, final List<HomeWphyData> wphyData) {
        forwardViewHolder.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 1) {
                    return;
                }
                selectedTabType = 1;
                switchTab(forwardViewHolder);
                swtichFragment(nphyData, wphyData);
            }
        });
        forwardViewHolder.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 2) {
                    return;
                }
                selectedTabType = 2;
                switchTab(forwardViewHolder);
                swtichFragment(nphyData, wphyData);
            }
        });
        switchTab(forwardViewHolder);
        swtichFragment(nphyData, wphyData);
    }

    private void switchTab(ForwardViewHolder forwardViewHolder) {
        if (selectedTabType == 1) {
            forwardViewHolder.bottomLine1.setVisibility(View.VISIBLE);
            forwardViewHolder.bottomLine2.setVisibility(View.GONE);
            forwardViewHolder.tvHomeTabLeft.setTextColor(context.getResources().getColor(R.color.ys_202_169_101));
            forwardViewHolder.tvHomeTabRight.setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
        } else {
            forwardViewHolder.bottomLine1.setVisibility(View.GONE);
            forwardViewHolder.bottomLine2.setVisibility(View.VISIBLE);
            forwardViewHolder.tvHomeTabLeft.setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
            forwardViewHolder.tvHomeTabRight.setTextColor(context.getResources().getColor(R.color.ys_202_169_101));
        }
    }

    private void swtichFragment(List<HomeNphyData> nphyData, List<HomeWphyData> wphyData) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment wpFragment = fragmentManager.findFragmentByTag(HomeForwardFragment.TYPE_WPHY);
        Fragment npFragment = fragmentManager.findFragmentByTag(HomeForwardFragment.TYPE_NPHY);
        if (selectedTabType == 1) {
            if (wpFragment != null) {
                transaction.hide(wpFragment);
            }
            if (npFragment == null) {
                npFragment = creatFragment(HomeForwardFragment.TYPE_NPHY, nphyData);
                transaction.add(R.id.homeForwardContainer, npFragment, HomeForwardFragment.TYPE_NPHY);
            } else {
                transaction.show(npFragment);
            }
        } else {
            if (npFragment != null) {
                transaction.hide(npFragment);
            }
            if (wpFragment == null) {
                wpFragment = creatFragment(HomeForwardFragment.TYPE_WPHY, wphyData);
                transaction.add(R.id.homeForwardContainer, wpFragment, HomeForwardFragment.TYPE_WPHY);
            } else {
                transaction.show(wpFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    public <T> Fragment creatFragment(String type, List<T> data) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("dataList", (Serializable) data);
        return Fragment.instantiate(context, HomeForwardFragment.class.getName(), bundle);
    }

    private void onBindStockView(StockViewHolder stockViewHolder, final HomeStockData stockData) {
        stockViewHolder.tvStockNmae.setText(stockData.getContractName());
        if (stockData.getIsTrade() == 1) {
            stockViewHolder.tvStockNmae.setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
            stockViewHolder.tvBound.setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
            stockViewHolder.tvTradeState.setText("交易中");
            stockViewHolder.tvTradeState.setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
            stockViewHolder.tvTradeState.setBackgroundResource(R.drawable.stock_code_bg);
        } else {
            stockViewHolder.tvStockNmae.setTextColor(context.getResources().getColor(R.color.ys_102_102_102));
            stockViewHolder.tvBound.setTextColor(context.getResources().getColor(R.color.ys_102_102_102));
            stockViewHolder.tvTradeState.setText("休市中");
            stockViewHolder.tvTradeState.setTextColor(context.getResources().getColor(R.color.ys_30_30_30));
            stockViewHolder.tvTradeState.setBackgroundResource(R.drawable.stock_code_gray_bg);
        }
        stockViewHolder.tvRemark.setText(stockData.getRemark());
        stockViewHolder.tvBound.setText(stockData.getBond() + "元起投");
        stockViewHolder.tvTradeTime.setText(stockData.getTradeTime());
        stockViewHolder.circleView.setColor("#" + stockData.getColor());
        stockViewHolder.tvShortCode.setText(stockData.getShortCode());
        stockViewHolder.rlStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/StockActivity").navigation();
            }
        });
    }

    private void onBindNoticeView(NoticeViewHolder noticeViewHolder, final List<HomeNoticeData> noticeDataList) {
        final VerticalScrollTextView scrollTextview = noticeViewHolder.scrollTextView;
        scrollTextview.animationStart();
        ArrayList<String> textList = new ArrayList<>();
        for (HomeNoticeData homeNoticeData : noticeDataList) {
            textList.add(homeNoticeData.getTitle());
        }
        scrollTextview.setFocusableInTouchMode(true);
        scrollTextview.requestFocus();
        scrollTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.isLogin()) {
                    int index = (scrollTextview.getIndex() - 1) % noticeDataList.size();
                    HomeNoticeData noticeData = noticeDataList.get(index);
                    ARouter.getInstance().build("/AppModule/NewsDetailActivity")
                            .withInt("news", noticeData.getId())
                            .withCharSequence("channelName", noticeData.getChannelName())
                            .navigation();
                } else {
                    showDialog();
                }
            }
        });
        scrollTextview.setStrList(textList);
        noticeViewHolder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.isLogin()) {
                    ARouter.getInstance().build("/AppModule/NewsActivity").navigation();
                } else {
                    showDialog();
                }
            }
        });
        if (datas == null && datas.size() == 0) {
            return;
        }
        final HomeItemData itemData = datas.get(0);

        noticeViewHolder.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemData != null && itemData.getNphy() != null) {
                    HomeStockData stockData = itemData.getStock();
                    if (stockData != null) {
                        if (presenter.isLogin()) {
                            ARouter.getInstance().build("/AppModule/StockActivity")
                                    .withBoolean("isRealTrade", true)
                                    .navigation();
                        } else {
                            showDialog();
                        }
                    }
                }
            }
        });
        noticeViewHolder.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemData != null && itemData.getNphy() != null) {
                    List<HomeNphyData> nphyList = itemData.getNphy();
                    if (nphyList.size() > 0) {
                        HomeNphyData nphyData = nphyList.get(0);
                        Forward forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                        if (presenter.isLogin()) {
                            ARouter.getInstance().build("/AppModule/ForwardActivity")
                                    .withCharSequence("forwardJson", JSON.toJSONString(forward))
                                    .withBoolean("isRealTrade", true)
                                    .navigation();
                        } else {
                            showDialog();
                        }
                    }
                }
            }
        });
        noticeViewHolder.rlTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemData != null && itemData.getNphy() != null) {
                    HomeStockData stockData = itemData.getStock();
                    if (stockData != null) {
                        if (presenter.isLogin()) {
                            ARouter.getInstance().build("/AppModule/StockActivity")
                                    .withBoolean("isRealTrade", false)
                                    .navigation();
                        } else {
                            showDialog();
                        }
                    }
                }
            }
        });
        noticeViewHolder.rlTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemData != null && itemData.getNphy() != null) {
                    List<HomeNphyData> nphyList = itemData.getNphy();
                    if (nphyList.size() > 0) {
                        HomeNphyData nphyData = nphyList.get(0);
                        Forward forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                        ARouter.getInstance().build("/AppModule/ForwardActivity")
                                .withCharSequence("forwardJson", JSON.toJSONString(forward))
                                .withBoolean("isRealTrade", false)
                                .navigation();
                    }
                }
            }
        });
    }

    private void showDialog() {
        new GodDialog.Builder(context)
                .setTitle(R.string.dialog_titile)
                .setMessage(R.string.please_login)
                .setPositiveButton(R.string.btn_commit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ARouter.getInstance().build("/AppModule/LoginActivity").navigation();
                    }
                })
                .setNegativeButton(R.string.btn_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).build().show();
    }

    @Override
    public int getPtrItemViewType(int position) {
        int type = NOTICE;
        switch (position) {
            case 0:
                type = NOTICE;
                break;
            case 1:
                type = STOCK;
                break;
            case 2:
                type = FORWARD;
                break;
            case 3:
                type = RANGE;
                break;
            case 4:
                type = Help;
                break;
        }
        return type;
    }


    class NoticeViewHolder extends BasePtrViewHold {
        VerticalScrollTextView scrollTextView;
        TextView tvMore;
        RelativeLayout rlTab1, rlTab2, rlTab3, rlTab4;

        public NoticeViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            scrollTextView = (VerticalScrollTextView) itemView.findViewById(R.id.scrollTextView);
            tvMore = (TextView) itemView.findViewById(R.id.tvMore);
            rlTab1 = (RelativeLayout) itemView.findViewById(R.id.rlTab1);
            rlTab2 = (RelativeLayout) itemView.findViewById(R.id.rlTab2);
            rlTab3 = (RelativeLayout) itemView.findViewById(R.id.rlTab3);
            rlTab4 = (RelativeLayout) itemView.findViewById(R.id.rlTab4);
        }
    }

    class StockViewHolder extends BasePtrViewHold {
        TextView tvStockNmae, tvTradeState, tvRemark, tvBound, tvTradeTime, tvShortCode;
        CircleView circleView;
        RelativeLayout rlStock;

        public StockViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvStockNmae = (TextView) itemView.findViewById(R.id.tvStockNmae);
            tvTradeState = (TextView) itemView.findViewById(R.id.tvTradeState);
            tvRemark = (TextView) itemView.findViewById(R.id.tvRemark);
            tvBound = (TextView) itemView.findViewById(R.id.tvBound);
            tvTradeTime = (TextView) itemView.findViewById(R.id.tvTradeTime);
            circleView = (CircleView) itemView.findViewById(R.id.circleView);
            tvShortCode = (TextView) itemView.findViewById(R.id.tvShortCode);
            rlStock = (RelativeLayout) itemView.findViewById(R.id.rlStock);
        }
    }

    class ForwardViewHolder extends BasePtrViewHold {
        TextView tvHomeTabLeft, tvHomeTabRight;
        FrameLayout homeForwardContainer;
        View bottomLine1, bottomLine2;
        RelativeLayout rlTab1, rlTab2;

        public ForwardViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvHomeTabLeft = (TextView) itemView.findViewById(R.id.tvHomeTabLeft);
            tvHomeTabRight = (TextView) itemView.findViewById(R.id.tvHomeTabRight);
            homeForwardContainer = (FrameLayout) itemView.findViewById(R.id.homeForwardContainer);
            bottomLine1 = itemView.findViewById(R.id.bottomLine1);
            bottomLine2 = itemView.findViewById(R.id.bottomLine2);
            rlTab1 = (RelativeLayout) itemView.findViewById(R.id.rlTab1);
            rlTab2 = (RelativeLayout) itemView.findViewById(R.id.rlTab2);
        }
    }

    class RangeViewHolder extends BasePtrViewHold {
        ListViewEx listViewEx;
        LinearLayout llLookRank;

        public RangeViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            listViewEx = (ListViewEx) itemView.findViewById(R.id.listViewEx);
            llLookRank = (LinearLayout) itemView.findViewById(R.id.llLookRank);
        }
    }

    class HelpViewHolder extends BasePtrViewHold {
        TextView tvAboutUs, tvHelpCenter, tvSave, tvAskOnline;

        public HelpViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvAboutUs = (TextView) itemView.findViewById(R.id.tvAboutUs);
            tvHelpCenter = (TextView) itemView.findViewById(R.id.tvHelpCenter);
            tvSave = (TextView) itemView.findViewById(R.id.tvSave);
            tvAskOnline = (TextView) itemView.findViewById(R.id.tvAskOnline);
        }
    }

//    @Override
//    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        if (holder instanceof NoticeViewHolder) {
//            NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
//            noticeViewHolder.scrollTextView.animationStop();
//        }
//    }

    int position = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void listViewScrollEvent(ListViewScrollEvent event) {
        Log.d("listViewScrollEvent", "刷新");
//        if (mRangeData != null && mRangeData.size() > 5 && mRangeAdapter != null) {
//            position++;
//            List newData = new ArrayList();
//            int size = mRangeData.size();
//            for (int i = position; i < position + 5; i++) {
//                newData.add(mRangeData.get(i % size));
//            }
//            mRangeAdapter.update(newData);
//        }
    }
}
