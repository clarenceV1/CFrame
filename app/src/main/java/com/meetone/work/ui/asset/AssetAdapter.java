package com.meetone.work.ui.asset;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.GlideRoundTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.meetone.work.R;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.meetone.work.bean.Asset;

public class AssetAdapter extends BasePtrAdapter<Asset, BasePtrViewHold> {

    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    Context context;
    AssetPresenter presenter;

    public AssetAdapter(Context context, ILoadImage iLoadImage, AssetPresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.context = context;
        this.presenter = presenter;
        itemWidth = DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 40);
        itemWeight = itemWidth * 122 / 335;
    }

    @Override
    public int getPtrItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        BasePtrViewHold viewHolder;
        if (viewType == 1) {
            View itemView = inflateItemView(parent, R.layout.asset_head_item);
            viewHolder = new HeadViewHolder(itemView, null);
        } else {
            View itemView = inflateItemView(parent, R.layout.asset_item);
            viewHolder = new ItemViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.getStatistics().wdzc_jl();
                    String tokenid = datas.get(position).getToken_id();
                    if (!TextUtils.isEmpty(tokenid)) {
                        try {
                            ARouter.getInstance().build("/MeetOne/RecordActivity")
                                    .withInt("tokenId", Integer.valueOf(tokenid)).navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onItemLongClick(View v, int position) {

                }
            });
        }
        return viewHolder;
    }

    @Override
    protected void onPtrBindViewHolder(BasePtrViewHold holder, final Asset data, int position) {
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            headViewHolder.tvTotalAsset.setText(data.getAssets());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) headViewHolder.imgBg.getLayoutParams();
            layoutParams.width = itemWidth;
            layoutParams.height = itemWeight;
            headViewHolder.imgBg.setLayoutParams(layoutParams);

            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url(data.getToken_icon())
                    .placeholder(R.drawable.default_image)
                    .transformation(new GlideRoundTransform(context, 12))
                    .build();
            imageParams.setImageView(headViewHolder.imgBg);
            iLoadImage.loadImage(context, imageParams);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url(data.getToken_icon())
                    .placeholder(R.drawable.default_circle_image)
                    .transformation(new GlideCircleTransform(context))
                    .build();
            imageParams.setImageView(itemViewHolder.imgCandy);
            iLoadImage.loadImage(context, imageParams);

            itemViewHolder.tvCandyName.setText(data.getToken_name());
            itemViewHolder.tvCandyNum.setText(data.getCandy_num());
            itemViewHolder.tvCandyPrice.setText(String.valueOf(data.getAssets()));
        }
    }

    class HeadViewHolder extends BasePtrViewHold {
        TextView tvTotalAsset;
        ImageView imgBg;

        public HeadViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvTotalAsset = getView(R.id.tvTotalAsset);
            imgBg = getView(R.id.imgBg);
        }
    }

    class ItemViewHolder extends BasePtrViewHold {
        ImageView imgCandy;
        TextView tvCandyName, tvCandyNum, tvCandyPrice;

        public ItemViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            imgCandy = getView(R.id.imgCandy);
            tvCandyName = getView(R.id.tvCandyName);
            tvCandyNum = getView(R.id.tvCandyNum);
            tvCandyPrice = getView(R.id.tvCandyPrice);
        }
    }

}
