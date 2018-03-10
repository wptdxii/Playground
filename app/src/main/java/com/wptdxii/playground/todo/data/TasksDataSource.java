package com.wptdxii.playground.todo.data;

import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.data.model.Task;

import java.util.List;

public interface TasksDataSource {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();

    }

    interface LoadTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String taskId, LoadTaskCallback callback);

    void saveTask(@NonNull Task task);

    void completeTask(@NonNull Task task);

//    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull Task task);

//    void activateTask(@NonNull String taskId);

    void deleteTask(@NonNull Task task);

//    void deleteTask(@NonNull String taskId);

    void deleteAllCompletedTasks();

    void deleteAllTasks();

    void refreshTasks();
}
