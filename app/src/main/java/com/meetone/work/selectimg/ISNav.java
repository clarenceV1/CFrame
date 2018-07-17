package com.meetone.work.selectimg;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.meetone.work.R;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;


/**
 * 总线
 * <p>
 * Created by yuyuhang on 2017/10/23.
 */
public class ISNav {

    private static ISNav instance;

    ILoadImage iLoadImage;

    public static ISNav getInstance() {
        if (instance == null) {
            synchronized (ISNav.class) {
                if (instance == null) {
                    instance = new ISNav();
                }
            }
        }
        return instance;
    }

    /**
     * 图片加载必须先初始化
     *
     * @param iLoadImage
     */
    public void init(@NonNull ILoadImage iLoadImage) {
        this.iLoadImage = iLoadImage;
    }

    public void displayImage(Activity context, String path, ImageView imageView) {
//        if (loader != null) {
//            loader.displayImage(context, path, imageView);
//        }
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(path)
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image)
                .build();
        imageParams.setImageView(imageView);
        iLoadImage.loadImage(context, imageParams);
    }

    public void toListActivity(Object source, ISListConfig config, int reqCode) {
        if (source instanceof Activity) {
            ISListActivity.startForResult((Activity) source, config, reqCode);
        } else if (source instanceof Fragment) {
            ISListActivity.startForResult((Fragment) source, config, reqCode);
        }
    }

    public void toCameraActivity(Object source, ISCameraConfig config, int reqCode) {
        if (source instanceof Activity) {
            ISCameraActivity.startForResult((Activity) source, config, reqCode);
        } else if (source instanceof Fragment) {
            ISCameraActivity.startForResult((Fragment) source, config, reqCode);
        }
    }

}
