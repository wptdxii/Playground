package com.wptdxii.playground.todo.tasks;

import com.wptdxii.playground.di.module.RxModule;
import com.wptdxii.playground.di.scope.ActivityScoped;

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
}
