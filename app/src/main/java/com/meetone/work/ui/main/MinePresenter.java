package com.meetone.work.ui.main;

import android.text.TextUtils;

import com.meetone.work.R;
import com.example.clarence.utillibrary.StringUtils;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.Invite;
import com.meetone.work.bean.MineInviteModel;
import com.meetone.work.bean.MineModel;
import com.meetone.work.bean.User;
import com.meetone.work.bean.respond.MineRespond;
import com.meetone.work.qrcode.QRCodeCreat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends AppBasePresenter<MineView> {
    @Inject
    public MinePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public String getShareText() {
        String url = dataStore.get().getInviteUrl();
        String title = dataStore.get().getInviteTitle();
        if (StringUtils.isEmpty(url)) {
            return null;
        } else {
            String shareText = "More.one";
            if (!TextUtils.isEmpty(title)) {
                shareText = title + url;
            } else {
                shareText = context.getString(R.string.share_text) + url;
            }
            return shareText;
        }

    }

    public String copyShareText() {
        String url = dataStore.get().getInviteUrl();
        String title = dataStore.get().getInviteTitle();
        if (StringUtils.isEmpty(url)) {
            return null;
        } else {
            if (!TextUtils.isEmpty(title)) {
                return title + url;
            } else {
                return context.getString(R.string.share_text) + url;
            }
        }
    }

    public void getUserInfo() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) {
                User user = userDAO.get().getUser();
                if (user == null) {
                    user = new User();
                }
                e.onNext(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        if (!TextUtils.isEmpty(user.getPhone())) {
                            mView.updateUserInfo(user);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public List<Invite> getDefaultData(int... num) {
        ArrayList<Invite> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Invite invite = new Invite();
            invite.setName("P" + (i + 1));
            if (num.length > i) {
                invite.setNum(num[i]);
            } else {
                invite.setNum(0);
            }
            datas.add(invite);
        }
        return datas;
    }

    public void loadMineData(boolean isloadCache) {
        if (isloadCache) {
            getMineDataOfCache();
        }
        getMineDataOfNet();
    }

    public void getMineDataOfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Map<String, MineModel>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, MineModel>> e) {
                MineModel mineModel = cacheStore.get().getMineData();
                if (mineModel != null && mineModel.getInvite() != null) {
                    MineInviteModel inviteModel = mineModel.getInvite();
                    mineModel.setInviteList(getDefaultData(inviteModel.getM1(),
                            inviteModel.getM2(),
                            inviteModel.getM3(),
                            inviteModel.getM4(),
                            inviteModel.getM5(),
                            inviteModel.getM6()
                    ));
                    mineModel.setInviteTotal(getInviteTotal(inviteModel));
                }
                Map<String, MineModel> map = new HashMap<>();
                map.put("MineModel", mineModel);
                e.onNext(map);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, MineModel>>() {
                    @Override
                    public void accept(Map<String, MineModel> map) {
                        if (map.get("MineModel") != null) {
                            mView.updataMineData(map.get("MineModel"));
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void getMineDataOfNet() {
        Disposable disposable = requestStore.get().loadMineData().doOnNext(new Consumer<MineRespond>() {
            @Override
            public void accept(MineRespond data) {
                MineModel mineModel = data.getData();
                if (mineModel != null) {
                    User user = mineModel.getUser();
                    if (user != null) {
                        userDAO.get().update(user.getUser_id(),
                                user.getAvatar(),
                                user.getNation_code(),
                                user.getNickname(),
                                user.getPhone());
                    }
                    if (mineModel.getInvite() != null) {
                        MineInviteModel inviteModel = mineModel.getInvite();
                        mineModel.setInviteList(getDefaultData(inviteModel.getM1(),
                                inviteModel.getM2(),
                                inviteModel.getM3(),
                                inviteModel.getM4(),
                                inviteModel.getM5(),
                                inviteModel.getM6()
                        ));
                        mineModel.setInviteTotal(getInviteTotal(inviteModel));
                    }
                    cacheStore.get().saveMineData(mineModel);
                    dataStore.get().saveInviteTitle(mineModel.getInvitetitle());
                    dataStore.get().saveInviteUrl(mineModel.getInviteurl());
                    QRCodeCreat.createQRcode(context, fileStore.get().getQRcodeFile(1), mineModel.getInviteurl());
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MineRespond>() {
                    @Override
                    public void accept(MineRespond mineRespond) {
                        if (mineRespond.getData() != null) {
                            mView.updataMineData(mineRespond.getData());
                        } else {
                            mView.toast(mineRespond.getMessage());
                            mView.updataMineData(new MineModel());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.toast(throwable.getMessage());
                        mView.updataMineData(new MineModel());
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    private int getInviteTotal(MineInviteModel invite) {
        int people = invite.getM1()
                + invite.getM2()
                + invite.getM3()
                + invite.getM4()
                + invite.getM5()
                + invite.getM6();
        return people;
    }

    public boolean isLogin() {
        return userDAO.get().isLogin();
    }
}
