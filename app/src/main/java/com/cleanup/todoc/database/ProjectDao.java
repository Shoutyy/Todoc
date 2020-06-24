package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProjects(long projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProject(Project project);
}
