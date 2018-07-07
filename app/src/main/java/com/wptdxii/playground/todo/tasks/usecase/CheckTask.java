package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;
import com.wptdxii.playground.core.interactor.CompletableUseCase;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

@ActivityScoped
public class CheckTask extends CompletableUseCase<CheckTask.Request> {
    private final TasksRepository mTasksRepository;

    @Inject
    CheckTask(@NonNull TasksRepository tasksRepository,
              @NonNull ThreadExecutor threadExecutor,
              @NonNull PostExecutionThread executionThread) {
        super(threadExecutor, executionThread);

        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Request request) {
        return Completable.fromAction(() -> mTasksRepository.updateTask(request.getTask()));
    }

    public static final class Request {
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
