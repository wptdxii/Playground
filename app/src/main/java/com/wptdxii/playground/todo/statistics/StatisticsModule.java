package com.wptdxii.playground.todo.statistics;

import com.wptdxii.playground.di.module.RxModule;
import com.wptdxii.playground.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = RxModule.class)
public abstract class StatisticsModule {

    @ContributesAndroidInjector
    abstract StatisticsFragment statisticsFragment();

    @ActivityScoped
    @Binds
    abstract StatisticsContract.Presenter providePresenter(StatisticsPresenter presenter);
}
