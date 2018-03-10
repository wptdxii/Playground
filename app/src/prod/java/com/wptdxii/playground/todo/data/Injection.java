package com.wptdxii.playground.todo.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.executor.AppExecutors;
import com.wptdxii.playground.todo.data.local.TasksLocalDataSource;
import com.wptdxii.playground.todo.data.local.ToDoDatabase;
import com.wptdxii.playground.todo.data.remote.TasksRemoteDataSource;

/**
 * Enables injection production implementations for {@link TasksDataSource} at compile time.
 */
public final class Injection {

    private Injection() {
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksLocalDataSource.getInstance(
                new AppExecutors(), database.tasksDao()), TasksRemoteDataSource.getInstance());
    }
}
