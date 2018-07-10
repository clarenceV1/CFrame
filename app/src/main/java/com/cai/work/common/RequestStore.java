package com.cai.work.common;

import com.cai.work.ApiService;
import com.cai.work.bean.respond.WithdrawalRespond;
import com.example.clarence.netlibrary.INet;

import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/3/26.
 */

public class RequestStore {
    @Inject
    INet iNet;

    @Inject
    public RequestStore() {
    }

    public Disposable requestHomeData(Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getHomeData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestLogin(String username, String password, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestLogin(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestUserInfo(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getUserInfo(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestFundDetail(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestFundDetail(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestBankList(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestBankList(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }


    public Disposable requestAddBankCard(String realname, String cardNo, String bankId, String branchName, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestAddBankCard(realname, cardNo, bankId, branchName, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable getBankList(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getBankList(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }


    public Disposable getMessage(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getMessage(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable deleteMessage(int ids, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).deleteMessage(ids, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable deleteMessageAll(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).deleteMessageAll(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable getRedPacket(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getRedPacket(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable uploadUserHeadImg(String token, String type, String data, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).uploadUserHeadImg(token, type, data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRechargeBankList(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRechargeBankList(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable commitPay(String offlineName, String amount, int offlineId, String offlineAccount, String offlineImageUrl, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).commitPay(offlineName, amount, offlineId, offlineAccount, offlineImageUrl, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestWithdrawal(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestWithdrawal(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable commitWithdrawal(int cardId, String amount, String password, int withdrawKind, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).commitWithdrawal(cardId, amount, password, withdrawKind, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRegister(String mobile, String sms, String loginPassword, String invitationCode, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRegister(mobile, sms, loginPassword, invitationCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestIdentifyCode(String mobile, int type, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestIdentifyCode(mobile, type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable forgetPassword(String mobile, String sms, String loginPassword, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).forgetPassword(mobile, sms, loginPassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable resetPassword(String mobile, String sms, String loginOldPassword, String loginPassword, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).resetPassword(sms, loginOldPassword, loginPassword, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable resetWithdrawalPassword(String mobile, String sms, String loginOldPassword, String loginPassword, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).resetWithdrawalPassword(sms, loginOldPassword, loginPassword, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestInvite(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestInvite(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestAskData(Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestAskData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRebate(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRebate(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestWithdrawRebate(String ids, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestWithdrawRebate(ids, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable getRebateWithdraw(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getRebateWithdraw(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestTradeData(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestTradeData(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestSocketInfo(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestSocketInfo(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRealStockHold(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRealStockHold(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestFakeStockHold(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestFakeStockHold(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRealStockAccounts(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRealStockAccounts(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestFakeStockAccounts(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestFakeStockAccounts(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRealForwardAccounts(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRealForwardAccounts(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestFakeForwardAccounts(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestFakeForwardAccounts(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestNews(int page, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestNews(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestRecord(String token, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestRecord(token, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestContracts(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestContracts(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestStockTrade(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestStockTrade(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestStockHq(String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestStockHq(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestStockList(String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestStockList(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestStockHistory(String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestStockHistory(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }


    public Disposable requestStockBuy(String code, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestStockHistory(code, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable commitStockBuy(String token, String code, String name, String marketType, String price, String amount, String principal,
                                     String bzj, String zy, String zs, String redbagIds, String zhf, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).commitStockBuy(token, code, name, marketType, price, amount, principal, bzj, zy, zs
                , redbagIds, zhf)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestForwardBuy(String token, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestForwardBuy(code, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable realPingCang(String token, String id, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).realPingCang(token, id, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable moniPingCang(String token, String id, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).moniPingCang(token, id, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable moniFanshou(String token, String id, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).moniFanshou(token, id, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable realFanshou(String token, String id, String code, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).realFanshou(token, id, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestMinData(String code, String resolution,Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestMinData(code, resolution)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestKaiCang(String token, String code,String amount,String bond,String zy,String zs,String redbagIds,String openWay, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestKaiCang(token, code, amount, bond, zy, zs, redbagIds, openWay)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }
}
