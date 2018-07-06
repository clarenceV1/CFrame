package com.cai.work.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.cai.work.R;
import com.cai.work.bean.CandyList;
import com.cai.work.bean.Discover;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;

public class DiscoverAdapter extends BasePtrAdapter<Discover, DiscoverAdapter.ViewHolder> {

    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    Context context;

    public DiscoverAdapter(Context context, ILoadImage iLoadImage) {
        this.iLoadImage = iLoadImage;
        this.context = context;
        itemWidth = DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 40);
        itemWeight = (int) (itemWidth * 130 / 345.0f);
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.discover_item);
        ViewHolder viewHolder = new ViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, final Discover data, int position) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageAd.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWeight;
        holder.imageAd.setLayoutParams(layoutParams);

        holder.imageAd.setVisibility(View.VISIBLE);
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(data.getDis_bgimage())
                .build();

        imageParams.setImageView(holder.imageAd);
        iLoadImage.loadImage(context, imageParams);
    }

    class ViewHolder extends BasePtrViewHold {

        ImageView imageAd;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            imageAd = getView(R.id.imageAd);
        }
    }

}
