package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.Executor;
import com.wptdxii.playground.core.UseCase;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.tasks.TasksFilterType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class GetTasks extends UseCase<GetTasks.Request, List<Task>> {

    private final TasksRepository mTasksRepository;

    @Inject
    public GetTasks(@NonNull TasksRepository tasksRepository,
                    @NonNull CompositeDisposable compositeDisposable,
                    @NonNull Executor<List<Task>> executor) {
        super(compositeDisposable, executor);
        mTasksRepository = tasksRepository;
    }

    @Override
    public Flowable<List<Task>> buildUseCase(@Nullable GetTasks.Request request) {
        TasksFilterType filterType = request.getFilterType();
        return mTasksRepository.getTasks()
                .flatMap(Flowable::fromIterable)
                .filter(task -> {
                    switch (filterType) {
                        case COMPLETED_TASKS:
                            return task.isCompleted();
                        case ACTIVE_TASKS:
                            return !task.isCompleted();
                        case ALL_TASKS:
                        default:
                            return true;
                    }
                })
                .toList().toFlowable();
    }

    public static final class Request implements UseCase.Request {
        private final boolean mForceUpdate;
        private final boolean mShowLoading;
        private final TasksFilterType mFilterType;

        public Request(boolean forceUpdate, boolean showLoading, TasksFilterType filterType) {
            mForceUpdate = forceUpdate;
            mShowLoading = showLoading;
            mFilterType = filterType;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public TasksFilterType getFilterType() {
            return mFilterType;
        }
    }
}
