package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

public interface TaskDao {
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(long projectId);

    @Insert
    long insertItem(Task task);

    @Update
    int updateItem(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteItem(long taskId);
}
