package com.wptdxii.playground.todo.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;

import javax.inject.Inject;

import dagger.Lazy;

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
    private TasksRepository mTasksRepository;

    @Inject
    AddEditPresenter(@Nullable String taskId,
                     @NonNull TasksRepository tasksRepository,
                     Lazy<Boolean> shouldLoadDataFromRepo) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
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
            mTasksRepository.getTask(mTaskId, new TasksDataSource.LoadTaskCallback() {
                @Override
                public void onTaskLoaded(@NonNull Task task) {
                    if (mAddEditView != null) {
                        mAddEditView.showTask(task);
                        mIsDataMissing = false;
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mAddEditView != null) {
                        mAddEditView.showEmptyTaskError();
                        mIsDataMissing = false;
                    }
                }
            });
        }
    }

    private void updateTask(String title, String description) {
        Task task = Task.createNewTaskWithId(mTaskId, title, description);
        if (task.isEmpty()) {
            if (mAddEditView != null) {
                mAddEditView.showEmptyTaskError();
            }
        } else {
            mTasksRepository.updateTask(task);
        }
    }

    private void createTask(String title, String description) {
        Task task = Task.createNewTask(title, description);
        if (task.isEmpty()) {
            if (mAddEditView != null) {
                mAddEditView.showEmptyTaskError();
            }
        } else {
            mTasksRepository.saveTask(task);
        }
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }
}
