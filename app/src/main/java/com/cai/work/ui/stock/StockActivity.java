package com.cai.work.ui.stock;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.spiner.SpinerPopWindow;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Stock;
import com.cai.work.bean.StockHQ;
import com.cai.work.bean.StockTrade;
import com.cai.work.databinding.StockBinding;
import com.cai.work.kline.HisData;
import com.cai.work.kline.KLineView;
import com.cai.work.kline.OnLoadMoreListener;
import com.cai.work.kline.Util;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/StockActivity", name = "股票详情页面")
public class StockActivity extends AppBaseActivity<StockBinding> implements StockView {

    @Inject
    StockPresenter presenter;
    @Inject
    ILoadImage imageLoader;
    StockHQ stockHQ;
    int spinerPopwindowHeight;
    StockAdapter adapter;
    SpinerPopWindow spinerPopWindow;
    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    private KLineView mKLineView;
    String imgeUrl = "http://image.sinajs.cn/newchart/min/sh600000.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spinerPopwindowHeight = DimensUtils.dip2px(this, 350);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.stock;
    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.stock_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvRight.setVisibility(View.VISIBLE);
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.stock_rule));
        mViewBinding.commonHeadView.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("http://www.baidu.com").navigation();
            }
        });
        mViewBinding.tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString();
                if (TextUtils.isEmpty(search)) {
                    if (spinerPopWindow.isShowing()) {
                        spinerPopWindow.dismiss();
                    }
                    return;
                }
                presenter.requestStockList(search);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adapter = new StockAdapter(this);
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setAdatper(adapter);
        spinerPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stock stock = (Stock) adapter.getItem(position);
                presenter.requestStockHq(stock.getStockCode());
                presenter.requestStockHistory(stock.getStockCode());
                KeyBoardUtils.forceHide(mViewBinding.tvSearch);
                spinerPopWindow.dismiss();
            }
        });
        presenter.requestStockTrade();
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                removeKeyboardListener();
            }
        });

        mKLineView = (KLineView) findViewById(R.id.kline);
        mKLineView.setDateFormat("yyyy-MM-dd");

        showImage();

    }

    public void showImage() {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(imgeUrl).build();
        imageParams.setImageView(mViewBinding.imgFenshi);
        imageLoader.loadImage(this, imageParams);
    }

    public void addKeyboardListener() {
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                StockActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = StockActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                if (heightDifference > 0) {
                    //keyboard show
                } else {
                    mViewBinding.tvSearch.clearFocus();
                    //keyboard hide
                    if (spinerPopWindow.isShowing()) {
                        spinerPopWindow.dismiss();
                    }
                }
            }

        };
        mViewBinding.rlSearch.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    public void removeKeyboardListener() {
        mViewBinding.rlSearch.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
    }

    //设置PopWindow
    private void showSpinWindow() {
        int spinerWith = mViewBinding.rlSearch.getWidth();
        //设置mSpinerPopWindow显示的宽度
        spinerPopWindow.setWidth(spinerWith);
        spinerPopWindow.setHeight(spinerPopwindowHeight);
        spinerPopWindow.setFocusable(false);
        //设置显示的位置在哪个控件的下方
        spinerPopWindow.showAsDropDown(mViewBinding.rlSearch);
        addKeyboardListener();
    }

    @Override
    public void toast(String msg, int type) {

    }

    @Override
    public void callBack(StockTrade data) {
        presenter.requestStockHq(data.getStock_code());
        presenter.requestStockHistory(data.getStock_code());
    }

    @Override
    public void callBack(StockHQ data) {
        stockHQ = data;
        refreshView();
    }

    @Override
    public void callBack(List<Stock> data) {
        if (data != null && data.size() > 0) {
            adapter.update(data);
            if (!spinerPopWindow.isShowing()) {
                showSpinWindow();
            }
        } else {
            if (spinerPopWindow.isShowing()) {
                spinerPopWindow.dismiss();
            }
        }

    }

    @Override
    public void callBack(String[][] data) {
        if (data != null && data.length > 0) {
            List<HisData> hisDataList = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                HisData hisData = new HisData();
                long date = DateUtils.date2TimeStamp(data[i][0], "yyyy/MM/dd");
                hisData.setDate(date);
                hisData.setOpen(Float.valueOf(data[i][1]));
                hisData.setClose(Float.valueOf(data[i][2]));
                hisData.setLow(Float.valueOf(data[i][3]));
                hisData.setHigh(Float.valueOf(data[i][1]));
                hisData.setVol(Long.valueOf(data[i][6]));
                hisDataList.add(hisData);
            }
            mKLineView.initData(hisDataList);
            mKLineView.setLimitLine();
            mKLineView.setOnLoadMoreListener(null);
        }
    }

    private void refreshView() {

        mViewBinding.tvName.setText(stockHQ.getStName());
        mViewBinding.tvCode.setText(("1".equals(stockHQ.getMk_code()) ? "SZ" : "SH") + stockHQ.getStCode());

        mViewBinding.tvPrice1.setText(stockHQ.getMk_price());
        mViewBinding.tvPrice2.setText(stockHQ.getZhangdie());
        mViewBinding.tvPrice3.setText(stockHQ.getZhangfu());

        mViewBinding.tvSell1.setText(stockHQ.getSp1());
        mViewBinding.tvSell2.setText(stockHQ.getSp2());
        mViewBinding.tvSell3.setText(stockHQ.getSp3());
        mViewBinding.tvSell4.setText(stockHQ.getSp4());
        mViewBinding.tvSell5.setText(stockHQ.getSp5());

        mViewBinding.tvSellNum1.setText(stockHQ.getSn1() / 100 + "");
        mViewBinding.tvSellNum2.setText(stockHQ.getSn2() / 100 + "");
        mViewBinding.tvSellNum3.setText(stockHQ.getSn3() / 100 + "");
        mViewBinding.tvSellNum4.setText(stockHQ.getSn4() / 100 + "");
        mViewBinding.tvSellNum5.setText(stockHQ.getSn5() / 100 + "");

        mViewBinding.tvBuy1.setText(stockHQ.getBp1());
        mViewBinding.tvBuy2.setText(stockHQ.getBp2());
        mViewBinding.tvBuy3.setText(stockHQ.getBp3());
        mViewBinding.tvBuy4.setText(stockHQ.getBp4());
        mViewBinding.tvBuy5.setText(stockHQ.getBp5());

        mViewBinding.tvBuyNum1.setText(stockHQ.getBn1() / 100 + "");
        mViewBinding.tvBuyNum2.setText(stockHQ.getBn2() / 100 + "");
        mViewBinding.tvBuyNum3.setText(stockHQ.getBn3() / 100 + "");
        mViewBinding.tvBuyNum4.setText(stockHQ.getBn4() / 100 + "");
        mViewBinding.tvBuyNum5.setText(stockHQ.getBn5() / 100 + "");

        mViewBinding.stockBottom1.setText(stockHQ.getKp_price());
        mViewBinding.stockBottom2.setText(stockHQ.getZhenfu());
        mViewBinding.stockBottom3.setText(stockHQ.getUp_price());
        mViewBinding.stockBottom4.setText(stockHQ.getDn_price());
        mViewBinding.stockBottom5.setText(stockHQ.getUp_limit());
        mViewBinding.stockBottom6.setText(stockHQ.getDn_limit());
        mViewBinding.stockBottom7.setText(stockHQ.getCjss());
        mViewBinding.stockBottom8.setText(stockHQ.getCjje());
    }
}
