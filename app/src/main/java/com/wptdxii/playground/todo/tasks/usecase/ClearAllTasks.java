package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;
import com.wptdxii.playground.core.interactor.CompletableUseCase;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScoped
public class ClearAllTasks extends CompletableUseCase<Void> {

    private final TasksRepository mTasksRepository;

    @Inject
    ClearAllTasks(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
                  @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Void aVoid) {
        return Completable.fromAction(mTasksRepository::deleteAllTasks);
    }
}
