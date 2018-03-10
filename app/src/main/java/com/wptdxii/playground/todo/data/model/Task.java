package com.wptdxii.playground.todo.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.wptdxii.framekit.util.Objects;

import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
@Entity(tableName = "todo_tasks")
public final class Task {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title
     * @param description
     */
    @Ignore
    public Task(String title, String description) {
        this(UUID.randomUUID().toString(), title, description, false);
    }

    /**
     * Use this constructor to create a new active Task if the Task has an id.
     *
     * @param id
     * @param title
     * @param description
     */
    @Ignore
    public Task(@NonNull String id, String title, String description) {
        this(id, title, description, false);
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title
     * @param description
     * @param completed
     */
    @Ignore
    public Task(String title, String description, boolean completed) {
        this(UUID.randomUUID().toString(), title, description, completed);
    }

    public Task(@NonNull String id, @Nullable String title,
                @Nullable String description, boolean completed) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(mTitle) && TextUtils.isEmpty(mDescription);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

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
        return "Task with title " + mTitle;
    }
}
