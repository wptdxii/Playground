package com.wptdxii.playground.todo.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wptdxii.playground.todo.data.model.Task;

import java.util.List;

/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface TasksDao {

    @Query("SELECT * FROM TODO_TASKS")
    List<Task> getTasks();

    @Query("SELECT * FROM TODO_TASKS WHERE entryid = :taskId")
    Task getTaskById(String taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("UPDATE TODO_TASKS SET completed = :completed WHERE entryid = :taskId")
    void updateCompltedTask(String taskId, boolean completed);

    @Query("DELETE FROM TODO_TASKS WHERE entryid = :taskId")
    void deleteTaskById(String taskId);

    @Query("DELETE FROM TODO_TASKS")
    void deleteAllTasks();

    @Query("DELETE FROM TODO_TASKS WHERE completed = 1")
    void deleteAllCompletedTasks();
}

