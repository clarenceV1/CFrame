package com.cai.work.ui.asset;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.cai.work.R;
import com.cai.work.bean.Asset;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.GlideRoundTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;

public class AssetAdapter extends BasePtrAdapter<Asset, BasePtrViewHold> {

    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    Context context;

    public AssetAdapter(Context context, ILoadImage iLoadImage) {
        this.iLoadImage = iLoadImage;
        this.context = context;
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
            itemViewHolder.tvCandyPrice.setText(String.valueOf(data.getPrice()));
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
