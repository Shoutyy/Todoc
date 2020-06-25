package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) { this.projectDao = projectDao; }

    // --- GET PROJECT ---
    public LiveData<Project> getProject() { return this.projectDao.getProject(); }
}
