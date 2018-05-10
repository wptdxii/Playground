package com.wptdxii.playground.todo.todo_mvp.data.source;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.exception.InstantiationException;
import com.wptdxii.framekit.util.Collections;
import com.wptdxii.playground.todo.todo_mvp.data.Task;
import com.wptdxii.playground.todo.todo_mvp.data.source.local.TasksLocalDataSource;
import com.wptdxii.playground.todo.todo_mvp.data.source.remote.TasksRemoteDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TasksRepository implements TasksDataSource {
    private static volatile TasksRepository sINSTANCE;

    private Map<String, Task> mTasksCachedDataSource = new LinkedHashMap<>();
    private TasksLocalDataSource mTasksLocalDataSource;
    private TasksRemoteDataSource mTasksRemoteDataSource;

    private boolean mCacheDirty = false;

    private TasksRepository(TasksLocalDataSource localDataSource,
                            TasksRemoteDataSource remoteDataSource) {
        if (sINSTANCE != null) {
            throw new InstantiationException();
        }
        this.mTasksLocalDataSource = localDataSource;
        this.mTasksRemoteDataSource = remoteDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksLocalDataSource localDataSource,
                                              @NonNull TasksRemoteDataSource remoteDataSource) {
        TasksRepository instance = sINSTANCE;
        if (instance == null) {
            synchronized (TasksRepository.class) {
                instance = sINSTANCE;
                if (instance == null) {
                    sINSTANCE = instance = new TasksRepository(localDataSource, remoteDataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksCachedDataSource.put(task.getId(), task);
        mTasksLocalDataSource.saveTask(task);
        mTasksRemoteDataSource.saveTask(task);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasksCachedDataSource.remove(task.getId());
        mTasksLocalDataSource.deleteTask(task);
        mTasksRemoteDataSource.deleteTask(task);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksCachedDataSource.remove(taskId);
        mTasksLocalDataSource.deleteTask(taskId);
        mTasksRemoteDataSource.deleteTask(taskId);
    }

    @Override
    public void deleteCompletedTasks() {
        if (!mTasksCachedDataSource.isEmpty()) {
            Iterator<Map.Entry<String, Task>> iterator = mTasksCachedDataSource.entrySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().isCompleted()) iterator.remove();
            }
        }

        mTasksLocalDataSource.deleteCompletedTasks();
        mTasksRemoteDataSource.deleteCompletedTasks();
    }

    @Override
    public void deleteAllTasks() {
        mTasksCachedDataSource.clear();
        mTasksLocalDataSource.deleteAllTasks();
        mTasksRemoteDataSource.deleteAllTasks();
    }

    @Override
    public void updateTask(@NonNull String taskId, boolean completed) {
        final Task task = mTasksCachedDataSource.get(taskId);
        if (task != null) {
            task.setCompleted(completed);
        }

        mTasksLocalDataSource.updateTask(taskId, completed);
        mTasksRemoteDataSource.updateTask(taskId, completed);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mTasksCachedDataSource.put(task.getId(), task);
        mTasksLocalDataSource.updateTask(task);
        mTasksLocalDataSource.updateTask(task);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull LoadTaskCallback callback) {
        Task task = mTasksCachedDataSource.get(taskId);
        if (task != null) {
            callback.onTaskLoaded(task);
            return;
        }

        mTasksLocalDataSource.getTask(taskId, new LoadTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                callback.onTaskLoaded(task);
                mTasksCachedDataSource.put(taskId, task);
            }

            @Override
            public void onDataNotAvailable() {
                mTasksRemoteDataSource.getTask(taskId, new LoadTaskCallback() {
                    @Override
                    public void onTaskLoaded(@NonNull Task task) {
                        callback.onTaskLoaded(task);
                        mTasksCachedDataSource.put(taskId, task);
                        mTasksLocalDataSource.saveTask(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        if (!mCacheDirty) {
            callback.onTasksLoaded(Collections.newArrayList(mTasksCachedDataSource.values()));
        } else {
            getTasksFromRemote(callback);
        }
    }

    private void getTasksFromRemote(@NonNull LoadTasksCallback callback) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                callback.onTasksLoaded(tasks);
                refreshCache(tasks);
                refreshLocal(tasks);
                mCacheDirty = false;
            }

            @Override
            public void onDataNotAvailable() {
                mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
                    @Override
                    public void onTasksLoaded(@NonNull List<Task> tasks) {
                        callback.onTasksLoaded(tasks);
                        refreshCache(tasks);
                        mCacheDirty = false;
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                        mCacheDirty = false;
                    }
                });
            }
        });
    }

    private void refreshLocal(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private void refreshCache(@NonNull List<Task> tasks) {
        mTasksCachedDataSource.clear();
        for (Task task : tasks) {
            mTasksCachedDataSource.put(task.getId(), task);
        }
    }

    public void refreshTasks() {
        mCacheDirty = true;
    }
}
