package com.cai.work.ui.forward;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import com.cai.work.bean.ForwardHold;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.Record;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ForwardBinding;
import com.cai.work.event.ForwardDetailEvent;
import com.cai.work.event.ForwardHoldEvent;
import com.cai.work.kline.HisData;
import com.cai.work.socket.SocketManager;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.koushikdutta.async.http.WebSocket;

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
    @Inject
    ForwardPresenter presenter;
    ForwardAdapter adapter;
    SpinerPopWindow spinerPopWindow;
    int spinerPopwindowHeight;
    Forward forward;
    ForwardDetail forwardDetailt;
    boolean isStartSocket;

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
                        .navigation();
            }
        });
        mViewBinding.btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/ForwardDetailActivity")
                        .withInt("type", 2)
                        .withCharSequence("forwardCode", forward.getCode())
                        .navigation();
            }
        });
        mViewBinding.kline.setDateFormat("yyyy-MM-dd");
        mViewBinding.kline.setChartVolumeHide();
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
//            presenter.requestMinData(forward.getCode(),"minute");
            presenter.requestMinData(forward.getCode(), "day");
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
                ARouter.getInstance().build("/AppModule/WebActivity").withCharSequence("url", "http://m.hellceshi.com/tpl/app/futures_rule.html").withCharSequence("title", "期货规则").navigation();
            }
        });
    }

    //设置PopWindow
    private void showSpinWindow() {
        int spinerWith = mViewBinding.spinner.getWidth();
        //设置mSpinerPopWindow显示的宽度
        spinerPopWindow.setWidth(spinerWith);
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
            mViewBinding.tvTradeTime.setText(record.getBuyDate());
        }
    }

    private void refreshForwardView() {
        if (forwardDetailt == null) {
            return;
        }
        mViewBinding.tvPrice.setText(forwardDetailt.getMk_price());
        mViewBinding.tvPrice2.setText(forwardDetailt.getZhangdie());
        mViewBinding.tvPrice3.setText(forwardDetailt.getZhangfu());

        mViewBinding.tvBuy.setText(forwardDetailt.getBn1());
        mViewBinding.tvSell.setText(forwardDetailt.getSn1());
        mViewBinding.tvRisePrice.setText(forwardDetailt.getUp_price());
        mViewBinding.tvDropPrice.setText(forwardDetailt.getDn_price());
    }


    @Override
    public void callBack(List<Forward> forwardList) {
        adapter.update(forwardList);
    }

    @Override
    public void callBack(String[][] data, String resolution) {
        if (data != null && data.length > 0) {
            List<HisData> hisDataList = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
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
            mViewBinding.kline.initData(hisDataList);
            mViewBinding.kline.setLimitLine();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chooseBankCard(ForwardDetailEvent event) {
        forwardDetailt = event.detail;
        isStartSocket = true;
        refreshForwardView();
    }
}
