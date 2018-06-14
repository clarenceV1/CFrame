package com.cai.work.ui.recharge;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RechargeBank;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RechargeUnderlineBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeUnderLine", name = "线下充值")
public class RechargeUnderLineActivity extends AppBaseActivity<RechargeUnderlineBinding> implements RechargeUnderLineView {
    @Inject
    ILoadImage imageLoader;
    @Inject
    RechargeUnderLinePresenter presenter;
    RechargeUnderLineAdapter adapter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.save_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new RechargeUnderLineAdapter(this, imageLoader);
        mViewBinding.listView.setAdapter(adapter);
        mViewBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setChoose(adapter.getItem(position));
            }
        });
        mViewBinding.btnCommitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mViewBinding.editRechargeMoney.getText().toString();
                if (TextUtils.isEmpty(amount)) {
                    ToastUtils.showShort(getString(R.string.recharge_no_money_toast));
                    mViewBinding.editRechargeMoney.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editRechargeMoney);
                    return;
                }
                String offlineName = mViewBinding.editPayUser.getText().toString();
                if (TextUtils.isEmpty(offlineName)) {
                    ToastUtils.showShort(getString(R.string.recharge_pay_user_hint));
                    mViewBinding.editPayUser.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editPayUser);
                    return;
                }
                String offlineAccount = mViewBinding.editPayAccount.getText().toString();
                if (TextUtils.isEmpty(offlineAccount)) {
                    ToastUtils.showShort(getString(R.string.recharge_no_money_toast));
                    mViewBinding.editRechargeMoney.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editPayAccount);
                    return;
                }
                int offlineId = adapter.getChooseId();
                if (offlineId == -1) {
                    ToastUtils.showShort(getString(R.string.recharge_payway));
                    return;
                }
                String offlineImageUrl = null;
                presenter.commitPay(offlineName, amount, offlineId, offlineAccount, offlineImageUrl);
            }
        });
        presenter.requestRechargeBankList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.recharge_underline;
    }

    @Override
    public void updateListView(List<RechargeBank> dataList) {
        adapter.update(dataList);
    }

    @Override
    public void payState(String msg) {
        ToastUtils.showShort(msg);
    }
}
