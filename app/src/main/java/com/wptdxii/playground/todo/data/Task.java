package com.wptdxii.playground.todo.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.framekit.util.ObjectUtil;

import java.util.UUID;

/**
 * Created by wptdxii
 */

public class Task {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final boolean mCompleted;

    /**
     * Use this constructor to create a new active task.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    public Task(@Nullable String title, @Nullable String description) {
        this(UUID.randomUUID().toString(), title, description, false);
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id(copy of another
     * Task).
     *
     * @param id          id of the Task
     * @param title       title of the task
     * @param description description of the task
     */
    public Task(@NonNull String id, @Nullable String title, @Nullable String description) {
        this(id, title, description, false);
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(UUID.randomUUID().toString(), title, description, completed);
    }


    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param id          id of the task
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    public Task(@NonNull String id, @Nullable String title, @Nullable String description,
                boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return ObjectUtil.equals(mId, task.mId) &&
                ObjectUtil.equals(mTitle, task.mTitle) &&
                ObjectUtil.equals(mDescription, task.mDescription);
    }
}
