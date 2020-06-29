package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Database;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyTripDatabase INSTANCE;

    private static Context activity;

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // --- INSTANCE ---
    public static SaveMyTripDatabase getInstance(Context context) {
        activity = context.getApplicationContext();

        if (INSTANCE == null) {
            synchronized (SaveMyTripDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTripDatabase.class, "MyDatabase.db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            getInstance(context).projectDao().insertProject(Project.getAllProjects());
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    /*
    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1L);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);


                ContentValues contentValues2 = new ContentValues();
                contentValues.put("id", 2L);
                contentValues.put("name", "Projet Lucidia");
                contentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);

                ContentValues contentValues3 = new ContentValues();
                contentValues.put("id", 3L);
                contentValues.put("name", "Projet Circus");
                contentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);

            }
        };
    } */
    /*
    private static void fillWithStartingData(Context context) {
        TaskDao dao = getInstance(context).taskDao();

        JSONArray tasks = loadJSONArray(context);

        try {

            for (int i=0; i< tasks.length(); i++) {
                JSONObject task = tasks.getJSONObject(i);

                long taskID = id;
                long projectID =  projectid
                String taskName = task.getString("name");
                String projectName = task.getString("project");

                dao.insertTask(new Task(1, taskName, projectName, 10/20/2020));
            }
        } catch (JSONException e) {

        }
    }   */

    private static JSONArray loadJSONArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.tasks);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());

            return json.getJSONArray("data");
        } catch (IOException |  JSONException exeeption) {
            exeeption.printStackTrace();
        }
        return null;
    }
}
