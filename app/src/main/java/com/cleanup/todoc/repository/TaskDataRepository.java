package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasksAlphabetically() {
        return this.taskDao.getTasksAlphabetically();
    }

    public LiveData<List<Task>> getTasksAlphabeticallyInverted() {
        return this.taskDao.getTasksAlphabeticallyInverted();
    }

    public LiveData<List<Task>> getTasksRecentFirst() {
        return this.taskDao.getTasksRecentFirst();
    }

    public LiveData<List<Task>> getTasksOldFirst() {
        return this.taskDao.getTasksOldFirst();
    }

    public void createTask(Task task) {
        this.taskDao.insertTask(task);
    }

    public void deleteTask(Long taskId) {
        this.taskDao.deleteTask(taskId);
    }

}
