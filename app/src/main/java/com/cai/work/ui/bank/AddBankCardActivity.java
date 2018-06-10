package com.cai.work.ui.bank;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.AddBankCardBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/BankAddActivity", name = "添加银行卡")
public class AddBankCardActivity extends AppBaseActivity<AddBankCardBinding> implements AddBankCardView {
    @Inject
    AddBankCardPresenter presenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.bank_add_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String realName = mViewBinding.etRealName.getText().toString();
                String bankNum = mViewBinding.etBankNum.getText().toString();
                String whichBank = "招商银行";
                String whichZone = "";
                String bankName = "";
                presenter.postBankInfo(realName, bankNum, whichBank, bankName);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.add_bank_card;
    }

    @Override
    public void refreshView(String msg) {
        ToastUtils.showShort(msg);
    }
}
