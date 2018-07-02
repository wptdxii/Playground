package com.wptdxii.playground.todo.taskdetails;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Strings;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

final class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private final String mTaskId;
    private TaskDetailsContract.View mTaskDetailsView;
    private TasksRepository mTasksRepository;

    @Inject
    TaskDetailsPresenter(@NonNull String taskId, TasksRepository tasksRepository) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public void attach(TaskDetailsContract.View view) {
        mTaskDetailsView = view;
        getTask();
    }

    @Override
    public void detach() {
        mTaskDetailsView = null;
    }

    private void getTask() {
        if (checkTaskId()) return;

        if (mTaskDetailsView != null) {
            mTaskDetailsView.showLoadingIndicator();
        }
        mTasksRepository.getTask(mTaskId, new TasksDataSource.LoadTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                if (mTaskDetailsView != null) {
                    showTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mTaskDetailsView != null) {
                    mTaskDetailsView.showMissingTask();
                }
            }
        });
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
