package com.cleanup.todoc.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDocDatabase extends RoomDatabase {

    private static volatile ToDocDatabase INSTANCE;

    public abstract TaskDao taskDao();

    public static ToDocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDocDatabase.class, "todoc.db")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
