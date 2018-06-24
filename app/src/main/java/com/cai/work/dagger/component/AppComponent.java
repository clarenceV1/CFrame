package com.cai.work.dagger.component;

import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.bank.AddBankCardActivity;
import com.cai.work.ui.bank.AddBankCardPresenter;
import com.cai.work.ui.bank.BankCardChooseActivity;
import com.cai.work.ui.bank.BankCardChoosePresenter;
import com.cai.work.ui.bank.BankCardListActivity;
import com.cai.work.ui.bank.BankCardListPresenter;
import com.cai.work.ui.forward.ForwardActivity;
import com.cai.work.ui.forward.ForwardPresenter;
import com.cai.work.ui.fund.FundDetailActivity;
import com.cai.work.ui.fund.FundDetailPresenter;
import com.cai.work.ui.invite.InviteActivity;
import com.cai.work.ui.invite.InvitePresenter;
import com.cai.work.ui.invite.InviteWayActivity;
import com.cai.work.ui.invite.InviteWayPresenter;
import com.cai.work.ui.invite.MyInviteActivity;
import com.cai.work.ui.invite.MyInvitePresenter;
import com.cai.work.ui.listview.ListviewScollActivity;
import com.cai.work.ui.login.ForgetPasswordActivity;
import com.cai.work.ui.login.ForgetPasswordPresenter;
import com.cai.work.ui.login.LoginActivity;
import com.cai.work.ui.login.LoginPresenter;
import com.cai.work.ui.login.RegisterActivity;
import com.cai.work.ui.login.RegisterPresenter;
import com.cai.work.ui.login.ResetPasswordActivity;
import com.cai.work.ui.login.ResetPasswordPresenter;
import com.cai.work.ui.login.SaveActivity;
import com.cai.work.ui.login.SavePresenter;
import com.cai.work.ui.main.MainActivity;
import com.cai.work.ui.main.MainPresenter;
import com.cai.work.ui.main.fragment.MainHoldFragment;
import com.cai.work.ui.main.fragment.MainHoldPresenter;
import com.cai.work.ui.main.fragment.MainHomeFragment;
import com.cai.work.ui.main.fragment.MainMineFragment;
import com.cai.work.ui.main.fragment.MainServiceFragment;
import com.cai.work.ui.main.fragment.MainServicePresenter;
import com.cai.work.ui.main.fragment.MainTradeFragment;
import com.cai.work.ui.main.fragment.MainTradePresenter;
import com.cai.work.ui.message.MessageActivity;
import com.cai.work.ui.message.MessagePresenter;
import com.cai.work.ui.news.NewsActivity;
import com.cai.work.ui.news.NewsPresenter;
import com.cai.work.ui.rank.RankActivity;
import com.cai.work.ui.rank.RankPresenter;
import com.cai.work.ui.rebate.RebateActivity;
import com.cai.work.ui.rebate.RebatePresenter;
import com.cai.work.ui.recharge.RechargeActivity;
import com.cai.work.ui.recharge.RechargePresenter;
import com.cai.work.ui.recharge.RechargeUnderLineActivity;
import com.cai.work.ui.recharge.RechargeUnderLinePresenter;
import com.cai.work.ui.redPacket.RedPacketActivity;
import com.cai.work.ui.redPacket.RedPacketPresenter;
import com.cai.work.ui.trade.TradeDetailActivity;
import com.cai.work.ui.trade.TradeDetailPresenter;
import com.cai.work.ui.web.WebActivity;
import com.cai.work.ui.welcome.WelcomeActivity;
import com.cai.work.ui.welcome.WelcomePresenter;
import com.cai.work.ui.withdrawal.WithdrawalActivity;
import com.cai.work.ui.withdrawal.WithdrawalDetailActivity;
import com.cai.work.ui.withdrawal.WithdrawalDetailPresenter;
import com.cai.work.ui.withdrawal.WithdrawalPasswordActivity;
import com.cai.work.ui.withdrawal.WithdrawalPasswordPresenter;
import com.cai.work.ui.withdrawal.WithdrawalPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
@Component(modules = {AppModule.class, FrameWorkModule.class})
public interface AppComponent {
    void inject(WebActivity webActivity);

    void inject(ListviewScollActivity listviewScollActivity);

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(MainMineFragment mainMineFragment);

    void inject(MainHomeFragment mainHomeFragment);

    void inject(WelcomeActivity welcomeActivity);

    void inject(WelcomePresenter welcomePresenter);

    void inject(FundDetailActivity fundDetailActivity);

    void inject(FundDetailPresenter fundDetailPresenter);

    void inject(BankCardListActivity bankActivity);

    void inject(BankCardListPresenter bankPresenter);

    void inject(AddBankCardActivity bankAddActivity);

    void inject(AddBankCardPresenter bankAddPresenter);

    void inject(MessageActivity messageActivity);

    void inject(MessagePresenter messagePresenter);

    void inject(RedPacketActivity redPacketActivity);

    void inject(RedPacketPresenter redPacketPresenter);

    void inject(LoginActivity loginActivity);

    void inject(LoginPresenter loginPresenter);

    void inject(ForgetPasswordActivity forgetPasswordActivity);

    void inject(ForgetPasswordPresenter forgetPasswordPresenter);

    void inject(RankActivity rankActivity);

    void inject(RankPresenter rankPresenter);

    void inject(SaveActivity saveActivity);

    void inject(SavePresenter savePresenter);

    void inject(RechargeActivity rechargeActivity);

    void inject(RechargePresenter rechargePresenter);

    void inject(RechargeUnderLineActivity rechargeUnderLineActivity);

    void inject(RechargeUnderLinePresenter rechargeUnderLinePresenter);

    void inject(WithdrawalActivity withdrawalActivity);

    void inject(WithdrawalPresenter withdrawalPresenter);

    void inject(BankCardChooseActivity bankCardChooseActivity);

    void inject(BankCardChoosePresenter bankCardChoosePresenter);

    void inject(RegisterActivity registerActivity);

    void inject(RegisterPresenter registerPresenter);

    void inject(ResetPasswordActivity resetPasswordActivity);

    void inject(ResetPasswordPresenter resetPasswordPresenter);

    void inject(WithdrawalPasswordActivity withdrawalPasswordActivity);

    void inject(WithdrawalPasswordPresenter withdrawalPasswordPresenter);

    void inject(InviteActivity inviteActivity);

    void inject(InvitePresenter invitePresenter);

    void inject(MyInviteActivity myInviteActivity);

    void inject(MyInvitePresenter myInvitePresenter);

    void inject(InviteWayActivity inviteWayActivity);

    void inject(InviteWayPresenter inviteWayPresenter);

    void inject(WithdrawalDetailActivity withdrawalDetailActivity);

    void inject(WithdrawalDetailPresenter withdrawalDetailPresenter);

    void inject(MainTradeFragment mainTradeFragment);

    void inject(MainTradePresenter mainTradePresenter);

    void inject(MainServiceFragment mainServiceFragment);

    void inject(MainServicePresenter mainServicePresenter);

    void inject(RebateActivity rebateActivity);

    void inject(RebatePresenter rebatePresenter);

    void inject(TradeDetailActivity tradeDetailActivity);

    void inject(TradeDetailPresenter tradeDetailPresenter);

    void inject(MainHoldFragment fragment);

    void inject(MainHoldPresenter presenter);

    void inject(NewsActivity activity);

    void inject(NewsPresenter presenter);

    void inject(ForwardActivity activity);

    void inject(ForwardPresenter presenter);
}
