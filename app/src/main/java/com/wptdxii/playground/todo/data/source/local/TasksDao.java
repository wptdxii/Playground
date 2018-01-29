package com.wptdxii.playground.todo.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wptdxii.playground.todo.data.Task;

import java.util.List;

/**
 * Created by wptdxii on 18-1-29
 */
@Dao
public interface TasksDao {

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("SELECT * FROM tasks WHERE entryid = :taskId")
    Task getTaskById(int taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("UPDATE tasks SET completed = :completed WHERE entryid = :taskId")
    void updateTaskById(int taskId, boolean completed);

    @Query("DELETE FROM tasks WHERE entryid = :taskId")
    void deleteTaskById(int taskId);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("DELETE FROM tasks WHERE completed = 1")
    void deleteAllCompletedTasks();
}
