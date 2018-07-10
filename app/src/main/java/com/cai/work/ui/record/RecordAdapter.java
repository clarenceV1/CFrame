package com.cai.work.ui.record;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.cai.work.R;
import com.cai.work.bean.RecordModel;
import com.example.clarence.utillibrary.StringUtils;

/**
 * Created by davy on 2018/3/5.
 */

public class RecordAdapter extends BasePtrAdapter<RecordModel, RecordAdapter.ViewHolder> {
    Context context;

    public RecordAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, RecordModel data, int position) {

        if (data.getType() == 1000) {
            holder.tvProfit.setText(context.getResources().getString(R.string.profit_1000));
        } else {
            holder.tvProfit.setText(context.getResources().getString(R.string.profit));
        }
        holder.profit_tv.setText("+" + StringUtils.formatNum(data.getCandy_total()));
        holder.time_tv.setText(data.getCreate_dt());

    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.record_item);
        return new ViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
    }

    class ViewHolder extends BasePtrViewHold {
        TextView profit_tv;
        TextView time_tv, tvProfit;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            profit_tv = getView(R.id.record_item_profit_tv);
            time_tv = getView(R.id.record_item_time_tv);
            tvProfit = getView(R.id.tvProfit);
        }
    }
}