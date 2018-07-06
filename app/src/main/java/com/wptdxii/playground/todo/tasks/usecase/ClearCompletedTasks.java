package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class ClearCompletedTasks extends UseCase<ClearCompletedTasks.Request, Void> {
    private final TasksRepository mTasksRepository;

    @Inject
    ClearCompletedTasks(@NonNull TasksRepository tasksRepository,
                               @NonNull CompositeDisposable compositeDisposable,
                               @NonNull Executor<Void> executor) {
        super(compositeDisposable, executor);
        mTasksRepository = tasksRepository;
    }

    @Override
    public Flowable<Void> buildUseCase(@Nullable ClearCompletedTasks.Request request) {
        mTasksRepository.deleteCompletedTasks();
        return Flowable.empty();
    }

    public static final class Request implements UseCase.Request {
    }

}
