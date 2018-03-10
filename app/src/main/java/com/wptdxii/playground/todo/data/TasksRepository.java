package com.wptdxii.playground.todo.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.playground.todo.data.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TasksRepository implements TasksDataSource {

    private TasksDataSource mLocalDataSource;
    private TasksDataSource mRemoteDataSource;

    Map<String, Task> mCachedTasks;
    boolean mCacheIsDirty = false;

    private TasksRepository() {
        if (SingleHolder.INSTANCE != null) {
            throw new UnsupportedOperationException("Already be instantiated!");
        }
    }

    private static class SingleHolder {
        private static TasksRepository INSTANCE = new TasksRepository();

    }

    public static TasksRepository getInstance(TasksDataSource localDataSource,
                                              TasksDataSource remoteDataSource) {
        TasksRepository instance = SingleHolder.INSTANCE;
        if (instance.mLocalDataSource == null) {
            instance.mLocalDataSource = localDataSource;
        }

        if (instance.mRemoteDataSource == null) {
            instance.mRemoteDataSource = remoteDataSource;
        }

        return instance;
    }

    public static void distoryInstance() {
        SingleHolder.INSTANCE = null;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        if (mCachedTasks != null && !mCacheIsDirty) {
            List<Task> tasks = new ArrayList<>(mCachedTasks.values());
            if (!tasks.isEmpty()) {
                callback.onTasksLoaded(tasks);
            } else {
                callback.onDataNotAvailable();
            }
            return;
        }

        if (mCacheIsDirty) {
            getTasksFromRemoteDataSource(callback);
        } else {
            getTasksFromLocalDataSource(callback);
        }
    }

    private void getTasksFromRemoteDataSource(LoadTasksCallback callback) {
        mRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getTasksFromLocalDataSource(LoadTasksCallback callback) {
        mLocalDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                callback.onTasksLoaded(tasks);

            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Task> tasks) {
        mLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mLocalDataSource.saveTask(task);
        }
    }

    private void refreshCache(List<Task> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();

        for (Task task : tasks) {
            mCachedTasks.put(task.getId(), task);
        }

        mCacheIsDirty = false;
    }

    @Override
    public void getTask(@NonNull String taskId, LoadTaskCallback callback) {
        Task task = getTaskFromCache(taskId);
        if (task != null) {
            callback.onTaskLoaded(task);
            return;
        }

        mLocalDataSource.getTask(taskId, new LoadTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                addToCache(task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                mRemoteDataSource.getTask(taskId, new LoadTaskCallback() {
                    @Override
                    public void onTaskLoaded(Task task) {
                        addToCache(task);
                        addToLocalDataSource(task);
                        callback.onTaskLoaded(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    private void addToLocalDataSource(Task task) {
        mLocalDataSource.saveTask(task);
    }

    private void addToCache(@NonNull Task task) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), task);
    }

    @Nullable
    private Task getTaskFromCache(String taskId) {
        if (mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        }
        return mCachedTasks.get(taskId);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mLocalDataSource.saveTask(task);
        mRemoteDataSource.saveTask(task);
        addToCache(task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        mLocalDataSource.completeTask(task);
        mRemoteDataSource.completeTask(task);

        Task completedTask = new Task(task.getId(), task.getTitle(), task.getDescription(), true);
        addToCache(completedTask);
    }

    //    @Override
    //    public void completeTask(@NonNull String taskId) {
    //        getTask(taskId, new LoadTaskCallback() {
    //            @Override
    //            public void onTaskLoaded(Task task) {
    //                completeTask(task);
    //            }
    //
    //            @Override
    //            public void onDataNotAvailable() {
    //                throw new TaskNotExistException();
    //            }
    //        });
    //        mLocalDataSource.completeTask(taskId);
    //        mRemoteDataSource.completeTask(taskId);
    //        addToCache(getTaskFromCache(taskId));
    //
    //    }

    @Override
    public void activateTask(@NonNull Task task) {
        mLocalDataSource.activateTask(task);
        mRemoteDataSource.activateTask(task);

        Task activateTask = new Task(task.getId(), task.getTitle(), task.getDescription(), false);
        addToCache(activateTask);

    }

    //    @Override
    //    public void activateTask(@NonNull String taskId) {
    //
    //    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mLocalDataSource.deleteTask(task);
        mRemoteDataSource.deleteTask(task);
        removeFromCache(task);
    }

    private void removeFromCache(Task task) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
            return;
        }
        mCachedTasks.remove(task.getId());
    }

    //    @Override
    //    public void deleteTask(@NonNull String taskId) {
    //
    //    }

    @Override
    public void deleteAllCompletedTasks() {
        mLocalDataSource.deleteAllCompletedTasks();
        mRemoteDataSource.deleteAllCompletedTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
            return;
        }

        Iterator<Map.Entry<String, Task>> iterator = mCachedTasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Task> entry = iterator.next();
            if (entry.getValue().isCompleted()) {
                iterator.remove();
            }
        }

    }

    @Override
    public void deleteAllTasks() {
        mLocalDataSource.deleteAllTasks();
        mRemoteDataSource.deleteAllTasks();
        if (mCachedTasks != null && !mCachedTasks.isEmpty()) {
            mCachedTasks.clear();
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }
}
