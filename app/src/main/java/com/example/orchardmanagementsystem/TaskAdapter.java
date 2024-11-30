// TaskAdapter.java
package com.example.orchardmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task2> taskList;
    private OnTaskDeleteListener deleteListener;

    public interface OnTaskDeleteListener {
        void onTaskDelete(int position);
    }

    public TaskAdapter(List<Task2> taskList, OnTaskDeleteListener deleteListener) {
        this.taskList = taskList;
        this.deleteListener = deleteListener;
    }

    public void updateTasks(List<Task2> newTaskList) {
        this.taskList = newTaskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView, deleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task2 task = taskList.get(position);
        holder.eventTypeTextView.setText(task.getEventType());
        holder.commentTextView.setText(task.getComment());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView eventTypeTextView;
        TextView commentTextView;
        ImageButton deleteButton;

        public TaskViewHolder(@NonNull View itemView, OnTaskDeleteListener deleteListener) {
            super(itemView);
            eventTypeTextView = itemView.findViewById(R.id.textEventType);
            commentTextView = itemView.findViewById(R.id.textComment);
            deleteButton = itemView.findViewById(R.id.buttonDelete);

            deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onTaskDelete(getAdapterPosition());
                }
            });
        }
    }
}



