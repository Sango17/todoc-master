package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private ToDocDatabase database;

    private static long PROJECT_ID_1 = 1;
    private static long PROJECT_ID_2 = 2;
    private static long PROJECT_ID_3 = 3;
    private static String TASK_DEMO_NAME_1 = "hhh Tâche example";
    private static String TASK_DEMO_NAME_2 = "aaa Tâche example";
    private static String TASK_DEMO_NAME_3 = "zzz Tâche example";
    private static Task TASK_DEMO_1 = new Task(PROJECT_ID_1, TASK_DEMO_NAME_1, new Date().getTime());
    private static Task TASK_DEMO_2 = new Task(PROJECT_ID_2, TASK_DEMO_NAME_2, new Date().getTime());
    private static Task TASK_DEMO_3 = new Task(PROJECT_ID_3, TASK_DEMO_NAME_3, new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database =
                Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                        ToDocDatabase.class)
                        .allowMainThreadQueries()
                        .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        this.database.taskDao().insertTask(TASK_DEMO_1);

        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTask(1));

        Assert.assertTrue(task.getName().equals(TASK_DEMO_NAME_1) &&
                task.getProjectId() == PROJECT_ID_1);
    }

    @Test
    public void insertTasksAndGetTasksAlphabetically() throws InterruptedException {
        setTasks();

        // Sort alphabetical
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksAlphabetically());
        Assert.assertTrue(tasksSortedAlphabetically(tasks));
    }

    @Test
    public void insertTasksAndGetTasksAlphabeticallyInverted() throws InterruptedException {
        setTasks();

        // Sort alphabetical inverted
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksAlphabeticallyInverted());
        Assert.assertTrue(tasksSortedAlphabeticallyInverted(tasks));
    }

    @Test
    public void insertTasksAndGetTasksOldFirst() throws InterruptedException {
        setTasks();

        // Sort old first
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksOldFirst());
        Assert.assertTrue(tasksSortedOldFirst(tasks));
    }

    @Test
    public void insertTasksAndGetTasksRecentFirst() throws InterruptedException {
        setTasks();

        // Sort Recent first
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksRecentFirst());
        Assert.assertTrue(tasksSortedRecentFirst(tasks));
    }

    @Test
    public void deleteTaskFromTasks() throws InterruptedException {
        setTasks();

        // Suppress one of three tasks
        this.database.taskDao().deleteTask(TASK_DEMO_1.getId());

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        Assert.assertEquals(tasks.get(0), TASK_DEMO_1);
    }

    private void setTasks(){

        try {
            this.database.taskDao().insertTask(TASK_DEMO_1);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            this.database.taskDao().insertTask(TASK_DEMO_2);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.database.taskDao().insertTask(TASK_DEMO_3);
    }

    private Boolean tasksSortedAlphabetically(List<Task> tasks) {
        return tasks.get(0).getName().equals(TASK_DEMO_NAME_2) &&
                tasks.get(1).getName().equals(TASK_DEMO_NAME_1) &&
                tasks.get(2).getName().equals(TASK_DEMO_NAME_3);
    }

    private boolean tasksSortedAlphabeticallyInverted(List<Task> tasks) {
        return tasks.get(0).getName().equals(TASK_DEMO_NAME_3) &&
                tasks.get(1).getName().equals(TASK_DEMO_NAME_1) &&
                tasks.get(2).getName().equals(TASK_DEMO_NAME_2);
    }

    private boolean tasksSortedOldFirst(List<Task> tasks) {
        return tasks.get(0).getName().equals(TASK_DEMO_NAME_1) &&
                tasks.get(1).getName().equals(TASK_DEMO_NAME_2) &&
                tasks.get(2).getName().equals(TASK_DEMO_NAME_3);
    }

    private boolean tasksSortedRecentFirst(List<Task> tasks) {
        return tasks.get(0).getName().equals(TASK_DEMO_NAME_1) &&
                tasks.get(1).getName().equals(TASK_DEMO_NAME_2) &&
                tasks.get(2).getName().equals(TASK_DEMO_NAME_3);
    }
}
