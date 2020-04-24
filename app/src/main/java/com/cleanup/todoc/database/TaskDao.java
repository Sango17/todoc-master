package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksAlphabetically();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksAlphabeticallyInverted();

    @Query("SELECT * FROM Task ORDER BY creation_timestamp DESC")
    LiveData<List<Task>> getTasksRecentFirst();

    @Query("SELECT * FROM Task ORDER BY creation_timestamp ASC")
    LiveData<List<Task>> getTasksOldFirst();

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    void deleteTask(long id);
}
