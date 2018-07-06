package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class CheckTask extends UseCase<CheckTask.Request, Void> {
    private final TasksRepository mTasksRepository;

    @Inject
    CheckTask(@NonNull TasksRepository tasksRepository,
              @NonNull CompositeDisposable compositeDisposable,
              @NonNull Executor<Void> executor) {
        super(compositeDisposable, executor);
        mTasksRepository = tasksRepository;
    }

    @Override
    public Flowable<Void> buildUseCase(@Nullable CheckTask.Request request) {
        mTasksRepository.updateTask(request.getTask());
        return Flowable.empty();
    }

    public static final class Request implements UseCase.Request {
        private final Task mTask;

        public Request(@NonNull Task task) {
            mTask = task;
        }

        @NonNull
        public Task getTask() {
            return mTask;
        }
    }
}
