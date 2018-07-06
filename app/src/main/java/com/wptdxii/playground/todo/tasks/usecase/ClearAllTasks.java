package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class ClearAllTasks extends UseCase<ClearAllTasks.Reqeust, Void> {

    private final TasksRepository mTasksRepository;

    @Inject
    ClearAllTasks(@NonNull TasksRepository tasksRepository,
                  @NonNull CompositeDisposable compositeDisposable,
                  @NonNull Executor<Void> executor) {
        super(compositeDisposable, executor);
        mTasksRepository = tasksRepository;
    }

    @Override
    public Flowable<Void> buildUseCase(@Nullable ClearAllTasks.Reqeust request) {
        mTasksRepository.deleteAllTasks();
        return Flowable.empty();
    }

    public static final class Reqeust implements UseCase.Request {
    }

}
