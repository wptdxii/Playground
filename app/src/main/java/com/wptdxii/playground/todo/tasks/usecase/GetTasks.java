package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;
import com.wptdxii.playground.core.interactor.FlowableUseCase;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.tasks.TasksFilterType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

@ActivityScoped
public class GetTasks extends FlowableUseCase<GetTasks.Request, List<Task>> {

    private final TasksRepository mTasksRepository;

    @Inject
    GetTasks(@NonNull TasksRepository tasksRepository,
             @NonNull ThreadExecutor threadExecutor,
             @NonNull PostExecutionThread executionThread) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Flowable<List<Task>> buildUseCase(Request request) {
        TasksFilterType filterType = request.getFilterType();
        if (request.isForceUpdate()) {
            mTasksRepository.refreshTasks();
        }

        return mTasksRepository.getTasks()
                .flatMap(Flowable::fromIterable)
                .filter(task -> filterTasks(filterType, task))
                .toList()
                .toFlowable();
    }

    private boolean filterTasks(TasksFilterType filterType, Task task) {
        switch (filterType) {
            case ACTIVE_TASKS:
                return !task.isCompleted();
            case COMPLETED_TASKS:
                return task.isCompleted();
            case ALL_TASKS:
            default:
                return true;
        }
    }

    public static final class Request {
        private final boolean mForceUpdate;
        private final TasksFilterType mFilterType;

        public Request(boolean forceUpdate, TasksFilterType filterType) {
            mForceUpdate = forceUpdate;
            mFilterType = filterType;
        }

        boolean isForceUpdate() {
            return mForceUpdate;
        }

        TasksFilterType getFilterType() {
            return mFilterType;
        }
    }
}
