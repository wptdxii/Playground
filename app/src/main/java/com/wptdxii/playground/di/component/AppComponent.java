package com.wptdxii.playground.di.component;

import android.app.Application;

import com.wptdxii.playground.App;
import com.wptdxii.playground.di.module.AppModule;
import com.wptdxii.playground.douban.di.DouBanBindingModule;
import com.wptdxii.playground.gank.di.GankBindingModule;
import com.wptdxii.playground.home.di.HomeBindingModule;
import com.wptdxii.playground.sample.di.SampleBindingModule;
import com.wptdxii.playground.todo.di.ToDoBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class,
        DouBanBindingModule.class,
        GankBindingModule.class,
        HomeBindingModule.class,
        SampleBindingModule.class,
        ToDoBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
