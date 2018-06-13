package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.work.R;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineListData;
import com.cai.work.bean.MineTopData;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

public class MainMineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TOP_VIEW = 1;
    public static final int LIST_VIEW = 2;
    public static final int BOTTOM_VIEW = 3;

    List<IRecycleViewBaseData> dataList;

    OnItemClickListener onItemClickListener;

    ILoadImage imageLoader;
    Context context;
    MainMinePresenter presenter;

    public MainMineAdapter(Context context, ILoadImage imageLoader, MainMinePresenter presenter) {
        this.imageLoader = imageLoader;
        this.context = context;
        this.presenter = presenter;
    }

    public void updateData(List<IRecycleViewBaseData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TOP_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_top, parent, false);
            return new TopViewHolder(view);
        } else if (viewType == LIST_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_item, parent, false);
            return new ListViewHolder(view);
        } else if (viewType == BOTTOM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_bottom, parent, false);
            return new BottomViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            MineTopData mineTopData = (MineTopData) dataList.get(position);
            onBindTopView(topViewHolder, mineTopData);
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            MineListData mineListData = (MineListData) dataList.get(position);
            onBindListView(listViewHolder, mineListData, position);
        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            onBindListView(bottomViewHolder);
        }
    }

    private void onBindTopView(TopViewHolder topViewHolder, MineTopData mineTopData) {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(mineTopData.getHeadIcon()).build();
        imageParams.setImageView(topViewHolder.ivIcon);
        imageLoader.loadImage(context, imageParams);
        topViewHolder.tvAccount.setText(mineTopData.getAccount());
        topViewHolder.tvMoney.setText(mineTopData.getMoney());
        topViewHolder.btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RechargeActivity").navigation();
            }
        });
        topViewHolder.btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("提现");
            }
        });
    }

    private void onBindListView(ListViewHolder listViewHolder, MineListData mineListData, final int position) {
        listViewHolder.itemIcon.setBackgroundResource(mineListData.getItemIcon());
        listViewHolder.itemName.setText(mineListData.getItemName());
        listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, dataList.get(position));
                }
            }
        });
    }

    private void onBindListView(BottomViewHolder bottomViewHolder) {
        bottomViewHolder.btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginOut();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList != null && position < dataList.size()) {
            return dataList.get(position).getLayoutType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvAccount, tvMoney;
        Button btnRecharge, btnWithdrawal;

        public TopViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvAccount = (TextView) itemView.findViewById(R.id.tvAccount);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            btnRecharge = (Button) itemView.findViewById(R.id.btnRecharge);
            btnWithdrawal = (Button) itemView.findViewById(R.id.btnWithdrawal);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemName;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemIcon = (ImageView) itemView.findViewById(R.id.itemIcon);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {
        Button btnQuit;

        public BottomViewHolder(View itemView) {
            super(itemView);
            btnQuit = (Button) itemView.findViewById(R.id.btnQuit);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion, IRecycleViewBaseData data);
    }
}
