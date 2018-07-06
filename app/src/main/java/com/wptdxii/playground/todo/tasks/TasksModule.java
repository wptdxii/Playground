package com.wptdxii.playground.todo.tasks;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.IOExecutor;
import com.wptdxii.playground.di.module.RxModule;
import com.wptdxii.playground.di.qualifier.AppContext;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.source.Task;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module(includes = RxModule.class)
public abstract class TasksModule {

    @ContributesAndroidInjector(modules = RxModule.class)
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

    @Binds
    @ActivityScoped
    abstract Executor<Void> providExecutor(IOExecutor<Void> ioExecutor);
}
