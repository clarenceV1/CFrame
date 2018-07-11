package com.cai.work.ui.main;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.DiscoverMin;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

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
                .build();
        imageParams.setImageView(image1);
        iLoadImage.loadImage(context, imageParams);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.discover_small_item;
    }
}
