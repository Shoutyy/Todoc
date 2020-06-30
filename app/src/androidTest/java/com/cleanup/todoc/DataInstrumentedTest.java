package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataInstrumentedTest {

    // FOR DATA
    private SaveMyTripDatabase database; //R

    public static Project[] getAllProjects() {
        return new Project[]{
                new Project(4L, "Projet 4", 0xEFEADAD1),
        };
    }
    //private static Project Project1 = new Project(4L, "Projet 4", 0xEFEADAD1);
    private static Task Task1 = new Task(1, 4L, "Menage" ,12/23/2020);
    private static Task Task2 = new Task(2, 4L, "Jardinage" ,12/25/2020);
    private static Task Task3 = new Task(3, 4L, "Babysitter" ,12/27/2020);

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before //R
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                SaveMyTripDatabase.class)
                .allowMainThreadQueries()
                .build();
    }


    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> Tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(Tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {

        // BEFORE : Adding demo project & demo task
        this.database.projectDao().insertProject(getAllProjects());
        this.database.taskDao().insertTask(Task1);
        this.database.taskDao().insertTask(Task2);
        this.database.taskDao().insertTask(Task3);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 3);
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo project & demo task. Next, get the item added & delete it.
        this.database.projectDao().insertProject(getAllProjects());
        this.database.taskDao().insertTask(Task1);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @After //R
    public void closeDb() throws Exception {
        database.close();
    }
}

