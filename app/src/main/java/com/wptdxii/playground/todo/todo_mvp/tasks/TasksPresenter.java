package com.wptdxii.playground.todo.todo_mvp.tasks;

import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.todo_mvp.data.Task;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksDataSource;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksRepository;

import java.util.ArrayList;
import java.util.List;

public final class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View mTaskView;
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;
    private TasksRepository mTasksRepository;
    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksRepository tasksRepository) {
        this.mTasksRepository = tasksRepository;
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

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                List<Task> taskList = new ArrayList<>();
                for (Task task : tasks) {
                    switch (mFilterType) {
                        case ALL_TASKS:
                            taskList.add(task);
                            break;
                        case ACTIVE_TASKS:
                            if (!task.isCompleted()) taskList.add(task);
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) taskList.add(task);
                            break;
                        default:
                            taskList.add(task);
                            break;
                    }
                }

                if (mTaskView != null && (showLoadingIndicator || mFirstLoad)) {
                    mTaskView.showLoadingIndicator(false);
                    mFirstLoad = false;
                }

                processTasks(taskList);
            }

            @Override
            public void onDataNotAvailable() {
                if (mTaskView != null) {
                    if (showLoadingIndicator || mFirstLoad) {
                        mTaskView.showLoadingError();
                        mTaskView.showLoadingIndicator(false);
                        mFirstLoad = false;
                    }
                    mTaskView.showNoTasks(mFilterType);
                }
            }
        });
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
    }

    @Override
    public void detach() {
        mTaskView = null;
    }
}
