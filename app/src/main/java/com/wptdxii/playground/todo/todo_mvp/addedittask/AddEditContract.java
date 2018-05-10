package com.wptdxii.playground.todo.todo_mvp.addedittask;

import com.wptdxii.framekit.base.Contract;
import com.wptdxii.playground.todo.todo_mvp.data.Task;

public interface AddEditContract {

    interface View extends Contract.View {

        void showEmptyTaskError();

        void showTask(Task task);
    }

    interface Presenter extends Contract.Presenter<View> {

        void saveTask(String title, String description);

        void getTask();
    }
}
