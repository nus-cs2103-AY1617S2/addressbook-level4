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
import savvytodo.model.TaskManager;
import savvytodo.model.category.Category;
import savvytodo.model.ReadOnlyTaskManager;
import savvytodo.model.task.Task;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.testutil.TypicalTestTasks;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager addressBook = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
        assertEquals(Collections.emptyList(), addressBook.getCategoryList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Category> newCategories = td.alice.getCategories().asObservableList();
        AddressBookStub newData = new AddressBookStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateCategories_throwsAssertionError() {
        TaskManager typicalAddressBook = new TypicalTestTasks().getTypicalAddressBook();
        List<ReadOnlyTask> newTasks = typicalAddressBook.getTaskList();
        List<Category> newCategories = new ArrayList<>(typicalAddressBook.getCategoryList());
        // Repeat the first category twice
        newCategories.add(newCategories.get(0));
        AddressBookStub newData = new AddressBookStub(newTasks, newCategories);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyAddressBook whose tasks and categories lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Category> categories = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Category> categories) {
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
