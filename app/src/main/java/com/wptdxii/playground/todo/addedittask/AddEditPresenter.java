package com.wptdxii.playground.todo.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.core.schedulers.ISchedulerProvider;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually bypassing Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
final class AddEditPresenter implements AddEditContract.Presenter {

    private final String mTaskId;
    private final Lazy<Boolean> mIsDataMissingProvider;
    private boolean mIsDataMissing;
    private AddEditContract.View mAddEditView;
    private final TasksRepository mTasksRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final ISchedulerProvider mSchedulerProvider;

    @Inject
    AddEditPresenter(@Nullable String taskId,
                     @NonNull TasksRepository tasksRepository,
                     @NonNull ISchedulerProvider schedulerProvider,
                     @NonNull CompositeDisposable compositeDisposable,
                     Lazy<Boolean> shouldLoadDataFromRepo) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mCompositeDisposable = compositeDisposable;
        mSchedulerProvider = schedulerProvider;
        mIsDataMissingProvider = shouldLoadDataFromRepo;
    }

    @Override
    public void attach(AddEditContract.View view) {
        mAddEditView = view;
        mIsDataMissing = mIsDataMissingProvider.get();
        if (mIsDataMissing) {
            getTask();
        }
    }

    @Override
    public void detach() {
        mAddEditView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void saveTask(String title, String description) {
        if (mTaskId == null) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public void getTask() {
        if (mTaskId != null) {
            mCompositeDisposable.clear();
            Disposable disposable = mTasksRepository
                    .getTak(mTaskId)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(task -> {
                        if (mAddEditView != null) {
                            mAddEditView.showTask(task);
                        }
                    }, throwable -> {
                        if (mAddEditView != null) {
                            mAddEditView.showEmptyTaskError();
                            mIsDataMissing = false;
                        }
                    });
            mCompositeDisposable.add(disposable);
        }
    }

    private void updateTask(String title, String description) {
        Task task = Task.createNewTaskWithId(mTaskId, title, description);
        if (task.isEmpty()) {
            if (mAddEditView != null) {
                mAddEditView.showEmptyTaskError();
            }
        } else {
            saveTask(task);
        }
    }

    private void saveTask(Task task) {
        mTasksRepository.updateTask(task);
        if (mAddEditView != null) {
            mAddEditView.showTasksList();
        }
    }

    private void createTask(String title, String description) {
        Task task = Task.createNewTask(title, description);
        if (task.isEmpty()) {
            if (mAddEditView != null) {
                mAddEditView.showEmptyTaskError();
            }
        } else {
            saveTask(task);
        }
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }
}
