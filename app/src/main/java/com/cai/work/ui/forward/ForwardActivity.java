package com.cai.work.ui.forward;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.spiner.SpinerPopWindow;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardDetail;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.Record;
import com.cai.work.databinding.ForwardBinding;
import com.cai.work.event.ForwardDetailEvent;
import com.cai.work.kline.HisData;
import com.cai.work.kline.TimeLineView;
import com.cai.work.socket.SocketManager;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/ForwardActivity", name = "期货详情页面")
public class ForwardActivity extends AppBaseActivity<ForwardBinding> implements ForwardView {

    @Autowired(name = "forwardJson")
    String forwardJson;
    @Autowired(name = "isRealTrade")
    boolean isRealTrade = true;//是否是真实交易
    @Inject
    ForwardPresenter presenter;
    ForwardAdapter adapter;
    SpinerPopWindow spinerPopWindow;
    int spinerPopwindowHeight;
    Forward forward;
    ForwardDetail forwardDetailt;
    boolean isStartSocket;

    public static final String TYPE_RESOLUTION_DAY = "day";
    public static final String TYPE_RESOLUTION_MINUTE = "minute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        spinerPopwindowHeight = DimensUtils.dp2px(this, 350);
        EventBus.getDefault().register(this);
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
        return R.layout.forward;
    }

    @Override
    public void initView() {
        initData();
        initHead();
        initSpinner();
        refreshView();
        mViewBinding.btnRise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/ForwardDetailActivity")
                        .withInt("type", 1)
                        .withCharSequence("forwardCode", forward.getCode())
                        .withBoolean("isRealTrade", isRealTrade)
                        .navigation();
            }
        });
        mViewBinding.btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/ForwardDetailActivity")
                        .withInt("type", 2)
                        .withCharSequence("forwardCode", forward.getCode())
                        .withBoolean("isRealTrade", isRealTrade)
                        .navigation();
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
        switchImage(1);
        mViewBinding.kline.setDateFormat("yyyy-MM-dd");
        mViewBinding.kline.setChartVolumeHide();
    }

    private void switchImage(int i) {
        if (i == 1) {
            mViewBinding.tvFenShi.setTextColor(getResources().getColor(R.color.ys_219_183_108));
            mViewBinding.tvKLine.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            mViewBinding.kline.setVisibility(View.GONE);
            mViewBinding.rlContainer.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.tvFenShi.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            mViewBinding.tvKLine.setTextColor(getResources().getColor(R.color.ys_219_183_108));
            mViewBinding.kline.setVisibility(View.VISIBLE);
            mViewBinding.rlContainer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SocketManager.pauseSocket();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStartSocket) {
            SocketManager.resumeSocket();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SocketManager.closeSocket();
    }

    private void refreshView() {
        if (forward != null) {
            mViewBinding.tvTitle.setText(forward.getName());
            mViewBinding.tvName.setText(forward.getName());
            mViewBinding.tvCode.setText("(" + forward.getCode().toUpperCase() + ")");
        }
        requestData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(forwardJson)) {
            forward = JSON.parseObject(forwardJson, Forward.class);
        }
    }

    private void initSpinner() {
        mViewBinding.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinWindow();
            }
        });
        adapter = new ForwardAdapter(this);
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setAdatper(adapter);
        spinerPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                forward = (Forward) adapter.getItem(position);
                refreshView();
                spinerPopWindow.dismiss();
            }
        });
    }

    private void requestData() {
        if (forward != null) {
            presenter.requestRecord(forward.getCode());
            presenter.startTimes(forward.getCode());
        }
        presenter.requestContracts();
    }

    private void initHead() {
        mViewBinding.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forward != null) {
                    ARouter.getInstance().build("/AppModule/WebActivity")
                            .withCharSequence("url", App.BASEURL + "/app/h5/help/get_rule_code?code=" + forward.getCode())
                            .withCharSequence("title", "期货规则").navigation();
                }
            }
        });
    }

    //设置PopWindow
    private void showSpinWindow() {
        //设置mSpinerPopWindow显示的宽度
        spinerPopWindow.setWidth(DimensUtils.dp2px(this, 100));
        spinerPopWindow.setHeight(spinerPopwindowHeight);
        //设置显示的位置在哪个控件的下方
        spinerPopWindow.showAsDropDown(mViewBinding.spinner);
    }


    @Override
    public void toast(String msg, int type) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(ForwardRecord forwardRecord) {
        Record record = forwardRecord.getRecords();
        if (record != null) {
            //时间
            String date = record.getBuyDate();
            if (!TextUtils.isEmpty(date)) {
                int index = date.indexOf("11:30");
                if (index != -1) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(date.substring(0, index + 5));
                    builder.append("\n");
                    builder.append(date.substring(index + 6));
                    mViewBinding.tvTradeTime.setText(builder.toString());
                } else {
                    mViewBinding.tvTradeTime.setText(record.getBuyDate());
                }
            }
            if (record.getIsTrade() == 1) {//交易
                mViewBinding.btnRise.setBackgroundResource(R.drawable.btn_red_half);
                mViewBinding.btnDrop.setBackgroundResource(R.drawable.btn_green_half);
            } else {
                mViewBinding.btnRise.setBackgroundResource(R.drawable.btn_gray_half);
                mViewBinding.btnDrop.setBackgroundResource(R.drawable.btn_gray_half2);
                mViewBinding.btnRise.setClickable(false);
                mViewBinding.btnDrop.setClickable(false);
            }
        }
    }

    private void refreshForwardView() {
        if (forwardDetailt == null) {
            return;
        }
        mViewBinding.tvPrice.setText(forwardDetailt.getMk_price());
        mViewBinding.tvPrice2.setText(forwardDetailt.getZhangdie());
        mViewBinding.tvPrice3.setText(forwardDetailt.getZhangfu() + "%");
        if (forwardDetailt.getZhangdie().contains("-")) {
            int green = getResources().getColor(R.color.ys_24_182_118);
            mViewBinding.tvPrice.setTextColor(green);
            mViewBinding.tvPrice2.setTextColor(green);
            mViewBinding.tvPrice3.setTextColor(green);
        } else {
            int green = getResources().getColor(R.color.ys_227_76_77);
            mViewBinding.tvPrice.setTextColor(green);
            mViewBinding.tvPrice2.setTextColor(green);
            mViewBinding.tvPrice3.setTextColor(green);
        }


        mViewBinding.tvBuy.setText(forwardDetailt.getBn1());
        mViewBinding.tvSell.setText(forwardDetailt.getSn1());
        mViewBinding.tvRisePrice.setText(forwardDetailt.getUp_price());
        mViewBinding.tvDropPrice.setText(forwardDetailt.getDn_price());

        try {
            int bn1 = Integer.valueOf(forwardDetailt.getBn1());
            int sn1 = Integer.valueOf(forwardDetailt.getSn1());
            mViewBinding.progressLeft.setProgress((int) (bn1 * 1.0 / (bn1 + sn1) * 100));
            mViewBinding.progressRight.setProgress((int) (sn1 * 1.0 / (bn1 + sn1) * 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void callBack(List<Forward> forwardList) {
        adapter.update(forwardList);
    }

    @Override
    public void callBack(String[][] data, String resolution) {
        if (data != null && data.length > 0) {
            List<HisData> hisDataList = new ArrayList<>();
            if (TYPE_RESOLUTION_DAY.equals(resolution)) {
                for (int i = 0; i < data.length; i++) {
                    HisData hisData = new HisData();
                    long date = DateUtils.date2TimeStamp(data[i][0], "yyyyMMdd");
                    hisData.setDate(date);
                    if (data[i].length > 1) {
                        hisData.setOpen(Double.valueOf(data[i][1]));
                    }
                    if (data[i].length > 2) {
                        hisData.setClose(Double.valueOf(data[i][2]));
                    }
                    if (data[i].length > 3) {
                        hisData.setLow(Double.valueOf(data[i][3]));
                    }
                    if (data[i].length > 1) {
                        hisData.setHigh(Double.valueOf(data[i][1]));
                    }

                    if (data[i].length > 6) {
                        hisData.setVol(Long.valueOf(data[i][6]));
                    }
                    hisDataList.add(hisData);
                }
                int size = hisDataList.size();
                mViewBinding.kline.setCount(size, size, 50);
                mViewBinding.kline.initData(hisDataList);
                mViewBinding.kline.setLimitLine();
            } else {
                if (data.length < 4) {
                    return;
                }
                int size = data.length;
                for (int i = 0; i < size; i++) {
                    HisData hisData = new HisData();
                    long date = DateUtils.date2TimeStamp(data[i][0], "HH:mm");
                    hisData.setDate(date);
                    if (data[i].length > 1) {
                        hisData.setClose(Double.valueOf(data[i][1]));
                    }
                    hisDataList.add(hisData);
                }
                TimeLineView fenshiView = new TimeLineView(this);
                fenshiView.setDateFormat("HH:mm");
                fenshiView.setCount(200, size + 70, 60);
                fenshiView.initData(hisDataList);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                mViewBinding.rlContainer.removeAllViews();
                mViewBinding.rlContainer.addView(fenshiView, layoutParams);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void forwardDetailtCallback(ForwardDetailEvent event) {
        forwardDetailt = event.detail;
        isStartSocket = true;
        refreshForwardView();
    }
}
