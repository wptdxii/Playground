package com.wptdxii.playground.todo.tasks;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.IOExecutor;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.source.Task;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksModule {

    @ContributesAndroidInjector
    abstract TasksFragment tasksFragment();

    @Provides
    @ActivityScoped
    static TasksFragment provideTasksFragment() {
        return TasksFragment.newInstance();
    }

    @Binds
    @ActivityScoped
    abstract TasksContract.Presenter providePresenter(TasksPresenter presenter);

    @Binds
    @ActivityScoped
    abstract Executor<List<Task>> provideExecutor(IOExecutor<List<Task>> ioExecutor);
}
