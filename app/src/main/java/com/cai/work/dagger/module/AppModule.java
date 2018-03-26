package com.cai.work.dagger.module;

import com.cai.work.base.App;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/3/26.
 */
@Module
public class AppModule {

    @Provides
    public BoxStore provideUser() {
        return App.getBoxStore();
    }
}
