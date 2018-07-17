package com.meetone.work.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.meetone.work.R;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.meetone.work.bean.CandyList;

public class CandyAdapter extends BasePtrAdapter<CandyList, CandyAdapter.ViewHolder> {

    int[] bgIds = new int[]{R.drawable.candy_img_background1, R.drawable.candy_img_background2, R.drawable.candy_img_background3};
    int[] textColors = new int[]{Color.parseColor("#EB58FF"), Color.parseColor("#5470FF"), Color.parseColor("#4C93FF")};
    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    CandyPresenter presenter;
    Context context;

    public CandyAdapter(Context context, ILoadImage iLoadImage, CandyPresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.presenter = presenter;
        this.context = context;
        itemWidth = DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 40);
        itemWeight = (int) (itemWidth * 130 / 345.0f);
    }

    public void changData(int token_id) {
        for (CandyList data : datas) {
            if (data.getToken_id() == token_id) {
                float giveTotal = data.getGive_total();
                data.setWallet_total(giveTotal + data.getWallet_total());
                data.setGive_total(0);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.candy_item);
        ViewHolder viewHolder = new ViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CandyList candyList = datas.get(position);
                if (candyList.getType() == 2) {
                    presenter.getStatistics().home_fl();
                    ARouter.getInstance().build("/MeetOne/WebActivity").withCharSequence("url", candyList.getUri()).navigation();
                } else {
                    presenter.getStatistics().home_tgxq();
                    ARouter.getInstance().build("/MeetOne/CandyDetailActivity").withInt("tokenId", candyList.getToken_id()).navigation();
                }
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, final CandyList data, int position) {
        handleHead(holder, data);
        if (data.getType() == 2) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageAd.getLayoutParams();
            layoutParams.width = itemWidth;
            layoutParams.height = itemWeight;
            holder.imageAd.setLayoutParams(layoutParams);

            holder.imageAd.setVisibility(View.VISIBLE);
            holder.rlItemRoot.setVisibility(View.GONE);
            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url(data.getImage())
                    .build();

            imageParams.setImageView(holder.imageAd);
            iLoadImage.loadImage(context, imageParams);
            return;
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.rlItemRoot.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWeight;
        holder.rlItemRoot.setLayoutParams(layoutParams);

        holder.imageAd.setVisibility(View.GONE);
        holder.rlItemRoot.setVisibility(View.VISIBLE);

        holder.rlItemRoot.setBackgroundResource(bgIds[position % 3]);
        holder.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    presenter.clickReceiveCoinBtn(data);
                }
            }
        });
        holder.tvReward.setText(context.getString(R.string.candy_list_other_content,
                StringUtils.formatNum(data.getCandy_total()),
                StringUtils.formatNum(data.getCandy_surplus())));
        holder.tvDescrible.setText(data.getToken_remark());
        holder.tvTitle.setText(data.getToken_symbol());
        if (data.getGive_total() > 0) {
            holder.tvGive.setVisibility(View.VISIBLE);
            holder.tvGive.setText("+" + StringUtils.formatNum(data.getGive_total()));
            holder.tvCommit.setText(R.string.candy_receive_now);
            holder.tvCommit.setBackgroundResource(R.drawable.candy_btn_getnow);
            holder.tvCommit.setTextColor(Color.WHITE);
            holder.tvTotal.setVisibility(View.GONE);
        } else {
            holder.tvGive.setVisibility(View.GONE);
            holder.tvTotal.setVisibility(View.VISIBLE);
            holder.tvTotal.setText(context.getString(R.string.wallet_total, StringUtils.formatNum(data.getWallet_total())));
            holder.tvCommit.setText(R.string.candy_receive_more);
            holder.tvCommit.setTextColor(textColors[position % 3]);
            holder.tvCommit.setBackgroundResource(R.drawable.candy_btn_getmore);
        }
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(data.getToken_icon())
                .transformation(new GlideCircleTransform(context))
                .build();
        imageParams.setImageView(holder.imageView);
        iLoadImage.loadImage(context, imageParams);
    }

    private void handleHead(ViewHolder holder, final CandyList data) {
        if (TextUtils.isEmpty(data.getBarOne())) {
            holder.itemHead.setVisibility(View.GONE);
        } else {
            holder.itemHead.setVisibility(View.VISIBLE);
            holder.tvBigTitle.setText(data.getBarOne());
            if (TextUtils.isEmpty(data.getBarTwo())) {
                holder.tvSmallTitle.setVisibility(View.GONE);
            } else {
                holder.tvSmallTitle.setVisibility(View.VISIBLE);
                holder.tvSmallTitle.setText(data.getBarTwo());
                holder.tvSmallTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.getType() == 2) {
                            presenter.getStatistics().home_gdfl();
                            ARouter.getInstance().build("/MeetOne/WelfareActivity")
                                    .withCharSequence("title", data.getBarTwo()).navigation();
                        } else {
                            presenter.getStatistics().home_wdzc();
                            if(presenter.isLogin()){
                                ARouter.getInstance().build("/MeetOne/AssetActivity").navigation();
                            }else{
                                ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
                            }

                        }
                    }
                });
            }
        }
    }

    class ViewHolder extends BasePtrViewHold {

        ImageView imageAd, imageView;
        View rlItemRoot;
        TextView tvCommit, tvReward, tvDescrible, tvTitle, tvGive, tvTotal;

        //head
        RelativeLayout itemHead;
        TextView tvBigTitle, tvSmallTitle;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            imageAd = getView(R.id.imageAd);
            imageView = getView(R.id.imageView);
            rlItemRoot = getView(R.id.rlItemRoot);
            tvCommit = getView(R.id.tvCommit);
            tvReward = getView(R.id.tvReward);
            tvDescrible = getView(R.id.tvDescrible);
            tvTitle = getView(R.id.tvTitle);
            tvGive = getView(R.id.tvGive);
            tvTotal = getView(R.id.tvTotal);

            itemHead = getView(R.id.itemHead);
            tvSmallTitle = getView(R.id.tvSmallTitle);
            tvBigTitle = getView(R.id.tvBigTitle);
        }
    }

}
