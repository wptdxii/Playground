package com.wptdxii.playground.todo.addedittask;

import android.support.annotation.Nullable;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.addedittask.AddEditActivity;
import com.wptdxii.playground.todo.addedittask.AddEditContract;
import com.wptdxii.playground.todo.addedittask.AddEditPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AddEditModule {

    @ActivityScoped
    @Provides
    @Nullable
    static String provideTaskId(AddEditActivity addEditActivity) {
        return addEditActivity.getIntent().getStringExtra(AddEditActivity.EXTRA_TASK_ID);
    }

    @ActivityScoped
    @Binds
    abstract AddEditContract.Presenter providePresenter(AddEditPresenter presenter);
}
