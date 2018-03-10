package com.wptdxii.playground.todo.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.data.model.Task;

/**
 * The Room database that contains the TODO_TASKS table.
 */
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {

    private static volatile ToDoDatabase sInstance;

    public static ToDoDatabase getInstance(@NonNull Context context) {
        ToDoDatabase instance = sInstance;
        if (instance == null) {
            synchronized (ToDoDatabase.class) {
                instance = sInstance;
                if (instance == null) {
                    sInstance = instance = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoDatabase.class, "todo_tasks.db")
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract TasksDao tasksDao();

}
