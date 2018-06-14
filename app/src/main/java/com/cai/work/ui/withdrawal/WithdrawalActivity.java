package com.cai.work.ui.withdrawal;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.BottomMenuDialog;
import com.cai.framework.widget.dialog.BottomMenuModel;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Withdrawal;
import com.cai.work.bean.Withdrawalkind;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WithdrawalBinding;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WithdrawalActivity", name = "提现")
public class WithdrawalActivity extends AppBaseActivity<WithdrawalBinding> implements WithdrawalView {
    @Inject
    WithdrawalPresenter presenter;
    Withdrawal withdrawal;
    int withdrawKind = 1;
    List<Withdrawalkind> withdrawalkindList = new ArrayList<>();

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
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.withdrawal_titile));

        mViewBinding.imgWithdrawKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuDialog();
            }
        });

        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mViewBinding.editWithdrawalMoney.getText().toString();
                if (TextUtils.isEmpty(amount)) {
                    ToastUtils.showShort(getString(R.string.withdrawal_amount_toast));
                    mViewBinding.editWithdrawalMoney.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editWithdrawalMoney);
                    return;
                }

                String password = mViewBinding.editPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showShort(getString(R.string.withdrawal_password_toast));
                    mViewBinding.editPassword.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editPassword);
                    return;
                }
                int cardId = withdrawal.getBank_list().get(1).getId();
                presenter.commitWithdrawal(cardId, amount, password, withdrawKind);
            }
        });
        presenter.requestWithdrawal();
    }

    private void showMenuDialog() {
        if (withdrawalkindList.size() == 0) {
            return;
        }
        BottomMenuModel menuModel = new BottomMenuModel();
        menuModel.setTitle(getString(R.string.withdrawal_dialog_title));
        List<BottomMenuModel.BottomMenuItemModel> menuList = new ArrayList<>();

        for (Withdrawalkind withdrawalkind : withdrawalkindList) {
            BottomMenuModel.BottomMenuItemModel menuItemModel = new BottomMenuModel.BottomMenuItemModel();
            menuItemModel.setMenuName(withdrawalkind.getType());
            menuItemModel.setTag(withdrawalkind);
            menuList.add(menuItemModel);
        }

        BottomMenuModel.BottomMenuItemModel menuItemModel3 = new BottomMenuModel.BottomMenuItemModel();
        menuItemModel3.setMenuName("取消");
        menuItemModel3.setTag("cancel");
        menuList.add(menuItemModel3);

        menuModel.setMenuList(menuList);
        menuModel.setClickListener(new BottomMenuModel.BottomMenuItemClickListener() {
            @Override
            public void onClick(BottomMenuDialog dialog, View v, BottomMenuModel.BottomMenuItemModel itemModel) {
                if (itemModel.getTag() instanceof Withdrawalkind) {
                    Withdrawalkind withdrawalkind = ((Withdrawalkind) itemModel.getTag());
                    mViewBinding.tvWithdrawKind.setText(withdrawalkind.getType());//到账时间
                    mViewBinding.tvWithdrawalPoundage.setText(withdrawalkind.getFee());
                }
                dialog.dismiss();
            }
        });
        BottomMenuDialog bottomMenuDialog = new BottomMenuDialog(this, menuModel);
        bottomMenuDialog.show();

    }

    @Override
    public int getLayoutId() {
        return R.layout.withdrawal;
    }

    @Override
    public void update(Withdrawal data) {
        withdrawal = data;
        List<String> typeKey = new ArrayList<>(withdrawal.getType().keySet());
        for (String key : typeKey) {
            Withdrawalkind withdrawalkind = new Withdrawalkind();
            withdrawalkind.setId(key);
            withdrawalkind.setType(withdrawal.getType().get(key));
            withdrawalkind.setFee(withdrawal.getFee().get(key));
            withdrawalkindList.add(withdrawalkind);
        }
        if (withdrawalkindList.size() > 0) {
            mViewBinding.tvWithdrawKind.setText(withdrawalkindList.get(0).getType());
            mViewBinding.tvWithdrawalPoundage.setText(withdrawalkindList.get(0).getFee());
        }
        mViewBinding.tvBalanceMoney.setText(data.getBalance());
    }

    @Override
    public void commitState(String msg) {
        ToastUtils.showShort(msg);
        finish();
    }
}
