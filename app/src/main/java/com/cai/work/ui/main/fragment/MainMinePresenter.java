package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;

import com.cai.framework.base.GodBaseConfig;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.bean.User;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineBottomData;
import com.cai.work.bean.MineListData;
import com.cai.work.bean.MineTopData;
import com.cai.work.common.DataStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainMinePresenter extends GodBasePresenter<MineView> {

    private int[] names = new int[]{
            R.string.mine_menu_title1, R.string.mine_menu_title2, R.string.mine_menu_title3,
            R.string.mine_menu_title4, R.string.mine_menu_title5, R.string.mine_menu_title6,
            R.string.mine_menu_title7, R.string.mine_menu_title8, R.string.mine_menu_title9
    };
    private int[] icons = new int[]{
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
    };

    @Inject
    UserDAO userDAO;
    @Inject
    DataStore dataStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public MainMinePresenter() {

    }

    @SuppressLint("CheckResult")
    public void getMineData() {
        Observable.create(new ObservableOnSubscribe<List<IRecycleViewBaseData>>() {
            @Override
            public void subscribe(ObservableEmitter<List<IRecycleViewBaseData>> observableEmitter) {
                List<IRecycleViewBaseData> dataList = getDatas();
                observableEmitter.onNext(dataList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IRecycleViewBaseData>>() {
                    @Override
                    public void accept(List<IRecycleViewBaseData> dataList) {
                        mView.refreshData(dataList);
                    }
                });
    }

    public List<IRecycleViewBaseData> getDatas() {
        List<IRecycleViewBaseData> dataList = new ArrayList<>();
        User account = userDAO.getData();
        account.setMobile(StringUtils.encryptMobile(account.getMobile()));
        MineTopData topData = new MineTopData();
        topData.setHeadIcon(account.getAvatarUrl());
        topData.setAccount(account.getMobile());
        topData.setMoney(account.getBalance());
        dataList.add(topData);

        MineListData listData;
        for (int i = 0; i < names.length; i++) {
            listData = new MineListData();
            listData.setItemIcon(icons[i]);
            listData.setItemName(names[i]);
            dataList.add(listData);
        }
        MineBottomData bottomData = new MineBottomData();
        dataList.add(bottomData);
        return dataList;
    }

    @Override
    public void onAttached() {

    }

    @SuppressLint("CheckResult")
    public void loginOut() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> result) {
                userDAO.deleteAll();
                accountDAO.deleteAll();
                result.onNext("");
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String dataList) {
                        mView.loginOut();
                    }
                });
    }

    public String getToken() {
        return accountDAO.getToken();
    }

    public String getActivityH5() {
//      http://{domain}/app/h5/activity/index?token=D8BE20C92A1A57B06D58DB28B5CD56FF
        StringBuilder builder = new StringBuilder();
        builder.append(GodBaseConfig.getInstance().getBaseUrl());
        builder.append("/app/h5/activity/index?");
        builder.append("token=").append(getToken());
        return builder.toString();
    }

    public String getCommonQuestionH5() {
//      http://{domain}/app/h5/help/question
        StringBuilder builder = new StringBuilder();
        builder.append(GodBaseConfig.getInstance().getBaseUrl());
        builder.append("/app/h5/help/question");
        return builder.toString();
    }
    public String getAboutUsH5() {
//      http://{domain}/app/h5/cms/see?name=about_us
        StringBuilder builder = new StringBuilder();
        builder.append(GodBaseConfig.getInstance().getBaseUrl());
        builder.append("/app/h5/cms/see?name=about_us");
        return builder.toString();
    }
}
