package com.wptdxii.playground.todo.data.entity;

public final class Task {
    private String mTitle;
    private String mDescription;
    private boolean mCompleted;

    public Task(String title, String description, boolean completed) {
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
