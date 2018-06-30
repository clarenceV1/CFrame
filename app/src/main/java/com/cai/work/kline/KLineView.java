package com.cai.work.kline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.cai.work.R;
import com.cai.work.utils.DataUtils;
import com.example.clarence.utillibrary.DisplayUtils;
import com.example.clarence.utillibrary.DoubleUtil;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * kline
 * Created by guoziwei on 2017/10/26.
 */
public class KLineView extends BaseView implements CoupleChartGestureListener.OnAxisChangeListener {


    public static final int NORMAL_LINE = 0;
    /**
     * average line
     */
    public static final int AVE_LINE = 1;
    /**
     * hide line
     */
    public static final int INVISIABLE_LINE = 6;


    public static final int MA5 = 5;
    public static final int MA10 = 10;
    public static final int MA20 = 20;
    public static final int MA30 = 30;

    public static final int DIF = 34;
    public static final int DEA = 35;


    protected CustomCombinedChart mChartPrice;
    protected CustomCombinedChart mChartVolume;

    protected Context mContext;

    /**
     * last price
     */
    private double mLastPrice;

    /**
     * yesterday close price
     */
    private double mLastClose;

    /**
     * the digits of the symbol
     */
    private int mDigits = 2;
    private CoupleChartGestureListener mCoupleChartGestureListener;

    public KLineView(Context context) {
        this(context, null);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_kline, this);
        mChartPrice = (CustomCombinedChart) findViewById(R.id.price_chart);
        mChartVolume = (CustomCombinedChart) findViewById(R.id.vol_chart);

        mChartPrice.setNoDataText(context.getString(R.string.loading));
        initChartPrice();
        initBottomChart(mChartVolume);
        setOffset();
        initChartListener();
    }

    public void showVolume() {
        mChartVolume.setVisibility(VISIBLE);
    }


    protected void initChartPrice() {
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(false);
        mChartPrice.setBorderWidth(1);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleYEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        mChartPrice.setDragDecelerationEnabled(false);
        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mData);
        mvx.setChartView(mChartPrice);
        mChartPrice.setXMarker(mvx);
        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(false);

        XAxis xAxisPrice = mChartPrice.getXAxis();
        xAxisPrice.setDrawLabels(false);
        xAxisPrice.setDrawAxisLine(false);
        xAxisPrice.setDrawGridLines(false);
        xAxisPrice.setAxisMinimum(-0.5f);


        YAxis axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setLabelCount(5, true);
        axisLeftPrice.setDrawLabels(true);
        axisLeftPrice.setDrawGridLines(false);

        axisLeftPrice.setDrawAxisLine(false);
        axisLeftPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftPrice.setTextColor(mAxisColor);
        axisLeftPrice.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DoubleUtil.getStringByDigits(value, mDigits);
            }
        });

        int[] colorArray = {mDecreasingColor, mDecreasingColor, mAxisColor, mIncreasingColor, mIncreasingColor};
        Transformer leftYTransformer = mChartPrice.getRendererLeftYAxis().getTransformer();
        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisLeft(), leftYTransformer);
        leftColorContentYAxisRenderer.setLabelColor(colorArray);
        leftColorContentYAxisRenderer.setLabelInContent(true);
        leftColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        mChartPrice.setRendererLeftYAxis(leftColorContentYAxisRenderer);


        YAxis axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, true);
        axisRightPrice.setDrawLabels(true);

        axisRightPrice.setDrawGridLines(false);
        axisRightPrice.setDrawAxisLine(false);
        axisRightPrice.setTextColor(mAxisColor);
        axisRightPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        axisRightPrice.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                double rate = (value - mLastClose) / mLastClose * 100;
                if (Double.isNaN(rate) || Double.isInfinite(rate)) {
                    return "";
                }
                String s = String.format(Locale.getDefault(), "%.2f%%",
                        rate);
                if (TextUtils.equals("-0.00%", s)) {
                    return "0.00%";
                }
                return s;
            }
        });

