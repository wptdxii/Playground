package com.wptdxii.playground.todo.statistics;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.di.scope.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StatisticsModule {

    //    @ContributesAndroidInjector(modules = StatisticsFragmentModule.class)


//    @FragmentScoped
    @ContributesAndroidInjector
    abstract StatisticsFragment statisticsFragment();

    @ActivityScoped
    @Binds
    abstract StatisticsContract.Presenter providePresenter(StatisticsPresenter presenter);
}
