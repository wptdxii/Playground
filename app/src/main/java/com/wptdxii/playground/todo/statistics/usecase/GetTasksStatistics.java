package com.wptdxii.playground.todo.statistics.usecase;

import android.util.Log;
import android.util.Pair;

import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;
import com.wptdxii.playground.core.interactor.FlowableUseCase;
import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

@FragmentScoped
public class GetTasksStatistics extends FlowableUseCase<Void, Pair<Long, Long>> {

    private final TasksRepository mTasksRepository;

    @Inject
    GetTasksStatistics(@NonNull ThreadExecutor threadExecutor,
                       @NonNull PostExecutionThread executionThread,
                       @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Flowable<Pair<Long, Long>> buildUseCase(Void aVoid) {
        Flowable<Task> taskFlowable = mTasksRepository.getTasks()
                .flatMap(Flowable::fromIterable);

        Flowable<Long> completedTasksCount = taskFlowable
                .filter(Task::isCompleted)
                .count()
                .toFlowable();

        Flowable<Long> activeTaskCount = taskFlowable
                .filter(task -> !task.isCompleted())
                .count()
                .toFlowable();

        return Flowable.zip(completedTasksCount, activeTaskCount, Pair::create);
    }
}
