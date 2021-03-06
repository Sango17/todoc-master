package com.cleanup.todoc.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public TaskViewModel(TaskDataRepository taskDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    public LiveData<List<Task>> getTasksAlphabetically() {
        return taskDataSource.getTasksAlphabetically();
    }

    public LiveData<List<Task>> getTasksAlphabeticallyInverted() {
        return taskDataSource.getTasksAlphabeticallyInverted();
    }

    public LiveData<List<Task>> getTasksRecentFirst() {
        return taskDataSource.getTasksRecentFirst();
    }

    public LiveData<List<Task>> getTasksOldFirst() {
        return taskDataSource.getTasksOldFirst();
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(Long TaskId) {
        executor.execute(() -> taskDataSource.deleteTask(TaskId));
    }
}
