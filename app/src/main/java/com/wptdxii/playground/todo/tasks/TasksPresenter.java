package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wptdxii.playground.base.schedulers.ISchedulerProvider;
import com.wptdxii.playground.base.schedulers.SchedulerProvider;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

final class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View mTaskView;
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;
    private final TasksRepository mTasksRepository;
    private final ISchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;
    private boolean mFirstLoad = true;

    @Inject
    TasksPresenter(@NonNull TasksRepository tasksRepository,
                   @NonNull ISchedulerProvider schedulerProvider,
                   @NonNull CompositeDisposable compositeDisposable) {
        mTasksRepository = tasksRepository;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void checkTask(@NonNull Task task) {
        mTasksRepository.updateTask(task);
        loadTasks(false, false);
        if (mTaskView != null) {
            mTaskView.showTaskChecked(task.isCompleted());
        }
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.deleteCompletedTasks();
        loadTasks(false, false);
        if (mTaskView != null) {
            mTaskView.showCompletedTasksCleared();
        }
    }

    @Override
    public void clearAllTasks() {
        mTasksRepository.deleteAllTasks();
        if (mTaskView != null) {
            mTaskView.showAllTasksCleared();
            mTaskView.showNoTasks(mFilterType);
        }
    }


    @Override
    public void loadTasks(boolean forceUpdate, boolean showLoadingIndicator) {
        if (forceUpdate || mFirstLoad) mTasksRepository.refreshTasks();

        if (mTaskView != null && (showLoadingIndicator || mFirstLoad)) {
            mTaskView.showLoadingIndicator(true);
        }

        mCompositeDisposable.clear();
        Disposable disposable = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable)
                .filter(task -> {
                    switch (mFilterType) {
                        case ACTIVE_TASKS:
                            return !task.isCompleted();
                        case COMPLETED_TASKS:
                            return task.isCompleted();
                        case ALL_TASKS:
                        default:
                            return true;
                    }
                }).toList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(tasks -> {
                    showSuccess(showLoadingIndicator);
                    processTasks(tasks);
                }, throwable -> {
                    showError(showLoadingIndicator);
                });

        mCompositeDisposable.add(disposable);
    }

    private void showSuccess(boolean showLoadingIndicator) {
        if (mTaskView != null && (showLoadingIndicator || mFirstLoad)) {
            mTaskView.showLoadingIndicator(false);
            mFirstLoad = false;
        }
    }

    private void showError(boolean showLoadingIndicator) {
        if (mTaskView != null) {
            if (showLoadingIndicator || mFirstLoad) {
                mTaskView.showLoadingError();
                mTaskView.showLoadingIndicator(false);
                mFirstLoad = false;
            }
            mTaskView.showNoTasks(mFilterType);
        }
    }

    private void processTasks(List<Task> tasks) {
        if (mTaskView != null) {
            if (tasks.isEmpty()) {
                mTaskView.showNoTasks(mFilterType);
            } else {
                mTaskView.showTasks(tasks);
                mTaskView.showFilterLabel(mFilterType);
            }
        }
    }

    @Override
    public TasksFilterType getFilter() {
        return mFilterType;
    }

    @Override
    public void setFilter(TasksFilterType filterType) {
        this.mFilterType = filterType;
    }

    @Override
    public void attach(TasksContract.View view) {
        this.mTaskView = view;
        loadTasks(false, false);
    }

    @Override
    public void detach() {
        mTaskView = null;
        mCompositeDisposable.clear();
    }
}
