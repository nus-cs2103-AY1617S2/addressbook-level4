package seedu.address.model;

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
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTestPersons;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getLabelList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskManager newData = new TypicalTestPersons().getTypicalAddressBook();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        TypicalTestPersons td = new TypicalTestPersons();
        // Repeat td.alice twice
        List<Task> newPersons = Arrays.asList(new Task(td.task1), new Task(td.task1));
        List<Label> newLabels = td.task1.getLabels().asObservableList();
        AddressBookStub newData = new AddressBookStub(newPersons, newLabels);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateLabels_throwsAssertionError() {
        TaskManager typicalAddressBook = new TypicalTestPersons().getTypicalAddressBook();
        List<ReadOnlyTask> newPersons = typicalAddressBook.getTaskList();
        List<Label> newLabels = new ArrayList<>(typicalAddressBook.getLabelList());
        // Repeat the first label twice
        newLabels.add(newLabels.get(0));
        AddressBookStub newData = new AddressBookStub(newPersons, newLabels);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and labels lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> persons = FXCollections.observableArrayList();
        private final ObservableList<Label> labels = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyTask> persons, Collection<? extends Label> labels) {
            this.persons.setAll(persons);
            this.labels.setAll(labels);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return persons;
        }

        @Override
        public ObservableList<Label> getLabelList() {
            return labels;
        }
    }

}
