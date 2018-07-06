package com.wptdxii.playground.todo.tasks.usecase;

import com.wptdxii.playground.core.interactor.CompletableUseCase;
import com.wptdxii.playground.core.interactor.UseCase;
import com.wptdxii.playground.core.schedulers.SchedulerProvider;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Completable;

public class CheckTasks extends CompletableUseCase<CheckTasks.Request> {

    private TasksRepository mTasksRepository;

    @Inject
    public CheckTasks(SchedulerProvider schedulerProvider, TasksRepository repository) {
        super(schedulerProvider);
        mTasksRepository = repository;
    }

    @Override
    protected Completable buildUseCase(Request request) {
        mTasksRepository.updateTask(request.getTask());
        return Completable.complete();
    }

    public static class Request implements UseCase.Reqeust {

        private final Task mTask;

        public Request(Task task) {
            mTask = task;
        }

        public Task getTask() {
            return mTask;
        }
    }

}
