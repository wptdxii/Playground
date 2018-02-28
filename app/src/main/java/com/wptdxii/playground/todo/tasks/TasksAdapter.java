package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wptdxii.playground.R;
import com.wptdxii.playground.todo.data.entity.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> mTasks;
    private TaskItemListener mListener;

    public TasksAdapter(@NonNull List<Task> tasks, @NonNull TaskItemListener listener) {
        this.mTasks = tasks;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_todo_tasks, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) mListener.onCompleteTaskClick(viewHolder.mTask);
            else mListener.onActivateTaskClick(viewHolder.mTask);
        });

        viewHolder.clTask.setOnClickListener(view -> {
            mListener.onTaskClick(viewHolder.mTask);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Task task = mTasks.get(position);
        String title = task.getTitle();
        if (TextUtils.isEmpty(title)) {
            title = task.getDescription();
        }
        holder.tvTitle.setText(title);

        boolean isCompleted = task.isCompleted();
        holder.cbCompleted.setSelected(isCompleted);
        holder.clTask.setPressed(isCompleted);

        holder.mTask = task;
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cl_task)
        ConstraintLayout clTask;
        @BindView(R.id.cb_completed)
        CheckBox cbCompleted;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        Task mTask;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface TaskItemListener {
        void onTaskClick(Task clickedTask);

        void onCompleteTaskClick(Task completedTask);

        void onActivateTaskClick(Task activatedTask);
    }

}
