package com.wptdxii.playground.todo.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wptdxii.framekit.base.BasePresenter;
import com.wptdxii.framekit.util.Preconditions;
import com.wptdxii.playground.todo.addnewtask.AddNewTaskActivity;
import com.wptdxii.playground.todo.data.TasksDataSource;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksPresenter extends BasePresenter<TasksContract.View>
        implements TasksContract.Presenter {

    private TasksRepository mTasksRepository;
    private boolean mFirstLoad = true;
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View view) {
        super(view);
        this.mTasksRepository = Preconditions.checkNotNull(tasksRepository, "tasksRepository cannot be null.");

    }

    @Override
    public void loadTasks(boolean forceUpdate, boolean showLoadingIndicator) {
        if (showLoadingIndicator) {
            getView().setLoadingIndicator(true);
        }

        if (mFirstLoad || forceUpdate) {
            mTasksRepository.refreshTasks();
            mFirstLoad = false;
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<>();
                for (Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;
                        case ACTIVE_TASKS:
                            if (task.isActive()) {
                                tasksToShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }

                if (!getView().isActive()) {
                    return;
                }

                if (showLoadingIndicator) {
                    getView().setLoadingIndicator(false);
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processTasks(List<Task> tasksToShow) {
        getView().setGroupViewVisibility(!tasksToShow.isEmpty());
        if (tasksToShow.isEmpty()) {
            processEmptyTasks();
        } else {
            getView().showTasks(tasksToShow);
            showFilterLabel();
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ALL_TASKS:
                getView().showNoTasks();
                break;
            case ACTIVE_TASKS:
                getView().showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                getView().showNoCompletedTasks();
                break;
            default:
                getView().showNoTasks();
                break;
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ALL_TASKS:
                getView().showAllFilterLabel();
                break;
            case ACTIVE_TASKS:
                getView().showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                getView().showCompletedFilterLabel();
                break;
            default:
                getView().showAllFilterLabel();
                break;
        }
    }

    @Override
    public void openTaskDetail(@NonNull Task task) {
        getView().showTaskDetail(task.getId());
    }

    @Override
    public void completeTask(@NonNull Task completedTask) {
        mTasksRepository.completeTask(completedTask);
        getView().showTaskMarkedComplete();
        loadTasks(false, false);
    }

    @Override
    public void activateTask(@NonNull Task activatedTask) {
        mTasksRepository.activateTask(activatedTask);
        getView().showTaskMarkedActivate();
        loadTasks(false, false);
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.deleteAllCompletedTasks();
        loadTasks(false, false);
        getView().showClearCompletedTasks();

    }

    @Override
    public void setFiltering(TasksFilterType filtering) {
        mCurrentFiltering = filtering;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mCurrentFiltering;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {
        if (AddNewTaskActivity.REQUEST_CODE_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            getView().showNewTaskSaved();
        }
    }
}
