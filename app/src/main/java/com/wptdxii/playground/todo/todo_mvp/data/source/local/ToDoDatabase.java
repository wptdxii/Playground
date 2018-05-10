package com.wptdxii.playground.todo.todo_mvp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.wptdxii.playground.todo.todo_mvp.data.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {

    private static volatile ToDoDatabase sINSTANCE;

    public static ToDoDatabase getInstance(Context context) {
        ToDoDatabase instance = sINSTANCE;
        if (instance == null) {
            synchronized (ToDoDatabase.class) {
                instance = sINSTANCE;
                if (instance == null) {
                    sINSTANCE = instance = Room.databaseBuilder(
                            context.getApplicationContext(), ToDoDatabase.class, "ToDo.db").build();
                }
            }
        }
        return instance;
    }

    public abstract TasksDao tasksDao();
}
