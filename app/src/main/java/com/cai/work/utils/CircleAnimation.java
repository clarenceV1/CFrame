package com.cai.work.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.cai.work.R;

/**
 * Created by clarence on 2018/2/1.
 */

public class CircleAnimation {
    public CircleAnimation() {
    }

    public static void startCWAnimation(View animateView, float fromDegree, float toDegree) {
        RotateAnimation cwAnimation = new RotateAnimation(fromDegree, toDegree, 1, 0.5F, 1, 0.5F);
        cwAnimation.setDuration(60L);
        cwAnimation.setFillAfter(true);
        animateView.startAnimation(cwAnimation);
    }

    public static void startCWAnimation(View animateView, float fromDegree, float toDegree, int duration, Animation.AnimationListener animationListener) {
        RotateAnimation cwAnimation = new RotateAnimation(fromDegree, toDegree, 1, 0.5F, 1, 0.5F);
        cwAnimation.setDuration((long) duration);
        cwAnimation.setFillAfter(true);
        animateView.startAnimation(cwAnimation);
        cwAnimation.setAnimationListener(animationListener);
    }

    public static void startRotateAnimation(View animateView, float fromDegree) {
        RotateAnimation rotate = new RotateAnimation(fromDegree, fromDegree - 360.0F, 1, 0.5F, 1, 0.5F);
        rotate.setDuration(700L);
        rotate.setRepeatCount(-1);
        rotate.setInterpolator(new LinearInterpolator());
        animateView.startAnimation(rotate);
    }

    public static void startRebackAnimation(Context context, View animateView, Animation.AnimationListener animListener) {
        Animation translate = AnimationUtils.loadAnimation(context, R.anim.top_out);
        translate.setAnimationListener(animListener);
        animateView.startAnimation(translate);
    }

    @TargetApi(8)
    public static void stopRotateAnmiation(View animatedView) {
        if (Build.VERSION.SDK_INT > 8) {
            animatedView.getAnimation().cancel();
        }

    }

    public static void scaleAnimation(final ImageView imageView) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0F, 1.2F, 1.0F, 1.2F, 1, 0.5F, 1, 0.5F);
        scaleAnimation.setDuration(100L);
        scaleAnimation.setFillAfter(true);
        imageView.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.2F, 1.0F, 1.2F, 1.0F, 1, 0.5F, 1, 0.5F);
                scaleAnimation.setDuration(100L);
                scaleAnimation.setFillAfter(true);
                imageView.startAnimation(scaleAnimation);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
