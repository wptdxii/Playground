package com.wptdxii.framekit.di;

import android.app.Application;
import android.content.Context;

import com.wptdxii.framekit.di.qualifier.AppContext;
import com.wptdxii.framekit.component.executor.JobExecutor;
import com.wptdxii.framekit.component.executor.PostExecutionThread;
import com.wptdxii.framekit.component.executor.ThreadExecutor;
import com.wptdxii.framekit.component.executor.UIThread;

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
    abstract ThreadExecutor provideExecutor(JobExecutor jobExecutor);

    @Singleton
    @Binds
    abstract PostExecutionThread provideUIThread(UIThread uiThread);
}
