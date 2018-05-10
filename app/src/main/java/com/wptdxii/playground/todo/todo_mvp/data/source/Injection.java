package com.wptdxii.playground.todo.todo_mvp.data.source;

import android.content.Context;

import com.wptdxii.framekit.component.executor.AppExecutors;
import com.wptdxii.framekit.exception.InstantiationException;
import com.wptdxii.playground.todo.todo_mvp.data.source.local.TasksLocalDataSource;
import com.wptdxii.playground.todo.todo_mvp.data.source.local.ToDoDatabase;
import com.wptdxii.playground.todo.todo_mvp.data.source.remote.TasksRemoteDataSource;

public final class Injection {

    private Injection() {
        throw new InstantiationException();
    }

    public static TasksRepository provideTasksRepository(Context context) {
        return TasksRepository.getInstance(TasksLocalDataSource.getInstance(
                new AppExecutors(), ToDoDatabase.getInstance(context).tasksDao()),
                TasksRemoteDataSource.getInstance());
    }
}
