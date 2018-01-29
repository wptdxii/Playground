package com.wptdxii.playground.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import com.wptdxii.framekit.util.Objects;

/**
 * Created by wptdxii
 */
@Entity(tableName = "tasks")
public final class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entryid")
    private int mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    public Task(@Nullable String title, @Nullable String description,
                boolean completed) {
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String title) {
        mTitle = title;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String description) {
        mDescription = description;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Tasks with title " + mTitle;
    }
}
