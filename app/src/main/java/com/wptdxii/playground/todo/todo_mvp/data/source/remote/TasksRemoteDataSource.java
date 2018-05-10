package com.wptdxii.playground.todo.todo_mvp.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Collections;
import com.wptdxii.playground.todo.todo_mvp.data.Task;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TasksRemoteDataSource implements TasksDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 3000;
    private static final Map<String, Task> mTasksMap = new LinkedHashMap<>();

    private TasksRemoteDataSource() {}

    public static TasksRemoteDataSource getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksMap.put(task.getId(), task);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasksMap.remove(task.getId());
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksMap.remove(taskId);
    }

    @Override
    public void deleteCompletedTasks() {
        Iterator<Map.Entry<String, Task>> iterator = mTasksMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().isCompleted()) iterator.remove();
        }
    }

    @Override
    public void deleteAllTasks() {
        mTasksMap.clear();
    }

    @Override
    public void updateTask(@NonNull String taskId, boolean completed) {
        Task task = mTasksMap.get(taskId);
        if (task != null) task.setCompleted(completed);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mTasksMap.put(task.getId(), task);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull LoadTaskCallback callback) {
        final Task task = mTasksMap.get(taskId);

        new Handler().postDelayed(() -> {
            if (task != null) {
                callback.onTaskLoaded(task);
            } else {
                callback.onDataNotAvailable();
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        final List<Task> tasks = Collections.newArrayList(mTasksMap.values());
        new Handler().postDelayed(() -> {
            if (!tasks.isEmpty()) {
                callback.onTasksLoaded(tasks);
            } else {
                callback.onDataNotAvailable();
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    private static class SingletonHolder {
        private static final TasksRemoteDataSource INSTANCE = new TasksRemoteDataSource();
    }
}
