package com.wptdxii.playground.todo.data.remote;

import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.data.TasksDataSource;
import com.wptdxii.playground.todo.data.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FakeTasksRemoteDataSource implements TasksDataSource {

    private Map<String, Task> mTasks;

    private FakeTasksRemoteDataSource() {
        if (SingletonHolder.INSTANCE != null) {
            throw new UnsupportedOperationException("Already be instantiated!");
        }

        initTasks();
    }

    private void initTasks() {
        mTasks = new LinkedHashMap<>();
        addTask("Fake: Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Fake: Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    private void addTask(String title, String description) {
        Task task = new Task(title, description);
        mTasks.put(task.getId(), task);
    }

    private static class SingletonHolder {
        private static final FakeTasksRemoteDataSource INSTANCE = new FakeTasksRemoteDataSource();
    }

    public static FakeTasksRemoteDataSource getInstance() {
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        List<Task> tasks = new ArrayList<>(mTasks.values());
        if (!tasks.isEmpty()) {
            callback.onTasksLoaded(tasks);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getTask(@NonNull String taskId, LoadTaskCallback callback) {
        Task task = mTasks.get(taskId);
        if (task != null) {
            callback.onTaskLoaded(task);
        } else {
            callback.onDataNotAvailable();
        }
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

    @Override
    public void activateTask(@NonNull Task task) {
        Task newTask = new Task(task.getId(), task.getTitle(), task.getDescription());
        mTasks.put(task.getId(), newTask);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasks.remove(task.getId());
    }

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
