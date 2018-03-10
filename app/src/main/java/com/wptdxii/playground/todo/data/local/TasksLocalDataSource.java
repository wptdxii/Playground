package com.wptdxii.playground.todo.data.local;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.executor.AppExecutors;
import com.wptdxii.playground.todo.data.TasksDataSource;
import com.wptdxii.playground.todo.data.model.Task;

import java.util.List;

public class TasksLocalDataSource implements TasksDataSource {

    private AppExecutors mAppExecutors;
    private TasksDao mTasksDao;

    private TasksLocalDataSource() {
        if (SingletonHolder.INSTANCE != null) {
            throw new UnsupportedOperationException("Already be instantiated!");
        }
    }

    private static class SingletonHolder {
        private static final TasksLocalDataSource INSTANCE = new TasksLocalDataSource();
    }

    public static TasksLocalDataSource getInstance(@NonNull AppExecutors executors,
                                                   @NonNull TasksDao tasksDao) {
        TasksLocalDataSource instance = SingletonHolder.INSTANCE;
        if (instance.mAppExecutors == null) {
            instance.mAppExecutors = executors;
        }

        if (instance.mTasksDao == null) {
            instance.mTasksDao = tasksDao;
        }
        return instance;
    }


    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        Runnable runnable = () -> {
            List<Task> tasks = mTasksDao.getTasks();
            mAppExecutors.getMainThread().execute(() -> {
                if (!tasks.isEmpty()) {
                    callback.onTasksLoaded(tasks);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void getTask(@NonNull String taskId, LoadTaskCallback callback) {
        Runnable runnable = () -> {
            Task task = mTasksDao.getTaskById(taskId);
            mAppExecutors.getMainThread().execute(() -> {
                if (task != null) {
                    callback.onTaskLoaded(task);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        Runnable runnable = () -> {
            mTasksDao.insertTask(task);
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Runnable runnable = () -> {
            mTasksDao.updateCompltedTask(task.getId(), true);
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

//    @Override
//    public void completeTask(@NonNull String taskId) {
//        Runnable runnable = () -> {
//            mTasksDao.updateCompltedTask(taskId, true);
//        };
//        mAppExecutors.getDiskIO().execute(runnable);
//    }

    @Override
    public void activateTask(@NonNull Task task) {
        Runnable runnable = () -> {
            mTasksDao.updateCompltedTask(task.getId(), false);
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

//    @Override
//    public void activateTask(@NonNull String taskId) {
//        Runnable runnable = () -> {
//            mTasksDao.updateCompltedTask(taskId, false);
//        };
//        mAppExecutors.getDiskIO().execute(runnable);
//    }

    @Override
    public void deleteTask(@NonNull Task task) {
        Runnable runnable = () -> {
            mTasksDao.deleteTaskById(task.getId());
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

//    @Override
//    public void deleteTask(@NonNull String taskId) {
//        Runnable runnable = () -> {
//            mTasksDao.deleteTaskById(taskId);
//        };
//        mAppExecutors.getDiskIO().execute(runnable);
//    }

    @Override
    public void deleteAllCompletedTasks() {
        Runnable runnable = () -> {
            mTasksDao.deleteAllCompletedTasks();
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void deleteAllTasks() {
        Runnable runnable = () -> {
            mTasksDao.deleteAllTasks();
        };
        mAppExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }
}
