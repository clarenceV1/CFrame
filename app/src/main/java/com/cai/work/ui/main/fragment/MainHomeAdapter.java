package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.widget.CircleView;
import com.cai.framework.widget.ListViewEx;
import com.cai.framework.widget.VerticalScrollTextView;
import com.cai.work.R;
import com.cai.work.bean.Forward;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.bean.home.HomeNoticeData;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeRangeData;
import com.cai.work.bean.home.HomeStockData;
import com.cai.work.bean.home.HomeWphyData;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int NOTICE = 0;
    public static final int STOCK = 1;
    public static final int FORWARD = 2;
    public static final int RANGE = 3;
    public static final int Help = 4;

    HomeItemData data;
    Context context;
    ILoadImage imageLoader;
    FragmentManager fragmentManager;
    int selectedTabType = 1;

    public MainHomeAdapter(Context context, ILoadImage imageLoader, HomeItemData data, FragmentManager fragmentManager) {
        this.data = data;
        this.context = context;
        this.imageLoader = imageLoader;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NOTICE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notice, parent, false);
            return new MainHomeAdapter.NoticeViewHolder(view);
        } else if (viewType == STOCK) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_stock, parent, false);
            return new MainHomeAdapter.StockViewHolder(view);
        } else if (viewType == FORWARD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_forward, parent, false);
            return new MainHomeAdapter.ForwardViewHolder(view);
        } else if (viewType == RANGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_range, parent, false);
            return new MainHomeAdapter.RangeViewHolder(view);
        } else if (viewType == Help) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_help, parent, false);
            return new MainHomeAdapter.HelpViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
        rangeViewHolder.listViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        rangeViewHolder.llLookRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RankActivity")
                        .withCharSequence("dataList", JSON.toJSONString(rangeData))
                        .navigation();
            }
        });
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
            stockViewHolder.tvTradeState.setText("交易中");
        } else {
            stockViewHolder.tvTradeState.setText("休市中");
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

    private void onBindNoticeView(NoticeViewHolder noticeViewHolder, List<HomeNoticeData> noticeDataList) {
        VerticalScrollTextView scrollTextview = noticeViewHolder.scrollTextView;
        scrollTextview.animationStart();
        ArrayList<String> textList = new ArrayList<>();
        for (HomeNoticeData homeNoticeData : noticeDataList) {
            textList.add(homeNoticeData.getTitle());
        }
        scrollTextview.setStrList(textList);
        noticeViewHolder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/NewsActivity").navigation();
            }
        });
        noticeViewHolder.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getNphy() != null) {
                    HomeStockData stockData = data.getStock();
                    if (stockData != null) {
                        ARouter.getInstance().build("/AppModule/StockActivity").navigation();
                    }
                }
            }
        });
        noticeViewHolder.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getNphy() != null) {
                    List<HomeNphyData> nphyList = data.getNphy();
                    if (nphyList.size() > 0) {
                        HomeNphyData nphyData = nphyList.get(0);
                        Forward forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                        ARouter.getInstance().build("/AppModule/ForwardActivity").withCharSequence("forwardJson", JSON.toJSONString(forward)).navigation();
                    }
                }
            }
        });
        noticeViewHolder.rlTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getNphy() != null) {
                    HomeStockData stockData = data.getStock();
                    if (stockData != null) {
                        ARouter.getInstance().build("/AppModule/StockActivity").navigation();
                    }
                }
            }
        });
        noticeViewHolder.rlTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getNphy() != null) {
                    List<HomeNphyData> nphyList = data.getNphy();
                    if (nphyList.size() > 0) {
                        HomeNphyData nphyData = nphyList.get(0);
                        Forward forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                        ARouter.getInstance().build("/AppModule/ForwardActivity").withCharSequence("forwardJson", JSON.toJSONString(forward)).navigation();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
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


    class NoticeViewHolder extends RecyclerView.ViewHolder {
        VerticalScrollTextView scrollTextView;
        TextView tvMore;
        RelativeLayout rlTab1, rlTab2, rlTab3, rlTab4;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            scrollTextView = (VerticalScrollTextView) itemView.findViewById(R.id.scrollTextView);
            tvMore = (TextView) itemView.findViewById(R.id.tvMore);
            rlTab1 = (RelativeLayout) itemView.findViewById(R.id.rlTab1);
            rlTab2 = (RelativeLayout) itemView.findViewById(R.id.rlTab2);
            rlTab3 = (RelativeLayout) itemView.findViewById(R.id.rlTab3);
            rlTab4 = (RelativeLayout) itemView.findViewById(R.id.rlTab4);
        }
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView tvStockNmae, tvTradeState, tvRemark, tvBound, tvTradeTime, tvShortCode;
        CircleView circleView;
        RelativeLayout rlStock;

        public StockViewHolder(View itemView) {
            super(itemView);
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

    class ForwardViewHolder extends RecyclerView.ViewHolder {
        TextView tvHomeTabLeft, tvHomeTabRight;
        FrameLayout homeForwardContainer;
        View bottomLine1, bottomLine2;
        RelativeLayout rlTab1, rlTab2;

        public ForwardViewHolder(View itemView) {
            super(itemView);
            tvHomeTabLeft = (TextView) itemView.findViewById(R.id.tvHomeTabLeft);
            tvHomeTabRight = (TextView) itemView.findViewById(R.id.tvHomeTabRight);
            homeForwardContainer = (FrameLayout) itemView.findViewById(R.id.homeForwardContainer);
            bottomLine1 = itemView.findViewById(R.id.bottomLine1);
            bottomLine2 = itemView.findViewById(R.id.bottomLine2);
            rlTab1 = (RelativeLayout) itemView.findViewById(R.id.rlTab1);
            rlTab2 = (RelativeLayout) itemView.findViewById(R.id.rlTab2);
        }
    }

    class RangeViewHolder extends RecyclerView.ViewHolder {
        ListViewEx listViewEx;
        LinearLayout llLookRank;

        public RangeViewHolder(View itemView) {
            super(itemView);
            listViewEx = (ListViewEx) itemView.findViewById(R.id.listViewEx);
            llLookRank = (LinearLayout) itemView.findViewById(R.id.llLookRank);
        }
    }

    class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView tvAboutUs, tvHelpCenter, tvSave, tvAskOnline;

        public HelpViewHolder(View itemView) {
            super(itemView);
            tvAboutUs = (TextView) itemView.findViewById(R.id.tvAboutUs);
            tvHelpCenter = (TextView) itemView.findViewById(R.id.tvHelpCenter);
            tvSave = (TextView) itemView.findViewById(R.id.tvSave);
            tvAskOnline = (TextView) itemView.findViewById(R.id.tvAskOnline);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof NoticeViewHolder) {
            NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
            noticeViewHolder.scrollTextView.animationStop();
        }
    }
}
