package com.cai.work.ui.stock;

import android.graphics.Bitmap;
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

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/StockActivity", name = "股票详情页面")
public class StockActivity extends AppBaseActivity<StockBinding> implements StockView {

    @Inject
    StockPresenter presenter;
    @Autowired(name = "isRealTrade")
    boolean isRealTrade =true;//是否是真实交易
    @Inject
    ILoadImage imageLoader;
    StockHQ stockHQ;
    int spinerPopwindowHeight;
    StockAdapter adapter;
    SpinerPopWindow spinerPopWindow;
    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    String stockCode;
    int red_color, green_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spinerPopwindowHeight = DimensUtils.dp2px(this, 350);
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
        red_color = getResources().getColor(R.color.ys_220_1_7);
        green_color = getResources().getColor(R.color.ys_65_255_173);
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(R.color.ys_24_24_24);
        }
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
                ARouter.getInstance().build("/AppModule/WebActivity").withCharSequence("url", "http://m.hellceshi.com/tpl/app/stock_rule.html").withCharSequence("title", "规则").navigation();
            }
        });
        mViewBinding.tvFenShi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImage(1);
            }
        });
        mViewBinding.tvKLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImage(2);
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
        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(stockCode)) {
                    ARouter.getInstance().build("/AppModule/StockBuyActivity")
                            .withCharSequence("stockCode", stockCode)
                            .withBoolean("isRealTrade", isRealTrade)
                            .navigation();
                }
            }
        });
        adapter = new StockAdapter(this);
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setAdatper(adapter);
        spinerPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stock stock = (Stock) adapter.getItem(position);
                presenter.loadImage(stock.getStockCode(),stock.getStockMarket());
                presenter.requestStockHq(stock.getStockCode());
                presenter.requestStockHistory(stock.getStockCode());
                KeyBoardUtils.forceHide(mViewBinding.tvSearch);
                spinerPopWindow.dismiss();
            }
        });
        switchImage(1);
        presenter.requestStockTrade();
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                removeKeyboardListener();
            }
        });

        mViewBinding.kline.setDateFormat("yyyy-MM-dd");

    }

    private void switchImage(int i) {
        if (i == 1) {
            mViewBinding.tvFenShi.setTextColor(getResources().getColor(R.color.ys_219_183_108));
            mViewBinding.tvKLine.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            mViewBinding.kline.setVisibility(View.GONE);
            mViewBinding.imgFenshi.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.tvFenShi.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            mViewBinding.tvKLine.setTextColor(getResources().getColor(R.color.ys_219_183_108));
            mViewBinding.kline.setVisibility(View.VISIBLE);
            mViewBinding.imgFenshi.setVisibility(View.GONE);
        }
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
        ToastUtils.showLong(msg);
    }

    @Override
    public void callBack(StockTrade data) {
        presenter.loadImage(data.getStock_code(),data.getShortMarket());
        stockCode = data.getStock_code();
        presenter.requestStockHq(data.getStock_code());
        presenter.requestStockHistory(data.getStock_code());

        if (data.getIsTrade().equals("2")) {
            mViewBinding.btnCommit.setClickable(false);
            mViewBinding.btnCommit.setText(R.string.btn_not_buy);
            mViewBinding.btnCommit.setBackgroundResource(R.drawable.btn_gray);
        } else {
            mViewBinding.btnCommit.setClickable(true);
            mViewBinding.btnCommit.setText(R.string.btn_buy);
            mViewBinding.btnCommit.setBackgroundResource(R.drawable.btn_red);
        }
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
            int size = data.length;
            List<HisData> hisDataList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                HisData hisData = new HisData();
                long date = DateUtils.date2TimeStamp(data[i][0], "yyyy/MM/dd");
                hisData.setDate(date);
                if (data[i].length > 1) {
                    hisData.setOpen(Float.valueOf(data[i][1]));
                }
                if (data[i].length > 2) {
                    hisData.setClose(Float.valueOf(data[i][2]));
                }
                if (data[i].length > 3) {
                    hisData.setLow(Float.valueOf(data[i][3]));
                }
                if (data[i].length > 1) {
                    hisData.setHigh(Float.valueOf(data[i][1]));
                }

                if (data[i].length > 6) {
                    hisData.setVol(Long.valueOf(data[i][6]));
                }
                hisDataList.add(hisData);
            }
            mViewBinding.kline.setCount(size, size, size);
            mViewBinding.kline.initData(hisDataList);
            mViewBinding.kline.setLimitLine();
        }
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mViewBinding.imgFenshi.setImageBitmap(bitmap);
    }

    private void refreshView() {


        mViewBinding.tvName.setText(stockHQ.getStName());
        mViewBinding.tvCode.setText(("1".equals(stockHQ.getMk_code()) ? "SZ" : "SH") + stockHQ.getStCode());

        mViewBinding.tvPrice1.setText(stockHQ.getMk_price());
        if (stockHQ.getZhangdie().contains("-")) {
            mViewBinding.tvPrice1.setTextColor(getResources().getColor(R.color.ys_22_110_15));
            mViewBinding.tvPrice2.setTextColor(getResources().getColor(R.color.ys_22_110_15));
            mViewBinding.tvPrice3.setTextColor(getResources().getColor(R.color.ys_22_110_15));
        } else {
            mViewBinding.tvPrice1.setTextColor(getResources().getColor(R.color.ys_232_0_63));
            mViewBinding.tvPrice2.setTextColor(getResources().getColor(R.color.ys_232_0_63));
            mViewBinding.tvPrice3.setTextColor(getResources().getColor(R.color.ys_232_0_63));
        }
        mViewBinding.tvPrice2.setText(stockHQ.getZhangdie());
        mViewBinding.tvPrice3.setText(stockHQ.getZhangfu());

        mViewBinding.tvSell1.setText(stockHQ.getSp1() + "");
        if (stockHQ.getKp_price() < stockHQ.getSp1()) {
            mViewBinding.tvSell1.setTextColor(red_color);
        } else {
            mViewBinding.tvSell1.setTextColor(green_color);
        }
        mViewBinding.tvSell2.setText(stockHQ.getSp2() + "");
        if (stockHQ.getKp_price() < stockHQ.getSp2()) {
            mViewBinding.tvSell2.setTextColor(red_color);
        } else {
            mViewBinding.tvSell2.setTextColor(green_color);
        }
        mViewBinding.tvSell3.setText(stockHQ.getSp3() + "");
        if (stockHQ.getKp_price() < stockHQ.getSp3()) {
            mViewBinding.tvSell3.setTextColor(red_color);
        } else {
            mViewBinding.tvSell3.setTextColor(green_color);
        }
        mViewBinding.tvSell4.setText(stockHQ.getSp4() + "");
        if (stockHQ.getKp_price() < stockHQ.getSp4()) {
            mViewBinding.tvSell4.setTextColor(red_color);
        } else {
            mViewBinding.tvSell4.setTextColor(green_color);
        }
        mViewBinding.tvSell5.setText(stockHQ.getSp5() + "");
        if (stockHQ.getKp_price() < stockHQ.getSp5()) {
            mViewBinding.tvSell5.setTextColor(red_color);
        } else {
            mViewBinding.tvSell5.setTextColor(green_color);
        }

        mViewBinding.tvSellNum1.setText(stockHQ.getSn1() / 100 + "");
        mViewBinding.tvSellNum2.setText(stockHQ.getSn2() / 100 + "");
        mViewBinding.tvSellNum3.setText(stockHQ.getSn3() / 100 + "");
        mViewBinding.tvSellNum4.setText(stockHQ.getSn4() / 100 + "");
        mViewBinding.tvSellNum5.setText(stockHQ.getSn5() / 100 + "");


        mViewBinding.tvBuy1.setText(stockHQ.getBp1() + "");
        if (stockHQ.getKp_price() < stockHQ.getBp1()) {
            mViewBinding.tvBuy1.setTextColor(red_color);
        } else {
            mViewBinding.tvBuy1.setTextColor(green_color);
        }
        mViewBinding.tvBuy2.setText(stockHQ.getBp2() + "");
        if (stockHQ.getKp_price() < stockHQ.getBp2()) {
            mViewBinding.tvBuy2.setTextColor(red_color);
        } else {
            mViewBinding.tvBuy2.setTextColor(green_color);
        }
        mViewBinding.tvBuy3.setText(stockHQ.getBp3() + "");
        if (stockHQ.getKp_price() < stockHQ.getBp3()) {
            mViewBinding.tvBuy3.setTextColor(red_color);
        } else {
            mViewBinding.tvBuy3.setTextColor(green_color);
        }
        mViewBinding.tvBuy4.setText(stockHQ.getBp4() + "");
        if (stockHQ.getKp_price() < stockHQ.getBp4()) {
            mViewBinding.tvBuy4.setTextColor(red_color);
        } else {
            mViewBinding.tvBuy4.setTextColor(green_color);
        }
        mViewBinding.tvBuy5.setText(stockHQ.getBp5() + "");
        if (stockHQ.getKp_price() < stockHQ.getBp5()) {
            mViewBinding.tvBuy5.setTextColor(red_color);
        } else {
            mViewBinding.tvBuy5.setTextColor(green_color);
        }

        mViewBinding.tvBuyNum1.setText(stockHQ.getBn1() / 100 + "");
        mViewBinding.tvBuyNum2.setText(stockHQ.getBn2() / 100 + "");
        mViewBinding.tvBuyNum3.setText(stockHQ.getBn3() / 100 + "");
        mViewBinding.tvBuyNum4.setText(stockHQ.getBn4() / 100 + "");
        mViewBinding.tvBuyNum5.setText(stockHQ.getBn5() / 100 + "");

        mViewBinding.stockBottom1.setText(stockHQ.getKp_price() + "");
        mViewBinding.stockBottom2.setText(stockHQ.getZhenfu());
        mViewBinding.stockBottom3.setText(stockHQ.getUp_price());
        mViewBinding.stockBottom4.setText(stockHQ.getDn_price());
        mViewBinding.stockBottom5.setText(stockHQ.getUp_limit());
        mViewBinding.stockBottom6.setText(stockHQ.getDn_limit());
        mViewBinding.stockBottom7.setText(stockHQ.getCjss());
        mViewBinding.stockBottom8.setText(stockHQ.getCjje());


    }
}
