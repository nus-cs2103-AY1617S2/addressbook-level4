package savvytodo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import savvytodo.model.category.Category;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Task;
import savvytodo.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getCategoryList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskManager_replacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.presentation twice
        List<Task> newTasks = Arrays.asList(new Task(td.presentation), new Task(td.presentation));
        List<Category> newCategories = td.presentation.getCategories().asObservableList();
        TaskManagerStub newData = new TaskManagerStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateCategories_throwsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        List<ReadOnlyTask> newTasks = typicalTaskManager.getTaskList();
        List<Category> newCategories = new ArrayList<>(typicalTaskManager.getCategoryList());
        // Repeat the first category twice
        newCategories.add(newCategories.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and categories lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Category> categories = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Category> categories) {
            this.tasks.setAll(tasks);
            this.categories.setAll(categories);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Category> getCategoryList() {
            return categories;
        }
    }

}
