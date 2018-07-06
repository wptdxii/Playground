package com.wptdxii.playground.todo.tasks.usecase;

import com.wptdxii.playground.core.interactor.FlowableUseCase;
import com.wptdxii.playground.core.interactor.UseCase;
import com.wptdxii.playground.core.schedulers.SchedulerProvider;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.tasks.TasksFilterType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetTaskss extends FlowableUseCase<GetTaskss.Request, List<Task>> {

    private final TasksRepository mTasksRepository;

    @Inject
    GetTaskss(SchedulerProvider schedulerProvider,
              TasksRepository tasksRepository) {
        super(schedulerProvider);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Flowable<List<Task>> buildUseCase(Request request) {
        return mTasksRepository.getTasks();
    }

    public static final class Request implements UseCase.Reqeust {
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
