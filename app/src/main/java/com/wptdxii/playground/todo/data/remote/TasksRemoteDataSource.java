package com.wptdxii.playground.todo.data.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Collections;
import com.wptdxii.playground.todo.data.TasksDataSource;
import com.wptdxii.playground.todo.data.model.Task;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TasksRemoteDataSource implements TasksDataSource {
    private static final long SERVICE_LATENCY_MILLIS = 5000L;
    private Map<String, Task> mTasks;

    private TasksRemoteDataSource() {
        if (SingleHolder.INSTANCE != null) {
            throw new UnsupportedOperationException("Already be instantiated!");
        }

        initTasks();
    }

    private void initTasks() {
        mTasks = new LinkedHashMap<>();
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    private void addTask(String title, String description) {
        Task newTask = new Task(title, description, false);
        mTasks.put(newTask.getId(), newTask);
    }

    private static class SingleHolder {
        private static final TasksRemoteDataSource INSTANCE = new TasksRemoteDataSource();
    }

    public static TasksRemoteDataSource getInstance() {
        return SingleHolder.INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        new Handler().postDelayed(() -> {
            if (!mTasks.isEmpty()) {
                callback.onTasksLoaded(Collections.newArrayList(mTasks.values()));
            } else {
                callback.onDataNotAvailable();
            }
        }, SERVICE_LATENCY_MILLIS);
    }

    @Override
    public void getTask(@NonNull String taskId, LoadTaskCallback callback) {
        Task task = mTasks.get(taskId);
        new Handler().postDelayed(() -> {
            if (task != null) {
                callback.onTaskLoaded(task);
            } else {
                callback.onDataNotAvailable();
            }
        }, SERVICE_LATENCY_MILLIS);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Task newTask = new Task(task.getId(), task.getTitle(), task.getDescription(), true);
        mTasks.put(task.getId(), newTask);
    }

    //    @Override
    //    public void completeTask(@NonNull String taskId) {
    //        Task task = mTasks.get(taskId);
    //        Task newTask = new Task(taskId, task.getTitle(), task.getDescription(), true);
    //        mTasks.put(taskId, newTask);
    //    }

    @Override
    public void activateTask(@NonNull Task task) {
        Task newTask = new Task(task.getId(), task.getTitle(), task.getDescription());
        mTasks.put(task.getId(), newTask);
    }

    //    @Override
    //    public void activateTask(@NonNull String taskId) {
    //        Task task = mTasks.get(taskId);
    //        Task newTask = new Task(taskId, task.getTitle(), task.getDescription());
    //        mTasks.put(taskId, newTask);
    //    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasks.remove(task.getId());
    }

    //    @Override
    //    public void deleteTask(@NonNull String taskId) {
    //        mTasks.remove(taskId);
    //    }

    @Override
    public void deleteAllCompletedTasks() {
        Iterator<Map.Entry<String, Task>> iterator = mTasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Task> entry = iterator.next();
            if (entry.getValue().isCompleted()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        mTasks.clear();
    }

    @Override
    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }
}
