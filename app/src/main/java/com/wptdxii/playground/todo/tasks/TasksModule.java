package com.wptdxii.playground.todo.tasks;

import com.wptdxii.playground.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksModule {

    @ContributesAndroidInjector
    abstract TasksFragment tasksFragment();

    @Binds
    @ActivityScoped
    abstract TasksContract.Presenter providePresenter(TasksPresenter tasksPresenter);
}
