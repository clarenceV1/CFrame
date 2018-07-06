package com.cai.work.ui.redPacket;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.StockBuyRedBag;
import com.cai.work.databinding.RedPacketSelectBinding;

import java.util.List;

@Route(path = "/AppModule/RedPacketSelectActivity", name = "红包选择")
public class RedPacketSelectActivity extends AppBaseActivity<RedPacketSelectBinding> {

    RedPacketSelectAdapter adapter;

    @Autowired(name = "datas")
    String dataStr;
    List<StockBuyRedBag> redBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {

    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText("红包选择");
        if (!TextUtils.isEmpty(dataStr)) {
            redBags = JSON.parseArray(dataStr, StockBuyRedBag.class);
        }
//        adapter = new RedPacketSelectAdapter(this);
//        mViewBinding.listView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.red_packet_select;
    }

}
