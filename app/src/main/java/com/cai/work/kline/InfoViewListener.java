package com.cai.work.kline;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * Created by dell on 2017/9/28.
 */

public class InfoViewListener implements OnChartValueSelectedListener {

    /**
     * if otherChart not empty, highlight will disappear after 3 second
     */
    private Chart[] mOtherChart;

    public InfoViewListener( Chart... otherChart) {

        mOtherChart = otherChart;
    }

    public InfoViewListener() {
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
//        mInfoView.setVisibility(View.GONE);
        if (mOtherChart != null) {
            for (int i = 0; i < mOtherChart.length; i++) {
                mOtherChart[i].highlightValues(null);
            }
        }
    }
}
