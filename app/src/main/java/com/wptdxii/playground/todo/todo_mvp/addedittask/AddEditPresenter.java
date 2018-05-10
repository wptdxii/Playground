package com.wptdxii.playground.todo.todo_mvp.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.todo.todo_mvp.data.Task;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksDataSource;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksRepository;

public final class AddEditPresenter implements AddEditContract.Presenter {

    private String mTaskId;
    private AddEditContract.View mAddEditView;
    private TasksRepository mTasksRepository;

    public AddEditPresenter(@Nullable String taskId, @NonNull TasksRepository tasksRepository) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public void attach(AddEditContract.View view) {
        mAddEditView = view;
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
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mAddEditView != null) {
                        mAddEditView.showEmptyTaskError();
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
}
