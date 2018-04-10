package com.cai.work.dagger.module;

import android.content.Context;

import com.cai.work.bean.MyObjectBox;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/3/26.
 */
@Module
public class AppModule {

    @Provides
    public BoxStore provideStore(Context context) {
        return MyObjectBox.builder().androidContext(context).build();
    }
}
