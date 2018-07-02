package com.cai.work.kline;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.clarence.utillibrary.DimensUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.List;

/**
 * Created by dell on 2017/9/28.
 */

public class InfoViewListener implements OnChartValueSelectedListener {

    private List<HisData> mList;
    private double mLastClose;
    private int mWidth;
    /**
     * if otherChart not empty, highlight will disappear after 3 second
     */
    private Chart[] mOtherChart;

    public InfoViewListener(Context context, double lastClose, List<HisData> list,  Chart... otherChart) {
        mWidth = DimensUtils.getWidthHeight(context)[0];
        mLastClose = lastClose;
        mList = list;
        mOtherChart = otherChart;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (mOtherChart != null) {
            for (Chart aMOtherChart : mOtherChart) {
                aMOtherChart.highlightValues(new Highlight[]{new Highlight(h.getX(), Float.NaN, h.getDataSetIndex())});
            }
        }
    }

    @Override
    public void onNothingSelected() {
        if (mOtherChart != null) {
            for (int i = 0; i < mOtherChart.length; i++) {
                mOtherChart[i].highlightValues(null);
            }
        }
    }
}
