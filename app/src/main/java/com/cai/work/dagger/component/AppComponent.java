package com.cai.work.dagger.component;

import com.cai.framework.dagger.component.FrameWorkComponent;
import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.base.App;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.candy.CandyDetailActivity;
import com.cai.work.ui.login.LoginActivity;
import com.cai.work.ui.main.CandyFragment;
import com.cai.work.ui.main.DiscoverFragment;
import com.cai.work.ui.main.MainActivity;
import com.cai.work.ui.main.MineFragment;
import com.cai.work.ui.message.MessageActivity;
import com.cai.work.ui.nationcode.NationCodeActivity;
import com.cai.work.ui.person.PersonActivity;
import com.cai.work.ui.record.RecordActivity;
import com.cai.work.ui.web.WebActivity;
import com.cai.work.ui.welcome.WelcomeActivity;

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
}
