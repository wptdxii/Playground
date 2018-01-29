package com.wptdxii.playground.todo.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.wptdxii.playground.todo.data.Task;

/**
 * Created by wptdxii on 18-1-29
 */

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract TasksDao tasksDao();
}
