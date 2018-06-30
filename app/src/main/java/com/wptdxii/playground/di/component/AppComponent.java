package com.wptdxii.playground.di.component;

import android.app.Application;
import android.content.Context;

import com.wptdxii.playground.App;
import com.wptdxii.playground.di.module.AppModule;
import com.wptdxii.playground.di.qualifier.AppContext;
import com.wptdxii.playground.main.di.MainBindingModule;
import com.wptdxii.playground.sample.di.SampleBindingModule;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.di.module.TasksRepositoryModule;
import com.wptdxii.playground.todo.di.ToDoBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class,
        TasksRepositoryModule.class,
        MainBindingModule.class,
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
