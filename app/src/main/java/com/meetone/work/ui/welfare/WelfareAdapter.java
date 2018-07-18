package com.meetone.work.ui.welfare;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.meetone.work.R;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.meetone.work.bean.Welfare;

public class WelfareAdapter extends BasePtrAdapter<Welfare, WelfareAdapter.ViewHolder> {

    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    Context context;
    WelfarePresenter presenter;
    String title;

    public WelfareAdapter(Context context, String title, ILoadImage iLoadImage, WelfarePresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.context = context;
        this.title = title;
        this.presenter = presenter;
        itemWidth = DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 40);
        itemWeight = (int) (itemWidth * 130 / 345.0f);
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.welfare_item);
        ViewHolder viewHolder = new ViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (presenter.isLogin()) {
                    presenter.getStatistics().gdfl_banner();
                    String url = getData(position).getUrl();
                    String title = getData(position).getTitle();
                    if (!TextUtils.isEmpty(url)) {
                        ARouter.getInstance().build("/MeetOne/WebActivity")
                                .withCharSequence("url", url)
                                .withCharSequence("title", title)
                                .navigation();
                    }
                } else {
                    ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
                }
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, final Welfare data, int position) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageAd.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWeight;
        holder.imageAd.setLayoutParams(layoutParams);

        holder.imageAd.setVisibility(View.VISIBLE);
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(data.getImage())
                .build();

        imageParams.setImageView(holder.imageAd);
        iLoadImage.loadImage(context, imageParams);

        if (position == 0) {
            if (!TextUtils.isEmpty(title)) {
                holder.tvTitle.setText(title);
            }
            holder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends BasePtrViewHold {

        ImageView imageAd;
        TextView tvTitle;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            imageAd = getView(R.id.imageAd);
            tvTitle = getView(R.id.tvTitle);
        }
    }

}
