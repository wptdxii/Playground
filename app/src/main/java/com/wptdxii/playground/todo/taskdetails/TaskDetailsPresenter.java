package com.wptdxii.playground.todo.taskdetails;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Strings;
import com.wptdxii.playground.core.schedulers.ISchedulerProvider;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

final class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private final String mTaskId;
    private final TasksRepository mTasksRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final ISchedulerProvider mSchedulerProvider;
    private TaskDetailsContract.View mTaskDetailsView;

    @Inject
    TaskDetailsPresenter(@NonNull String taskId,
                         @NonNull TasksRepository tasksRepository,
                         @NonNull ISchedulerProvider schedulerProvider,
                         @NonNull CompositeDisposable compositeDisposable) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void attach(TaskDetailsContract.View view) {
        mTaskDetailsView = view;
        getTask();
    }

    @Override
    public void detach() {
        mTaskDetailsView = null;
        mCompositeDisposable.clear();
    }

    private void getTask() {
        if (checkTaskId()) return;

        if (mTaskDetailsView != null) {
            mTaskDetailsView.showLoadingIndicator();
        }

        mCompositeDisposable.clear();
        Disposable disposable = mTasksRepository
                .getTak(mTaskId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(task -> {
                    if (mTaskDetailsView != null) {
                        showTask(task);
                    }
                }, throwable -> {
                    if (mTaskDetailsView != null) {
                        mTaskDetailsView.showMissingTask();
                    }
                });
        mCompositeDisposable.add(disposable);

    }

    private boolean checkTaskId() {
        if (Strings.isEmpty(mTaskId) && mTaskDetailsView != null) {
            mTaskDetailsView.showMissingTask();
            return true;
        }
        return false;
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getDescription();
        if (Strings.isEmpty(task.getTitle())) {
            mTaskDetailsView.hideTaskTitle();
        } else {
            mTaskDetailsView.showTaskTitle(title);
        }

        if (Strings.isEmpty(task.getDescription())) {
            mTaskDetailsView.hideTaskDescription();
        } else {
            mTaskDetailsView.showTaskDescription(description);
        }

        mTaskDetailsView.showTaskCompletionStatus(task.isCompleted());
    }

    @Override
    public void editTask() {

        if (checkTaskId()) return;
        if (mTaskDetailsView != null) {
            mTaskDetailsView.showEditTask(mTaskId);
        }

    }

    @Override
    public void deleteTask() {
        if (checkTaskId()) return;
        mTasksRepository.deleteTask(mTaskId);
        if (mTaskDetailsView != null) {
            mTaskDetailsView.showTaskDeleted();
        }
    }

    @Override
    public void checkTask(boolean checked) {
        if (checkTaskId()) return;

        mTasksRepository.updateTask(mTaskId, checked);

        if (mTaskDetailsView != null) {
            mTaskDetailsView.showTaskChecked(checked);
        }
    }
}
