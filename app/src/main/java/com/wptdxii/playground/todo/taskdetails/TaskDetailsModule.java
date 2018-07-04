package com.wptdxii.playground.todo.taskdetails;

import com.wptdxii.playground.di.module.RxModule;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsActivity;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsContract;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = RxModule.class)
public abstract class TaskDetailsModule {

    @Binds
    @ActivityScoped
    abstract TaskDetailsContract.Presenter providePresenter(TaskDetailsPresenter presenter);

    @Provides
    @ActivityScoped
    static String provideTaskId(TaskDetailsActivity activity) {
        return activity.getIntent().getStringExtra(TaskDetailsActivity.EXTRA_TASK_ID);
    }
}
