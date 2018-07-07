package com.cai.work;

import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.News;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.StockHistory;
import com.cai.work.bean.respond.BankCardRespond;
import com.cai.work.bean.respond.BankListRespond;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.ForwardAccountRespond;
import com.cai.work.bean.respond.ForwardBuyRespond;
import com.cai.work.bean.respond.ForwardContractsRespond;
import com.cai.work.bean.respond.FundDetailRespond;
import com.cai.work.bean.respond.InviteResond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.RechargeBankResond;
import com.cai.work.bean.respond.RedPacketRespond;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.ServiceRespond;
import com.cai.work.bean.respond.StockAccountRespond;
import com.cai.work.bean.respond.StockBuyRespond;
import com.cai.work.bean.respond.StockHQRespond;
import com.cai.work.bean.respond.StockHoldRespond;
import com.cai.work.bean.respond.StockListRespond;
import com.cai.work.bean.respond.StockTradeRespond;
import com.cai.work.bean.respond.TradeRespond;
import com.cai.work.bean.respond.UploadRespond;
import com.cai.work.bean.respond.UserInfoRespond;
import com.cai.work.bean.respond.RebateRespond;
import com.cai.work.bean.respond.WithdrawalDetailRespond;
import com.cai.work.bean.respond.WithdrawalRespond;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("/app/index")
    Flowable<HomeRespond> getHomeData();

    @POST("/app/login/index")
    @FormUrlEncoded
    Flowable<LoginRespond> requestLogin(@Field("username") String username, @Field("password") String password);

    @GET("/app/member/index")
    Flowable<UserInfoRespond> getUserInfo(@Query("token") String token);

    @GET("/app/journal/index")
    Flowable<FundDetailRespond> requestFundDetail(@Query("page") int page, @Query("token") String token);

    @GET("/app/bankCard")
    Flowable<BankCardRespond> requestBankList(@Query("token") String token);

    @POST("/app/bankCard/addCard")
    @FormUrlEncoded
    Flowable<BaseRespond> requestAddBankCard(@Field("realname") String realname, @Field("cardNo") String cardNo, @Field("bankId") String bankId, @Field("branchName") String branchName, @Field("token") String token);

    @GET("/app/bankCard/getBanks")
    Flowable<BankListRespond> getBankList(@Query("token") String token);

    @GET("/app/message/index")
    Flowable<MessageRespond> getMessage(@Query("page") int page, @Query("token") String token);

    @POST("/app/message/delete")
    @FormUrlEncoded
    Flowable<MessageRespond> deleteMessage(@Field("ids") int ids, @Field("token") String token);

    @POST("/app/message/deleteAll")
    @FormUrlEncoded
    Flowable<BaseRespond> deleteMessageAll(@Field("token") String token);

    @GET("/app/redBags/index")
    Flowable<RedPacketRespond> getRedPacket(@Query("page") int page, @Query("token") String token);

    @POST("/app/upload/index")
    @FormUrlEncoded
    Flowable<UploadRespond> uploadUserHeadImg(@Field("token") String token, @Field("t") String t, @Field("data") String data);

    @GET("/app/recharge/getOffline")
    Flowable<RechargeBankResond> requestRechargeBankList(@Query("token") String token);

    @POST("/app/recharge/offlineSave")
    @FormUrlEncoded
    Flowable<BaseRespond> commitPay(@Field("offlineName") String offlineName, @Field("amount") String amount,
                                    @Field("offlineId") int offlineId, @Field("offlineAccount") String offlineAccount,
                                    @Field("offlineImageUrl") String offlineImageUrl, @Field("token") String token);

    @GET("/app/withdraw/getData")
    Flowable<WithdrawalRespond> requestWithdrawal(@Query("token") String token);

    @POST("/app/recharge/offlineSave")
    @FormUrlEncoded
    Flowable<BaseRespond> commitWithdrawal(@Field("cardId") int cardId, @Field("amount") String amount,
                                           @Field("password") String password, @Field("withdrawKind") int withdrawKind,
                                           @Field("token") String token);

    @POST("/app/register")
    @FormUrlEncoded
    Flowable<CommonRespond> requestRegister(@Field("mobile") String mobile, @Field("sms") String sms, @Field("loginPassword") String loginPassword, @Field("invitationCode") String invitationCode);

    @GET("/app/send/get_code")
    Flowable<CommonRespond> requestIdentifyCode(@Query("mobile") String mobile, @Query("type") int type);

    @POST("/app/resetpwd")
    @FormUrlEncoded
    Flowable<CommonRespond> forgetPassword(@Field("mobile") String mobile, @Field("sms") String sms, @Field("loginPassword") String loginPassword);

    @POST("/app/userSafe/updatePwd")
    @FormUrlEncoded
    Flowable<CommonRespond> resetPassword(@Field("sms") String sms, @Field("old_pwd") String loginOldPassword, @Field("new_pwd") String loginPassword, @Field("token") String token);

    @POST("/app/userSafe/updateWithdrawPwd")
    @FormUrlEncoded
    Flowable<CommonRespond> resetWithdrawalPassword(@Field("sms") String sms, @Field("old_pwd") String loginOldPassword, @Field("new_pwd") String loginPassword, @Field("token") String token);

    @GET("/app/rebate")
    Flowable<InviteResond> requestInvite(@Query("token") String token);

    @GET("/app/ask/index")
    Flowable<ServiceRespond> requestAskData();

    @GET("/app/rebate/getRebate")
    Flowable<RebateRespond> requestRebate(@Query("token") String token);

    @POST("/app/rebate/withdrawRebate")
    @FormUrlEncoded
    Flowable<CommonRespond> requestWithdrawRebate(@Field("rebateIds") String rebateIds, @Field("token") String token);


    @GET("/app/rebate/getRebateWithdraw")
    Flowable<WithdrawalDetailRespond> getRebateWithdraw(@Query("page") int page, @Query("token") String token);

    @GET("/app/trade")
    Flowable<TradeRespond> requestTradeData(@Query("token") String token);


    @GET("/app/trade/getSocketAddr")
    Flowable<SocketInfo> requestSocketInfo(@Query("token") String token);


    @GET("/app/stockTrade/sell")
    Flowable<StockHoldRespond> requestRealStockHold(@Query("token") String token);


    @GET("/app/mnstockTrade/sell")
    Flowable<StockHoldRespond> requestFakeStockHold(@Query("token") String token);

    @GET("/app/stockTrade/finish")
    Flowable<StockAccountRespond> requestRealStockAccounts(@Query("token") String token);

    @GET("/app/mnstockTrade/finish")
    Flowable<StockAccountRespond> requestFakeStockAccounts(@Query("token") String token);

    @GET("/app/futures/settlement")
    Flowable<ForwardAccountRespond> requestRealForwardAccounts(@Query("page") int page, @Query("token") String token);

    @GET("/app/futuresVirtual/settlement")
    Flowable<ForwardAccountRespond> requestFakeForwardAccounts(@Query("page") int page, @Query("token") String token);

    @GET("/app/news/queryPage")
    Flowable<List<News>> requestNews(@Query("page") int page);

    @GET("/app/futures/get_code")
    Flowable<ForwardRecord> requestRecord(@Query("token") String token, @Query("code") String code);

    @GET("/app/futures/get_contracts")
    Flowable<ForwardContractsRespond> requestContracts(@Query("token") String token);

    @GET("/app/stockTrade")
    Flowable<StockTradeRespond> requestStockTrade(@Query("token") String token);

    @GET("/app/stock/getHq")
    Flowable<StockHQRespond> requestStockHq(@Query("code") String code);

    @GET("/app/stock/getStock")
    Flowable<StockListRespond> requestStockList(@Query("code") String code);

    @GET("/app/stock/getHistory")
    Flowable<String[][]> requestStockHistory(@Query("code") String code);

    @GET("/app/stockTrade/checkInfo")
    Flowable<StockBuyRespond> requestStockHistory(@Query("code") String code, @Query("token") String token);

    @POST("/app/stockTrade/checkBuy")
    @FormUrlEncoded
    Flowable<CommonRespond> commitStockBuy(@Field("token") String token, @Field("code") String code, @Field("name") String name,
                                           @Field("marketType") String marketType, @Field("price") String price, @Field("amount") String amount,
                                           @Field("principal") String principal, @Field("bzj") String bzj, @Field("zy") String zy,
                                           @Field("zs") String zs, @Field("redbagIds") String redbagIds, @Field("zhf") String zhf);

    @GET("/app/futures/futures_info")
    Flowable<ForwardBuyRespond> requestForwardBuy(@Query("code") String code, @Query("token") String token);

    @POST("/app/futures/close")
    @FormUrlEncoded
    Flowable<CommonRespond> realPingCang(@Field("token") String token, @Field("id") String id, @Field("code") String code);

    @POST("/app/futuresVirtual/open")
    @FormUrlEncoded
    Flowable<CommonRespond> moniPingCang(@Field("token") String token, @Field("id") String id, @Field("code") String code);

    @POST("/app/futures/oneKeyBackHand")
    @FormUrlEncoded
    Flowable<CommonRespond> moniFanshou(@Field("token") String token, @Field("id") String id, @Field("code") String code);


    @POST("/app/futuresVirtual/oneKeyBackHand")
    @FormUrlEncoded
    Flowable<CommonRespond> realFanshou(@Field("token") String token, @Field("id") String id, @Field("code") String code);

    @GET("http://114.55.224.60:8081/web_new.php")
    Flowable<String[][]> requestMinData(@Query("symbol") String code, @Query("resolution") String resolution);

}
