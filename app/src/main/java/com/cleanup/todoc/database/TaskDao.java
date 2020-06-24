package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(long projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(Task task);
    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTask(Task task);
    */
    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}
