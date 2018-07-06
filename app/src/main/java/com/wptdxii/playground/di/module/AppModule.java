package com.wptdxii.playground.di.module;

import android.app.Application;
import android.content.Context;

import com.wptdxii.playground.core.schedulers.ISchedulerProvider;
import com.wptdxii.playground.core.schedulers.SchedulerProvider;
import com.wptdxii.playground.di.qualifier.AppContext;
import com.wptdxii.playground.home.di.HomeBindingModule;
import com.wptdxii.playground.sample.di.SampleBindingModule;
import com.wptdxii.playground.todo.data.di.module.TasksRepositoryModule;
import com.wptdxii.playground.todo.di.ToDoBindingModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @AppContext
    @Binds
    abstract Context provideContext(Application application);

    @Singleton
    @Binds
    abstract ISchedulerProvider provideSchedulerProvider(SchedulerProvider provider);
}
