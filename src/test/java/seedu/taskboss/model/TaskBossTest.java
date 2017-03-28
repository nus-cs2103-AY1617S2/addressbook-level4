package seedu.taskboss.model;

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
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.testutil.TypicalTestTasks;

public class TaskBossTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskBoss taskBoss = new TaskBoss();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBoss.getTaskList());
        assertEquals(Collections.emptyList(), taskBoss.getCategoryList());
    }

    @Test
    public void resetData_null_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        taskBoss.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskBoss_replacesData() throws IllegalValueException {
        TaskBoss newData = new TypicalTestTasks().getTypicalTaskBoss();
        taskBoss.resetData(newData);
        assertEquals(newData, taskBoss);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() throws IllegalValueException {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.taskA), new Task(td.taskA));
        List<Category> newCategories = td.taskA.getCategories().asObservableList();
        TaskBossStub newData = new TaskBossStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        taskBoss.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateCategories_throwsAssertionError() throws IllegalValueException {
        TaskBoss typicalTaskBoss = new TypicalTestTasks().getTypicalTaskBoss();
        List<ReadOnlyTask> newTasks = typicalTaskBoss.getTaskList();
        List<Category> newCategories = new ArrayList<>(typicalTaskBoss.getCategoryList());
        // Repeat the first category twice
        newCategories.add(newCategories.get(0));
        TaskBossStub newData = new TaskBossStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        taskBoss.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskBoss whose tasks and categories lists can violate interface constraints.
     */
    private static class TaskBossStub implements ReadOnlyTaskBoss {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Category> categories = FXCollections.observableArrayList();

        TaskBossStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Category> categories) {
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
