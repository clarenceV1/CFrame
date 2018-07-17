package com.meetone.work.dagger.component;

import com.cai.framework.dagger.component.FrameWorkComponent;
import com.cai.framework.dagger.module.FrameWorkModule;
import com.meetone.work.base.App;
import com.meetone.work.dagger.module.AppModule;
import com.meetone.work.ui.asset.AssetActivity;
import com.meetone.work.ui.candy.CandyDetailActivity;
import com.meetone.work.ui.debug.DebugActivity;
import com.meetone.work.ui.login.LoginActivity;
import com.meetone.work.ui.main.CandyFragment;
import com.meetone.work.ui.main.DiscoverFragment;
import com.meetone.work.ui.main.MainActivity;
import com.meetone.work.ui.main.MineFragment;
import com.meetone.work.ui.message.MessageActivity;
import com.meetone.work.ui.nationcode.NationCodeActivity;
import com.meetone.work.ui.person.PersonActivity;
import com.meetone.work.ui.record.RecordActivity;
import com.meetone.work.ui.web.WebActivity;
import com.meetone.work.ui.welcome.WelcomeActivity;
import com.meetone.work.ui.welfare.WelfareActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
@Component(modules = {FrameWorkModule.class, AppModule.class})
public interface AppComponent extends FrameWorkComponent {

    void inject(App app);

    void inject(WebActivity webActivity);

    void inject(WelcomeActivity activity);

    void inject(MainActivity activity);

    void inject(CandyFragment fragment);

    void inject(DiscoverFragment fragment);

    void inject(MineFragment presenter);

    void inject(CandyDetailActivity activity);

    void inject(LoginActivity activity);

    void inject(MessageActivity activity);

    void inject(PersonActivity activity);

    void inject(NationCodeActivity activity);

    void inject(RecordActivity activity);

    void inject(WelfareActivity activity);

    void inject(AssetActivity activity);

    void inject(DebugActivity activity);

}
