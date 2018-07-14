package com.cai.work.ui.bank;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.wheel.OneWheelDialog;
import com.cai.framework.widget.dialog.wheel.OneWheelModel;
import com.cai.framework.widget.dialog.wheel.ThreeWheelDialog;
import com.cai.framework.widget.dialog.wheel.ThreeWheelModel;
import com.cai.framework.widget.dialog.wheel.WheelCallBackListener;
import com.cai.framework.widget.dialog.wheel.WheelModel;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Bank;
import com.cai.work.bean.City;
import com.cai.work.bean.Province;
import com.cai.work.databinding.AddBankCardBinding;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/BankAddActivity", name = "添加银行卡")
public class AddBankCardActivity extends AppBaseActivity<AddBankCardBinding> implements AddBankCardView {
    @Inject
    AddBankCardPresenter presenter;
    List<Bank> bankList;
    Bank selectBank;

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
        presenter.getBankList(false);
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.bank_add_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.tvWhichBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBankListDialog();
            }
        });
        mViewBinding.tvWhichZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankList == null) {
                    presenter.getBankList(true);
                } else {
                    presenter.loadCitys();
                }
            }
        });
        mViewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String realName = mViewBinding.etRealName.getText().toString();
                if (TextUtils.isEmpty(realName)) {
                    ToastUtils.showShort("请输入您的真实姓名");
                    mViewBinding.etRealName.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.etRealName);
                    return;
                }
                String bankNum = mViewBinding.etBankNum.getText().toString();
                if (TextUtils.isEmpty(bankNum)) {
                    ToastUtils.showShort("请输入您的银行卡号");
                    mViewBinding.etBankNum.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.etBankNum);
                    return;
                }
                String whichBank = mViewBinding.tvWhichBank.getText().toString();
                if (TextUtils.isEmpty(whichBank)) {
                    ToastUtils.showShort("请选择银行");
                    return;
                }
                String whichZone = mViewBinding.tvWhichZone.getText().toString();
                if (TextUtils.isEmpty(whichZone)) {
                    ToastUtils.showShort("请选择地区");
                    return;
                }
                String bankName = mViewBinding.etBankName.getText().toString();
                if (TextUtils.isEmpty(bankName)) {
                    mViewBinding.etBankName.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.etBankName);
                    ToastUtils.showShort("请填写开户行");
                    return;
                }
                presenter.postBankInfo(realName, bankNum, whichBank, bankName);
            }
        });
    }

    private void showBankListDialog() {
        OneWheelModel oneWheelModel = new OneWheelModel();
        oneWheelModel.setCircle(false);
        String[] content = getBankNameList();

        oneWheelModel.setContent(content);
        final OneWheelDialog oneWheelDialog = new OneWheelDialog(this, oneWheelModel);
        oneWheelDialog.setOnOKButtonListener(new WheelCallBackListener() {
            @Override
            public void onClick(Integer... params) {
                int position = params[0];
                selectBank = bankList.get(position);
                mViewBinding.tvWhichBank.setText(selectBank.getBankName());
                oneWheelDialog.dismiss();
            }
        });
        oneWheelDialog.setOnCancelButtonListener(new WheelCallBackListener() {
            @Override
            public void onClick(Integer... params) {
                oneWheelDialog.dismiss();
            }
        });
        oneWheelDialog.show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.add_bank_card;
    }

    @Override
    public void refreshView(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getBankList(List<Bank> dataList, boolean showDialog) {
        bankList = dataList;
        if (showDialog) {
            showBankListDialog();
        }
    }

    @Override
    public void showCityDialog(final List<Province> provinces) {
        ArrayList<String> Provincestr = new ArrayList<>();//省
        ArrayList<List<String>> Citystr = new ArrayList<>();//市
        ArrayList<List<List<String>>> Areastr = new ArrayList<>();//区

        for (Province province : provinces) {
            List<String> cityStr = new ArrayList<>();
            List<City> citys = province.getCity();
            List<List<String>> zoneS1 = new ArrayList<>();
            for (City city : citys) {
                cityStr.add(city.getName());
                List<String> zoneS2 = new ArrayList<>();
                for (String zone : city.getArea()) {
                    zoneS2.add(zone);
                }
                zoneS1.add(zoneS2);
            }
            Provincestr.add(province.getName());
            Citystr.add(cityStr);
            Areastr.add(zoneS1);
        }
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(Provincestr, Citystr, Areastr, true);
        pvOptions.setCyclic(false);
//        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                Log.d("onOptionsSelect", options1 + ":" + option2 + ":" + options3);
                Province province = provinces.get(options1);
                String provinceStr = province.getName();
                City city = province.getCity().get(option2);
                String cityStr = city.getName();
                String zone = city.getArea()[options3];
                String us = provinceStr + cityStr + zone;
                mViewBinding.tvWhichZone.setText(us);
            }
        });
        pvOptions.show();
    }

    public String[] getBankNameList() {
        String[] bankNameList = new String[bankList.size()];
        for (int i = 0; i < bankList.size(); i++) {
            bankNameList[i] = bankList.get(i).getBankName();
        }
        return bankNameList;
    }
}
