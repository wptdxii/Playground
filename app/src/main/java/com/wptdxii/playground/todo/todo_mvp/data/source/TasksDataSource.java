package com.wptdxii.playground.todo.todo_mvp.data.source;

import android.support.annotation.NonNull;
import com.wptdxii.playground.todo.todo_mvp.data.Task;

import java.util.List;

public interface TasksDataSource {

    void saveTask(@NonNull Task task);

    void deleteTask(@NonNull Task task);

    void deleteTask(@NonNull String taskId);

    void deleteCompletedTasks();

    void deleteAllTasks();

    void updateTask(@NonNull String taskId, boolean completed);

    void updateTask(@NonNull Task task);

    void getTask(@NonNull String taskId, @NonNull LoadTaskCallback callback);

    void getTasks(@NonNull LoadTasksCallback callback);


    interface LoadTaskCallback {

        void onTaskLoaded(@NonNull Task task);

        void onDataNotAvailable();
    }

    interface LoadTasksCallback {

        void onTasksLoaded(@NonNull List<Task> tasks);

        void onDataNotAvailable();
    }
}
