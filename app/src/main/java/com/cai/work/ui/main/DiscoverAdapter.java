package com.cai.work.ui.main;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.widget.GridViewEx;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.cai.work.R;
import com.cai.work.bean.Discover;
import com.cai.work.bean.DiscoverMin;
import com.example.clarence.imageloaderlibrary.GlideRoundTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;

import java.util.List;

public class DiscoverAdapter extends BasePtrAdapter<Discover, BasePtrViewHold> {

    ILoadImage iLoadImage;
    int itemWeight;
    int itemWidth;
    int itemSmallWeight;
    int itemSmallWidth;
    Context context;
    DiscoverPresenter presenter;

    public DiscoverAdapter(Context context, ILoadImage iLoadImage, DiscoverPresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.context = context;
        this.presenter = presenter;
        itemWidth = DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 40);
        itemWeight = itemWidth * 130 / 345;

        itemSmallWidth = (DeviceUtils.getScreenWidth(context) - DimensUtils.dp2px(context, 50)) / 2;
        itemSmallWeight = itemSmallWidth * 110 / 163;
    }

    @Override
    public int getPtrItemViewType(int position) {
        if (datas.get(position).isHead()) {
            return 1;
        } else if (datas.get(position).getMin() != null) {
            return 2;
        } else
            return 3;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        BasePtrViewHold viewHolder;
        if (viewType == 1) {
            View itemView = inflateItemView(parent, R.layout.discover_head_item);
            viewHolder = new ViewHeadHolder(itemView, null);
        } else if (viewType == 2) {
            View itemView = inflateItemView(parent, R.layout.discover_gride_item);
            viewHolder = new ViewSmallHolder(itemView, null);
        } else {
            View itemView = inflateItemView(parent, R.layout.discover_big_item);
            viewHolder = new ViewBigHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.getStatistics().fx_banner();
                    String url = getData(position).getDis_url();
                    String title = getData(position).getDis_title();
                    jump(url, title);
                }

                @Override
                public void onItemLongClick(View v, int position) {

                }
            });
        }
        return viewHolder;
    }

    private void jump(String url, String title) {
        if (presenter.isLogin()) {
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
    protected void onPtrBindViewHolder(BasePtrViewHold holder, final Discover data, int position) {
        if (holder instanceof ViewSmallHolder) {
            ViewSmallHolder smallHolder = (ViewSmallHolder) holder;
            Point point = new Point(itemSmallWidth, itemSmallWeight);
            smallHolder.gridViewEx.setAdapter(new DiscoverGrideAdapter(context, point, iLoadImage, data.getMin()));
            smallHolder.gridViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<DiscoverMin> min = data.getMin();
                    presenter.getStatistics().fx_banner();
                    String url = min.get(position).getDis_url();
                    String title = min.get(position).getDis_title();
                    jump(url, title);
                }
            });
        } else if (holder instanceof ViewBigHolder) {
            ViewBigHolder bigHolder = (ViewBigHolder) holder;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bigHolder.imageAd.getLayoutParams();
            layoutParams.width = itemWidth;
            layoutParams.height = itemWeight;
            bigHolder.imageAd.setLayoutParams(layoutParams);

            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url(data.getDis_bgimage())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .transformation(new GlideRoundTransform(context, 12))
                    .build();
            imageParams.setImageView(bigHolder.imageAd);
            iLoadImage.loadImage(context, imageParams);
        }

    }

    class ViewHeadHolder extends BasePtrViewHold {

        public ViewHeadHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
        }
    }

    class ViewBigHolder extends BasePtrViewHold {

        ImageView imageAd;

        public ViewBigHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            imageAd = getView(R.id.imageAd);
        }
    }

    class ViewSmallHolder extends BasePtrViewHold {

        GridViewEx gridViewEx;

        public ViewSmallHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            gridViewEx = getView(R.id.gridViewEx);
        }
    }

}
