package com.cai.work.kline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.cai.work.R;
import com.cai.work.utils.DataUtils;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.DoubleUtil;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * kline
 * Created by guoziwei on 2017/10/26.
 */
public class TimeLineView extends BaseView implements CoupleChartGestureListener.OnAxisChangeListener {


    public static final int NORMAL_LINE = 0;

    public static final int NORMAL_LINE_5DAY = 5;
    /**
     * average line
     */
    public static final int AVE_LINE = 1;
    /**
     * hide line
     */
    public static final int INVISIABLE_LINE = 6;

    protected CustomCombinedChart mChartPrice;

    protected Context mContext;

    /**
     * last price
     */
    private double mLastPrice;

    /**
     * the digits of the symbol
     */
    private int mDigits = 2;

    public TimeLineView(Context context) {
        super(context);
        initView(context);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_timeline, this);
        mChartPrice = (CustomCombinedChart) findViewById(R.id.price_chart);

        mChartPrice.setNoDataText(context.getString(R.string.loading));
        initChartPrice();
//        initBottomChart(mChartVolume);
        setOffset();
        initChartListener();
    }


    protected void initChartPrice() {
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(true);
        mChartPrice.setBorderWidth(1);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleYEnabled(true);
        mChartPrice.getDescription().setEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        mChartPrice.setDragDecelerationEnabled(false);
        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mData);
        mvx.setChartView(mChartPrice);
        mChartPrice.setXMarker(mvx);
        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(false);

        XAxis xAxisPrice = mChartPrice.getXAxis();
        xAxisPrice.enableGridDashedLine(10, 10, 0);
        xAxisPrice.setDrawLabels(true);
        xAxisPrice.setLabelCount(6, true);
        xAxisPrice.setDrawAxisLine(false);
        xAxisPrice.setDrawGridLines(false);
        xAxisPrice.setTextColor(getResources().getColor(R.color.ys_255_255_255));
        xAxisPrice.setAxisMinimum(-0.5f);
        xAxisPrice.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxisPrice.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mData.isEmpty()) {
                    return "";
                }
                if (value < 0) {
                    value = 0;
                }
                if (value < mData.size()) {
                    return DateUtils.formatDate(mData.get((int) value).getDate(), mDateFormat);
                }
                return "";
            }
        });


        YAxis axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setLabelCount(2, true);
        axisLeftPrice.enableGridDashedLine(10, 10, 0);
        axisLeftPrice.setDrawLabels(true);
        axisLeftPrice.setDrawGridLines(false);
        axisLeftPrice.setDrawAxisLine(false);
        axisLeftPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftPrice.setTextColor(getResources().getColor(R.color.ys_255_255_255));
        axisLeftPrice.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DoubleUtil.getStringByDigits(value, mDigits);
            }
        });

        int[] colorArray = {mDecreasingColor, mDecreasingColor, mAxisColor, mIncreasingColor, mIncreasingColor};
        Transformer leftYTransformer = mChartPrice.getRendererLeftYAxis().getTransformer();
        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisLeft(), leftYTransformer);

        int white = ContextCompat.getColor(getContext(), R.color.ys_255_255_255);
        int[] labelColor = {white, white, white, white, white};
        leftColorContentYAxisRenderer.setLabelColor(labelColor);
        leftColorContentYAxisRenderer.setLabelInContent(true);
        leftColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        mChartPrice.setRendererLeftYAxis(leftColorContentYAxisRenderer);


        YAxis axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, true);
        axisRightPrice.setDrawLabels(false);

        axisRightPrice.setDrawGridLines(false);
        axisRightPrice.setDrawAxisLine(false);
        axisRightPrice.setTextColor(getResources().getColor(R.color.ys_255_255_255));
        axisRightPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

//        axisRightPrice.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                double rate = (value - mLastClose) / mLastClose * 100;
//                if (Double.isNaN(rate) || Double.isInfinite(rate)) {
//                    return "";
//                }
//                String s = String.format(Locale.getDefault(), "%.2f%%",
//                        rate);
//                if (TextUtils.equals("-0.00%", s)) {
//                    return "0.00%";
//                }
//                return s;
//            }
//        });

//        设置标签Y渲染器
        Transformer rightYTransformer = mChartPrice.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer rightColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisRight(), rightYTransformer);
        rightColorContentYAxisRenderer.setLabelInContent(true);
        rightColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        rightColorContentYAxisRenderer.setLabelColor(colorArray);
        mChartPrice.setRendererRightYAxis(rightColorContentYAxisRenderer);

    }


    private void initChartListener() {
        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(this, mChartPrice));
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener());

        mChartPrice.setOnTouchListener(new ChartInfoViewHandler(mChartPrice));
    }


    public void initData(List<HisData> hisDatas) {

        mData.clear();
        mData.addAll(DataUtils.calculateHisData(hisDatas));
        mChartPrice.setRealCount(hisDatas.size());

        mChartPrice.fitScreen();
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener());

        ArrayList<Entry> priceEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            priceEntries.add(new Entry(i, (float) mData.get(i).getClose()));
        }
        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(NORMAL_LINE, priceEntries));
        sets.add(setLine(INVISIABLE_LINE, paddingEntries));
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        mChartPrice.setData(combinedData);

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.notifyDataSetChanged();
//        mChartPrice.moveViewToX(combinedData.getEntryCount());
        moveToLast(mChartPrice, MAX_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);
        mChartPrice.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);

    }

    @android.support.annotation.NonNull
    private LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        if (type == NORMAL_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
            lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.normal_line_color));
        } else if (type == NORMAL_LINE_5DAY) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
            lineDataSetMa.setCircleColor(mTransparentColor);
        } else if (type == AVE_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
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
        lineDataSetMa.setDrawFilled(true);
        lineDataSetMa.setFillColor(getResources().getColor(R.color.ys_219_183_108));
        return lineDataSetMa;
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

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
    }

    /**
     * align two chart
     */
    private void setOffset() {
        int chartHeight = getResources().getDimensionPixelSize(R.dimen.bottom_chart_height);
        mChartPrice.setViewPortOffsets(0, 0, 0, chartHeight);
    }

    public HisData getLastData() {
        if (mData != null && !mData.isEmpty()) {
            return mData.get(mData.size() - 1);
        }
        return null;
    }

    @Override
    public void onAxisChange(BarLineChartBase chart) {

    }
}
