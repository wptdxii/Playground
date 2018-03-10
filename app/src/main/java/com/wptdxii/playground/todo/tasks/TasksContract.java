package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.base.BaseContract;
import com.wptdxii.playground.todo.data.model.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseContract.BaseView {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showTasks(@NonNull List<Task> tasks);

        void setGroupViewVisibility(boolean showGroupList);

        void showNoTasks();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showAllFilterLabel();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showTaskDetail(@NonNull String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActivate();

        void showClearCompletedTasks();

        void showFilteringPopupMenu();

        void showAddNewTask();

        void showNewTaskSaved();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadTasks(boolean forceUpdate, boolean showLoadingIndicator);

        void openTaskDetail(@NonNull Task task);


        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activatedTask);

        void setFiltering(TasksFilterType filtering);

        TasksFilterType getFiltering();

        void clearCompletedTasks();

        void onActivityResult(int requestCode, int resultCode);

    }
}
