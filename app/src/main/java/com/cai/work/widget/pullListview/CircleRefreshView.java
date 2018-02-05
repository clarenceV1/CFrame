package com.cai.work.widget.pullListview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cai.framework.utils.DeviceUtils;
import com.cai.work.R;
import com.cai.work.utils.CircleAnimation;


/**
 * 刷新效果View
 * Created by huangyuxiang on 2017/7/9.
 */

public class CircleRefreshView extends RelativeLayout implements IRefreshView {

    //下拉和加载中View
    private ImageView ivCircle;
    private ValueAnimator mHeightValueAnimator;
    private LinearLayout llContain;
    //刷新中固定的高度
    private int mRefreshingHeight;
    //刷新中圆圈开始旋转时的高度
    private int mCircleStartRotateHeight;
    //大于这个高度松手可以触发刷新的高度
    private int mReleaseToRefreshHeight;
    //下拉位移比例
    private float mPullRatio = 2f / 3;
    //是否正在刷新
    private boolean isRefreshing;
    //是否能够下拉刷新
    private boolean isRefreshEnable = true;
    //每一个旋转角度对应多少个下拉位移
    private float mPerDegreePullOffset;
    private float nowDelta;
    int duration = 400;
    private int heightAnimatorDuration = 800;
    int dp50;
    IRefreshHandle refreshHandle;

    public interface IRefreshHandle {

        void refreshAnimationStart(float nowDelta);

        void refreshAnimationEnd();
    }
    private float animationStopTime = 0;

    public CircleRefreshView(Context context) {
        super(context);
        init();
    }

    public CircleRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initValue();
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.circle_refresh_view, this);
        this.setBackgroundColor(getContext().getResources().getColor(R.color.white_an));
        ivCircle = (ImageView) findViewById(R.id.ivCircle);
        llContain = (LinearLayout) findViewById(R.id.llContain);
    }

    private void initValue() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mRefreshingHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
        mCircleStartRotateHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
        mReleaseToRefreshHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics);
        mPerDegreePullOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.3f, displayMetrics);
        dp50 = DeviceUtils.dip2px(getContext(), 50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int height = MeasureSpec.makeMeasureSpec(layoutParams.height >= 0 ? layoutParams.height : 0, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);
    }

    @Override
    public boolean isEyeVisible() {
        return getHeight() > 0 && getVisibility() == VISIBLE;
    }

    @Override
    public boolean isRefreshing() {
        return isRefreshing;
    }

    @Override
    public boolean isCanReleaseToRefresh() {
        return getHeight() >= mReleaseToRefreshHeight;
    }

    @Override
    public void animateToInitialState() {
        stopAllAnimation();

        int currentHeight = getHeight();
        if (currentHeight < mRefreshingHeight) {
            duration = (int) ((currentHeight * 1.0f / mRefreshingHeight) * duration);
        }
        mHeightValueAnimator = ValueAnimator.ofFloat(currentHeight, 0);
        mHeightValueAnimator.setDuration(duration);
        mHeightValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float height = (float) valueAnimator.getAnimatedValue();
                setViewHeight(height);
                handleLoadingView(height);
            }
        });
        mHeightValueAnimator.start();
    }

    @Override
    public void onPull(float offset) {
        if (!isRefreshEnable) {
            return;
        }
        ivCircle.setVisibility(VISIBLE);
        stopAllAnimation();
        float height = offset * mPullRatio;
        setViewHeight(height);
        handleLoadingView(height);
    }

    @Override
    public void setRefreshing() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        stopAllAnimation();
        ValueAnimator mHeightValueAnimator = ValueAnimator.ofInt(getHeight(), 0);
        mHeightValueAnimator.setDuration(heightAnimatorDuration);
        mHeightValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float height = (int) valueAnimator.getAnimatedValue();
                setViewHeight(height);
                if (height <= dp50 && refreshHandle != null) {
                    Log.d("onAnimationUpdate", "onAnimationUpdate:<<<" + valueAnimator.getCurrentPlayTime());
                    if (ivCircle != null) {
                        ivCircle.clearAnimation();
                        ivCircle.setVisibility(GONE);
                    }
                    nowDelta *= animationStopTime;
                    refreshHandle.refreshAnimationStart(nowDelta);
                } else {
                    Log.d("onAnimationUpdate", "onAnimationUpdate>>>>>" + valueAnimator.getCurrentPlayTime());
                }
            }
        });
        mHeightValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startRotateAnimation(ivCircle, nowDelta);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        mHeightValueAnimator.start();
    }

    public void startRotateAnimation(View animateView, float fromDegree) {
        RotateAnimation rotate = new RotateAnimation(fromDegree, fromDegree - 360.0F, 1, 0.5F, 1, 0.5F);
        rotate.setDuration(700L);
        rotate.setRepeatCount(-1);
        rotate.setInterpolator(new LinearInterpolator() {
            public float getInterpolation(float input) {
                Log.d("onAnimationUpdate", "onAnimationUpdate+++++" + input);
                animationStopTime = input;
                return input;
            }
        });
        animateView.startAnimation(rotate);
    }

    @Override
    public void setRefreshComplete(final String updateTips) {
        stopAllAnimation();
        if (refreshHandle != null) {
            refreshHandle.refreshAnimationEnd();
        }
        isRefreshing = false;
    }

    public void setRefreshHandle(IRefreshHandle refreshHandle) {
        this.refreshHandle = refreshHandle;
    }

    public void stopAllAnimation() {
        if (mHeightValueAnimator != null && mHeightValueAnimator.isRunning()) {
            mHeightValueAnimator.cancel();
        }
    }

    /**
     * 处理小球加载效果
     */
    private void handleLoadingView(float height) {
        if (height > mRefreshingHeight) {
            if (height > mCircleStartRotateHeight) {
                float progress = (height - mCircleStartRotateHeight) / mPerDegreePullOffset / 360;
                CircleAnimation.startCWAnimation(ivCircle, nowDelta, -progress * 360);
                nowDelta = progress * 360;
            }
        }
    }

    /**
     * 设置View指定高度
     */
    private void setViewHeight(float height) {
        View view = CircleRefreshView.this;
        if (view != null && view.getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams.height != (int) height) {
                layoutParams.height = (int) height;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llContain.getLayoutParams();
                if (height <= dp50) {
                    params.height = layoutParams.height;
                    llContain.requestLayout();
                }
                view.requestLayout();
            }
        }
    }
}
