package com.wptdxii.playground.todo.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wptdxii.playground.todo.data.source.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {

    public static final String DB_NAME_TODO = "ToDo.db";

    private static volatile ToDoDatabase sINSTANCE;

    /**
     * use without dagger
     *
     * @param context
     * @return
     */
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
