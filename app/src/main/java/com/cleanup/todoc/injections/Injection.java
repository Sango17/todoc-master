package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static TaskDataRepository provideTaskDataSource(Context context) {
        ToDocDatabase database = ToDocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        Executor executor = provideExecutor();

        return new ViewModelFactory(dataSourceTask, executor);
    }
}
