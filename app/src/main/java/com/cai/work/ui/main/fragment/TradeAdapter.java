package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.framework.widget.CircleView;
import com.cai.framework.widget.CustomExpandListview;
import com.cai.work.R;
import com.cai.work.bean.Trade;
import com.cai.work.bean.TradeItem;

import java.util.List;

public class TradeAdapter extends BaseExpandableListAdapter implements CustomExpandListview.HeaderAdapter {
    Trade trade;
    Context context;
    private CustomExpandListview listview;

    public TradeAdapter(Context context, CustomExpandListview listview) {
        this.context = context;
        this.listview = listview;
    }

    public void update(Trade trade) {
        this.trade = trade;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        int count = 0;
        if (trade != null) {
            if (trade.getGj_contract() != null) {
                count++;
            }
            if (trade.getGn_contract() != null) {
                count++;
            }
            if (trade.getStock() != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return 1;
        } else if (groupPosition == 2) {
            return trade.getGn_contract().size();
        } else if (groupPosition == 1) {
            return trade.getGj_contract().size();
        }
        return 0;
    }

    @Override
    public String getGroup(int groupPosition) {
        if (groupPosition == 0) {
            return trade.getGp();
        } else if (groupPosition == 1) {
            return trade.getGj();
        } else if (groupPosition == 2) {
            return trade.getGn();
        }
        return "";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return trade.getStock();
        } else if (groupPosition == 2) {
            return trade.getGn_contract().get(childPosition);
        } else if (groupPosition == 1) {
            return trade.getGj_contract().get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trade_group_item, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvGroupName.setText(getGroup(groupPosition));
        if (isExpanded) {
            groupViewHolder.tvArrow.setBackgroundResource(R.drawable.jy_open);
        } else {
            groupViewHolder.tvArrow.setBackgroundResource(R.drawable.jy_sousuo);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trade_item, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        Object data = getChild(groupPosition, childPosition);
        if (data != null) {
            TradeItem tradeItem = null;
            if (groupPosition == 0) {
                tradeItem = trade.getStock();
                childViewHolder.tvStockNmae.setText(tradeItem.getContractName());
            } else if (groupPosition == 2) {
                List<TradeItem> gnList = trade.getGn_contract();
                tradeItem = gnList.get(childPosition);
            } else if (groupPosition == 1) {
                List<TradeItem> gjList = trade.getGj_contract();
                tradeItem = gjList.get(childPosition);
            }
            childViewHolder.tvStockNmae.setText(tradeItem.getContractName());
            childViewHolder.circleView.setColor("#" + tradeItem.getColor());
            childViewHolder.tvShortCode.setText(tradeItem.getShortCode());

            if (!TextUtils.isEmpty(tradeItem.getMk_price())) {
                childViewHolder.tvHandMoney.setText(tradeItem.getMk_price());
                childViewHolder.tvHandMoney.setVisibility(View.VISIBLE);
            } else {
                childViewHolder.tvHandMoney.setText(tradeItem.getMk_price());
                childViewHolder.tvHandMoney.setVisibility(View.GONE);
            }
            if (groupPosition == 0) {
                childViewHolder.tvTradeState.setText(tradeItem.getContractCode());
                childViewHolder.tvTradeState.setVisibility(View.VISIBLE);
                childViewHolder.tvRemark.setText(tradeItem.getRemark());
            } else {
                childViewHolder.tvTradeState.setVisibility(View.GONE);
                childViewHolder.tvRemark.setText(tradeItem.getContractCode());
            }
            childViewHolder.tvPrice.setText(tradeItem.getZdfu() + "%");
            if (tradeItem.getZdfu().contains("-")) {
                childViewHolder.tvHandMoney.setTextColor(context.getResources().getColor(R.color.ys_0_229_0));
                childViewHolder.tvPrice.setTextColor(context.getResources().getColor(R.color.ys_0_229_0));
            } else {
                childViewHolder.tvHandMoney.setTextColor(context.getResources().getColor(R.color.ys_241_83_83));
                childViewHolder.tvPrice.setTextColor(context.getResources().getColor(R.color.ys_241_83_83));
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listview.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        if (groupPosition > -1) {
            ((TextView) header.findViewById(R.id.tvGroupName)).setText(getGroup(groupPosition));
        }
    }

    static class GroupViewHolder {
        TextView tvGroupName;
        ImageView tvArrow;

        public GroupViewHolder(View view) {
            this.tvGroupName = (TextView) view.findViewById(R.id.tvGroupName);
            this.tvArrow = (ImageView) view.findViewById(R.id.tvArrow);
        }
    }

    static class ChildViewHolder {
        TextView tvStockNmae, tvShortCode, tvRemark, tvHandMoney, tvTradeState, tvPrice;
        CircleView circleView;

        public ChildViewHolder(View view) {
            this.tvStockNmae = (TextView) view.findViewById(R.id.tvStockNmae);
            this.tvShortCode = (TextView) view.findViewById(R.id.tvShortCode);
            this.tvTradeState = (TextView) view.findViewById(R.id.tvTradeState);
            this.tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            this.tvRemark = (TextView) view.findViewById(R.id.tvRemark);
            this.tvHandMoney = (TextView) view.findViewById(R.id.tvHandMoney);
            this.circleView = (CircleView) view.findViewById(R.id.circleView);
        }

    }
}
