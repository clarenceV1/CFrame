package com.cai.work.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cai.work.R;
import com.cai.work.bean.ScrollData;
import com.cai.work.ui.adapter.ScrollAdapter;
import com.cai.work.utils.CircleAnimation;
import com.cai.work.widget.pullListview.CircleRefreshView;
import com.cai.work.widget.pullListview.PullToMiddleRefreshListView;
import com.cai.work.widget.pullListview.RefreshView;

import java.util.ArrayList;

/**
 * Created by clarence on 2018/1/22.
 */

public class PullListViewActivity extends Activity {

    private PullToMiddleRefreshListView pullListview;
    private ScrollAdapter adapter;
    private ArrayList<ScrollData> data;
    RefreshView refreshView2;
    CircleRefreshView refreshView1;
    View stickHead;
    boolean isLoading, isMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_listview);

        init();
    }

    public void showHead(View view) {
        refreshView1.setVisibility(View.VISIBLE);
        stickHead.findViewById(R.id.tvTitle).setVisibility(View.VISIBLE);
        stickHead.requestLayout();
        pullListview.setRefreshView(refreshView1);
    }

    public void hideHead(View view) {
        refreshView1.setVisibility(View.GONE);
        stickHead.findViewById(R.id.tvTitle).setVisibility(View.GONE);
        stickHead.requestLayout();
        pullListview.setRefreshView(refreshView2);
    }

    public void clickHead1(View view) {
        pullListview.setRefreshView(refreshView1);
        Toast.makeText(PullListViewActivity.this, "设置成功头1", Toast.LENGTH_SHORT).show();
    }

    public void clickHead2(View view) {
        pullListview.setRefreshView(refreshView2);
        Toast.makeText(PullListViewActivity.this, "设置成功头2", Toast.LENGTH_SHORT).show();
    }

    ImageView ivOutCircle;

    private void init() {
        pullListview = (PullToMiddleRefreshListView) findViewById(R.id.pullListview);
        data = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            ScrollData scrollData = new ScrollData();
            scrollData.setName("这是第" + i + "个数据");
            data.add(scrollData);
        }
//        floatHead = LayoutInflater.from(this).inflate(R.layout.pull_listview_head1, null);
//        RefreshHeaderContentView headContain = (RefreshHeaderContentView) floatHead.findViewById(R.id.header_content_view);
//        pullListview.setHeaderContentView(headContain);
//        pullListview.addHeaderView(floatHead);

//        refreshView1 = new CircleRefreshView(this);
//        pullListview.setRefreshView(refreshView1);
//        pullListview.addHeaderView(refreshView1);

        stickHead = LayoutInflater.from(this).inflate(R.layout.pull_listview_stick_head, null);
        ivOutCircle = (ImageView) stickHead.findViewById(R.id.ivOutCircle);
        refreshView1 = (CircleRefreshView) stickHead.findViewById(R.id.cfView);
        refreshView1.setRefreshHandle(new CircleRefreshView.IRefreshHandle() {
            @Override
            public void refreshAnimationStart(float nowDelta) {
                ivOutCircle.setVisibility(View.VISIBLE);
                CircleAnimation.startRotateAnimation(ivOutCircle, nowDelta);
            }

            @Override
            public void refreshAnimationEnd() {
                if (ivOutCircle != null) {
//                    ivOutCircle.clearAnimation();
                    CircleAnimation.startRebackAnimation(PullListViewActivity.this, ivOutCircle, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ivOutCircle.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
        });
        pullListview.setRefreshView(refreshView1);
        pullListview.addHeaderView(stickHead);

        refreshView2 = new RefreshView(this);
        refreshView2.setTags("头部2");
        pullListview.addHeaderView(refreshView2);

        View footer = LayoutInflater.from(this).inflate(R.layout.pull_listview_footer, null);
        pullListview.addFooterView(footer);

        adapter = new ScrollAdapter(this, data);
        pullListview.setAdapter(adapter);
        pullListview.setOnRefreshListener(new PullToMiddleRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullListview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullListview.setRefreshComplete("刷新完成");
                    }
                }, 3000);
            }
        });
        pullListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    //判断是否滚动到底部
                    if (!isLoading && view.getLastVisiblePosition() == view.getCount() - 1) {
                        isLoading = true;
                        getMoreData();
                        return;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public void getMoreData() {
        pullListview.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 3; i++) {
                    ScrollData scrollData = new ScrollData();
                    scrollData.setName("我是底部加载的:" + i);
                    data.add(scrollData);
                }
                isLoading = false;
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
