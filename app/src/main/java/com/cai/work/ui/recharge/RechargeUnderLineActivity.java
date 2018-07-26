package com.cai.work.ui.recharge;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageloaderlibrary.ILoadImage;
import com.cai.framework.utils.PhotoUtils;
import com.cai.framework.widget.dialog.BottomMenuDialog;
import com.cai.framework.widget.dialog.BottomMenuModel;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RechargeBank;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RechargeUnderlineBinding;
import com.cai.work.ui.login.SaveActivity;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeUnderLine", name = "线下充值")
public class RechargeUnderLineActivity extends AppBaseActivity<RechargeUnderlineBinding> implements RechargeUnderLineView {
    @Inject
    ILoadImage imageLoader;
    @Inject
    RechargeUnderLinePresenter presenter;
    RechargeUnderLineAdapter adapter;
    String imageUploadPath;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.recharge_underline_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.tvAccountManey.setText(presenter.getMoney());
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
                    mViewBinding.editPayAccount.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editPayAccount);
                    return;
                }
                int offlineId = adapter.getChooseId();
                if (offlineId == -1) {
                    ToastUtils.showShort(getString(R.string.recharge_payway));
                    return;
                }
                presenter.commitPay(offlineName, amount, offlineId, offlineAccount, imageUploadPath);
            }
        });
        mViewBinding.imgUploadProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuDialog();
            }
        });
        presenter.requestRechargeBankList();
    }
    private void showMenuDialog() {
        BottomMenuModel menuModel = new BottomMenuModel();
        menuModel.setTitle(getString(R.string.recharge_underline_dialog_title));
        List<BottomMenuModel.BottomMenuItemModel> menuList = new ArrayList<>();

        BottomMenuModel.BottomMenuItemModel menuItemModel = new BottomMenuModel.BottomMenuItemModel();
        menuItemModel.setMenuName("拍照");
        menuItemModel.setTag(1);
        menuList.add(menuItemModel);

        BottomMenuModel.BottomMenuItemModel menuItemModel2 = new BottomMenuModel.BottomMenuItemModel();
        menuItemModel2.setMenuName("相册");
        menuItemModel2.setTag(2);
        menuList.add(menuItemModel2);

        BottomMenuModel.BottomMenuItemModel menuItemModel3 = new BottomMenuModel.BottomMenuItemModel();
        menuItemModel3.setMenuName("取消");
        menuItemModel3.setTag(3);
        menuList.add(menuItemModel3);

        menuModel.setMenuList(menuList);
        menuModel.setClickListener(new BottomMenuModel.BottomMenuItemClickListener() {
            @Override
            public void onClick(BottomMenuDialog dialog, View v, BottomMenuModel.BottomMenuItemModel itemModel) {
                int tag = ((int) itemModel.getTag());
                if (tag == 1) {
                    PhotoUtils.getInstance().takePhoto(RechargeUnderLineActivity.this);
                } else if(tag ==2){
                    PhotoUtils.getInstance().setCrop(true);
                    PhotoUtils.getInstance().choosePhone(RechargeUnderLineActivity.this);
                }
                dialog.dismiss();
            }
        });
        BottomMenuDialog bottomMenuDialog = new BottomMenuDialog(this, menuModel);
        bottomMenuDialog.show();

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
        finish();
    }

    @Override
    public void callBackImagePath(String path) {
        imageUploadPath = path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        String path = PhotoUtils.getInstance().onActivityResult(this, requestCode, resultCode, data);
        if (!TextUtils.isEmpty(path)) {
            presenter.uploadImage(path);
        }
    }
}
