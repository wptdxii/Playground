package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;
import com.wptdxii.playground.core.interactor.CompletableUseCase;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

@ActivityScoped
public class ClearCompletedTasks extends CompletableUseCase<Void> {
    private final TasksRepository mTasksRepository;

    @Inject
    ClearCompletedTasks(@NonNull TasksRepository tasksRepository,
                        @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread executionThread) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Void aVoid) {
        return Completable.fromAction(mTasksRepository::deleteCompletedTasks);
    }
}
