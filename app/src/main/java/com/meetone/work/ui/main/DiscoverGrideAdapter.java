package com.meetone.work.ui.main;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.imageload.GlideRoundTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.meetone.work.R;
import com.meetone.work.bean.DiscoverMin;

import java.util.List;

public class DiscoverGrideAdapter extends GodBaseAdapter<DiscoverMin> {

    Point point;
    ILoadImage iLoadImage;

    public DiscoverGrideAdapter(Context context, Point point, ILoadImage iLoadImage, List<DiscoverMin> data) {
        super(context, data);
        this.point = point;
        this.iLoadImage = iLoadImage;
    }

    @Override
    public void initItemView(View convertView, DiscoverMin itemData, int position) {
        ImageView image1 = convertView.findViewById(R.id.image1);
        ViewGroup.LayoutParams layoutParams = image1.getLayoutParams();
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        image1.setLayoutParams(layoutParams);

        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(itemData.getDis_bgimage())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .transformation(new GlideRoundTransform(context,12))
                .build();
        imageParams.setImageView(image1);
        iLoadImage.loadImage(context, imageParams);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.discover_small_item;
    }
}