//        设置标签Y渲染器
        Transformer rightYTransformer = mChartPrice.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer rightColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisRight(), rightYTransformer);
        rightColorContentYAxisRenderer.setLabelInContent(true);
        rightColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        rightColorContentYAxisRenderer.setLabelColor(colorArray);
        mChartPrice.setRendererRightYAxis(rightColorContentYAxisRenderer);

    }


    private void initChartListener() {
        mCoupleChartGestureListener = new CoupleChartGestureListener(this, mChartPrice, mChartVolume);
        mChartPrice.setOnChartGestureListener(mCoupleChartGestureListener);
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mChartVolume));
        mChartPrice.setOnTouchListener(new ChartInfoViewHandler(mChartPrice));
    }

    public void initData(List<HisData> hisDatas) {
        mData.clear();
        mData.addAll(DataUtils.calculateHisData(hisDatas));
        mChartPrice.setRealCount(mData.size());

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma5Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma10Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma20Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma30Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            HisData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }

        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MA5, ma5Entries),
                setLine(MA10, ma10Entries),
                setLine(MA20, ma20Entries),
                setLine(MA30, ma30Entries));
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        mChartPrice.setData(combinedData);

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartPrice.notifyDataSetChanged();
        moveToLast(mChartPrice);
        initChartVolumeData();

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 0.5f);

        mChartPrice.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
        mChartVolume.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);

        HisData hisData = getLastData();
        setDescription(mChartVolume, "成交量 " + hisData.getVol());
    }


    private BarDataSet setBar(ArrayList<BarEntry> barEntries, int type) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "vol");
        barDataSet.setHighLightAlpha(120);
        barDataSet.setHighLightColor(getResources().getColor(R.color.highlight_color));
        barDataSet.setDrawValues(false);
        barDataSet.setVisible(type != INVISIABLE_LINE);
        barDataSet.setHighlightEnabled(type != INVISIABLE_LINE);
        barDataSet.setColors(getResources().getColor(R.color.increasing_color), getResources().getColor(R.color.decreasing_color));
        return barDataSet;
    }


    @android.support.annotation.NonNull
    private LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        if (type == NORMAL_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
            lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.normal_line_color));
        } else  if (type == DIF) {
            lineDataSetMa.setColor(getResources().getColor(R.color.dif));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == DEA) {
            lineDataSetMa.setColor(getResources().getColor(R.color.dea));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == AVE_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else {
            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setCircleRadius(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);

        return lineDataSetMa;
    }

    @android.support.annotation.NonNull
    public CandleDataSet setKLine(int type, ArrayList<CandleEntry> lineEntries) {
        CandleDataSet set = new CandleDataSet(lineEntries, "KLine" + type);
        set.setDrawIcons(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowColor(Color.DKGRAY);
        set.setShadowWidth(0.75f);
        set.setDecreasingColor(mDecreasingColor);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setShadowColorSameAsCandle(true);
        set.setIncreasingColor(mIncreasingColor);
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        set.setDrawValues(true);
        set.setValueTextSize(10);
        set.setHighlightEnabled(true);
        if (type != NORMAL_LINE) {
            set.setVisible(false);
        }
        return set;
    }

    private void initChartVolumeData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            HisData t = mData.get(i);
            barEntries.add(new BarEntry(i, (float) t.getVol(), t));
        }
        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        mChartVolume.setData(combinedData);

        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartVolume.notifyDataSetChanged();
        moveToLast(mChartVolume);

    }

    /**
     * according to the price to refresh the last data of the chart
     */
    public void refreshData(float price) {
        if (price <= 0 || price == mLastPrice) {
            return;
        }
        mLastPrice = price;
        CombinedData data = mChartPrice.getData();
        if (data == null) return;
        LineData lineData = data.getLineData();
        if (lineData != null) {
            ILineDataSet set = lineData.getDataSetByIndex(0);
            if (set.removeLast()) {
                set.addEntry(new Entry(set.getEntryCount(), price));
            }
        }
        CandleData candleData = data.getCandleData();
        if (candleData != null) {
            ICandleDataSet set = candleData.getDataSetByIndex(0);
            if (set.removeLast()) {
                HisData hisData = mData.get(mData.size() - 1);
                hisData.setClose(price);
                hisData.setHigh(Math.max(hisData.getHigh(), price));
                hisData.setLow(Math.min(hisData.getLow(), price));
                set.addEntry(new CandleEntry(set.getEntryCount(), (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), price));

            }
        }
        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
    }

    public void addDatas(List<HisData> hisDatas) {
        for (HisData hisData : hisDatas) {
            addData(hisData);
        }
    }

    public void addData(HisData hisData) {
        hisData = DataUtils.calculateHisData(hisData, mData);
        CombinedData combinedData = mChartPrice.getData();
        LineData priceData = combinedData.getLineData();
        ILineDataSet padding = priceData.getDataSetByIndex(0);
        ILineDataSet ma5Set = priceData.getDataSetByIndex(1);
        ILineDataSet ma10Set = priceData.getDataSetByIndex(2);
        ILineDataSet ma20Set = priceData.getDataSetByIndex(3);
        ILineDataSet ma30Set = priceData.getDataSetByIndex(4);
        CandleData kData = combinedData.getCandleData();
        ICandleDataSet klineSet = kData.getDataSetByIndex(0);
        IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);

        if (mData.contains(hisData)) {
            int index = mData.indexOf(hisData);
            klineSet.removeEntry(index);
            padding.removeFirst();
            // ma比较特殊，entry数量和k线的不一致，移除最后一个
            ma5Set.removeLast();
            ma10Set.removeLast();
            ma20Set.removeLast();
            ma30Set.removeLast();
            volSet.removeEntry(index);
            mData.remove(index);
        }
        mData.add(hisData);
        mChartPrice.setRealCount(mData.size());
        int klineCount = klineSet.getEntryCount();
        klineSet.addEntry(new CandleEntry(klineCount, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
        volSet.addEntry(new BarEntry(volSet.getEntryCount(), hisData.getVol(), hisData));


        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 1.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 1.5f);


        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        mChartVolume.notifyDataSetChanged();
        mChartVolume.invalidate();

        setChartDescription(hisData);

    }

    public void addDatasFirst(List<HisData> hisDatas) {
        CombinedData combinedData = mChartPrice.getData();
        LineData priceData = combinedData.getLineData();
        ILineDataSet padding = priceData.getDataSetByIndex(0);
        ILineDataSet ma5Set = priceData.getDataSetByIndex(1);
        ILineDataSet ma10Set = priceData.getDataSetByIndex(2);
        ILineDataSet ma20Set = priceData.getDataSetByIndex(3);
        ILineDataSet ma30Set = priceData.getDataSetByIndex(4);
        CandleData kData = combinedData.getCandleData();
        ICandleDataSet klineSet = kData.getDataSetByIndex(0);
        IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);

        mData.addAll(0, hisDatas);
        // 这里需要重新绘制图表，把之前的图表清理掉
        klineSet.clear();
        padding.clear();
        ma5Set.clear();
        ma10Set.clear();
        ma20Set.clear();
        ma30Set.clear();
        volSet.clear();

        // 重新计算各个指标
        DataUtils.calculateHisData(mData);
        mChartPrice.setRealCount(mData.size());
        for (int i = 0; i < mData.size(); i++) {
            HisData hisData = mData.get(i);
            klineSet.addEntry(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
            volSet.addEntry(new BarEntry(i, hisData.getVol(), hisData));
        }


        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.moveViewToX(hisDatas.size() - 0.5f);
        mChartVolume.moveViewToX(hisDatas.size() - 0.5f);


        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        mChartVolume.notifyDataSetChanged();
        mChartVolume.invalidate();


        HisData hisData = mData.get(0);
        setChartDescription(hisData);

    }

    private void setChartDescription(HisData hisData) {
//        setDescription(mChartPrice, String.format(Locale.getDefault(), "MA5:%.2f  MA10:%.2f  MA20:%.2f  MA30:%.2f",
//                hisData.getMa5(), hisData.getMa10(), hisData.getMa20(), hisData.getMa30()));
        setDescription(mChartVolume, "成交量 " + hisData.getVol());
    }


    /**
     * align two chart
     */
    private void setOffset() {
        int chartHeight = getResources().getDimensionPixelSize(R.dimen.bottom_chart_height);
        mChartPrice.setViewPortOffsets(0, 0, 0, chartHeight);
        int bottom = DisplayUtils.dip2px(mContext, 20);
        mChartVolume.setViewPortOffsets(0, 0, 0, bottom);
    }


    /**
     * add limit line to chart
     */
    public void setLimitLine(double lastClose) {
        LimitLine limitLine = new LimitLine((float) lastClose);
        limitLine.enableDashedLine(5, 10, 0);
        limitLine.setLineColor(getResources().getColor(R.color.limit_color));
        mChartPrice.getAxisLeft().addLimitLine(limitLine);
    }

    public void setLimitLine() {
        setLimitLine(mLastClose);
    }


    @Override
    public void onAxisChange(BarLineChartBase chart) {
        float lowestVisibleX = chart.getLowestVisibleX();
        if (lowestVisibleX <= chart.getXAxis().getAxisMinimum()) return;
        int maxX = (int) chart.getHighestVisibleX();
        int x = Math.min(maxX, mData.size() - 1);
        HisData hisData = mData.get(x < 0 ? 0 : x);
        setChartDescription(hisData);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l) {
        if (mCoupleChartGestureListener != null) {
            mCoupleChartGestureListener.setOnLoadMoreListener(l);
        }
    }

    public void loadMoreComplete() {
        if (mCoupleChartGestureListener != null) {
            mCoupleChartGestureListener.loadMoreComplete();
        }
    }
}