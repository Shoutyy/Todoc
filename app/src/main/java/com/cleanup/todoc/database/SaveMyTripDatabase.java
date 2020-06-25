package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Database;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyTripDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // --- INSTANCE ---
    public static SaveMyTripDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyTripDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTripDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
        /*
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 2L);
                contentValues.put("name", "Projet Lucidia");
                contentValues.put("color", 0xFFB4CDBA);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);


                contentValues.put("id", 1L);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);
                contentValues.put("id", 3L);
                contentValues.put("name", "Projet Circus");
                contentValues.put("color", 0xFFA3CED2);



                ContentValues contentValues2 = new ContentValues();
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                */
            }
        };
    }
}
