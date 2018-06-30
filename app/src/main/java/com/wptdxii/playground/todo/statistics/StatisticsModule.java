package com.wptdxii.playground.todo.statistics;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.statistics.StatisticsContract;
import com.wptdxii.playground.todo.statistics.StatisticsFragment;
import com.wptdxii.playground.todo.statistics.StatisticsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StatisticsModule {

    @ContributesAndroidInjector
    abstract StatisticsFragment statisticsFragment();

    @ActivityScoped
    @Binds
    abstract StatisticsContract.Presenter providePresenter(StatisticsPresenter presenter);
}
