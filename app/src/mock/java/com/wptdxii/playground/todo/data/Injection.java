package com.wptdxii.playground.todo.data;

import android.content.Context;

import com.wptdxii.framekit.component.executor.AppExecutors;
import com.wptdxii.playground.todo.data.local.TasksLocalDataSource;
import com.wptdxii.playground.todo.data.local.ToDoDatabase;
import com.wptdxii.playground.todo.data.remote.FakeTasksRemoteDataSource;

public final class Injection {

    private Injection() {
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static TasksRepository provideTasksRepository(Context context) {
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksLocalDataSource.getInstance(
                new AppExecutors(), database.tasksDao()), FakeTasksRemoteDataSource.getInstance());
    }

}
