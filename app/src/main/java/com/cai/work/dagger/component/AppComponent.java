package com.cai.work.dagger.component;

import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.bank.BankListActivity;
import com.cai.work.ui.bank.BankListPresenter;
import com.cai.work.ui.fund.FundDetailActivity;
import com.cai.work.ui.fund.FundDetailPresenter;
import com.cai.work.ui.listview.ListviewScollActivity;
import com.cai.work.ui.main.MainActivity;
import com.cai.work.ui.main.MainPresenter;
import com.cai.work.ui.main.fragment.MainHomeFragment;
import com.cai.work.ui.main.fragment.MainMineFragment;
import com.cai.work.ui.web.WebActivity;
import com.cai.work.ui.welcome.WelcomeActivity;
import com.cai.work.ui.welcome.WelcomePresenter;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
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

    void inject(BankListActivity bankActivity);

    void inject(BankListPresenter bankPresenter);

}
