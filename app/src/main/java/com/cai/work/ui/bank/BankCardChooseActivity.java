package com.cai.work.ui.bank;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageloaderlibrary.ILoadImage;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.WithdrawalBank;
import com.cai.work.databinding.BankCardChooseBinding;
import com.cai.work.event.BankCardChooseEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/BankCardChooseActivity", name = "选择银行卡")
public class BankCardChooseActivity extends AppBaseActivity<BankCardChooseBinding> implements BankCardChooselView {

    @Autowired(name = "dataList")
    String dataJson;
    @Inject
    BankCardChoosePresenter presenter;
    BankCardChooseAdapter adapter;
    List<WithdrawalBank> bankCardList = null;
    WithdrawalBank choose;
    @Inject
    ILoadImage imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
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
    public void initView() {
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choose != null) {
                    EventBus.getDefault().post(new BankCardChooseEvent(choose));
                }
                finish();
            }
        });
        mViewBinding.commonHeadView.imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ARouter.getInstance().build("/AppModule/BankAddActivity").navigation();
            }
        });
        mViewBinding.commonHeadView.imgRight.setImageResource(R.drawable.bankadd);
        mViewBinding.commonHeadView.imgRight.setVisibility(View.VISIBLE);
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.bank_card_choose_titile));

        if (!TextUtils.isEmpty(dataJson)) {
            bankCardList = JSON.parseArray(dataJson, WithdrawalBank.class);
        }
        adapter = new BankCardChooseAdapter(this, imageLoader, bankCardList);
        mViewBinding.listView.setAdapter(adapter);
        mViewBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choose != null) {
                    choose.setChoose(false);
                }
                choose = bankCardList.get(position);
                EventBus.getDefault().post(new BankCardChooseEvent(choose));
                finish();
//                choose.setChoose(true);
//                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.bank_card_choose;
    }

}
