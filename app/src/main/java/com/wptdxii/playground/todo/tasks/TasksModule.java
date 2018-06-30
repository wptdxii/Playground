package com.wptdxii.playground.todo.tasks;

import android.support.v4.app.Fragment;

import com.wptdxii.playground.di.scope.ActivityScoped;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksModule {

    @Provides
    @ActivityScoped
    static Fragment provideTasksFragment() {
        return TasksFragment.newInstance();
    }
}
