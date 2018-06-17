package com.cai.work;

import com.cai.work.bean.respond.BankCardRespond;
import com.cai.work.bean.respond.BankListRespond;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.FundDetailRespond;
import com.cai.work.bean.respond.InviteResond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.RechargeBankResond;
import com.cai.work.bean.respond.RedPacketRespond;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.UploadRespond;
import com.cai.work.bean.respond.UserInfoRespond;
import com.cai.work.bean.respond.WithdrawalRespond;

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

    @POST("/app/login/index")
    @FormUrlEncoded
    Flowable<MessageRespond> deleteMessage(@Field("ids") int ids, @Field("token") String token);


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
    Flowable<CommonRespond> forgetPassword(@Field("mobile")String mobile,  @Field("sms")String sms,  @Field("loginPassword")String loginPassword);

    @POST("/app/userSafe/updatePwd")
    @FormUrlEncoded
    Flowable<CommonRespond> resetPassword(@Field("sms")String sms,  @Field("old_pwd")String loginOldPassword, @Field("new_pwd")String loginPassword, @Field("token") String token);

    @POST("/app/userSafe/updateWithdrawPwd")
    @FormUrlEncoded
    Flowable<CommonRespond> resetWithdrawalPassword(@Field("sms")String sms,  @Field("old_pwd")String loginOldPassword, @Field("new_pwd")String loginPassword, @Field("token") String token);

    @GET("/app/rebate")
    Flowable<InviteResond> requestInvite(@Query("token") String token);

}
