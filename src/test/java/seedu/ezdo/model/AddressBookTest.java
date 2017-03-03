package seedu.ezdo.model;

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
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.testutil.TypicalTestTasks;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EzDo ezDo = new EzDo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ezDo.getTaskList());
        assertEquals(Collections.emptyList(), ezDo.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        ezDo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EzDo newData = new TypicalTestTasks().getTypicalAddressBook();
        ezDo.resetData(newData);
        assertEquals(newData, ezDo);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newPersons = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        AddressBookStub newData = new AddressBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        EzDo typicalAddressBook = new TypicalTestTasks().getTypicalAddressBook();
        List<ReadOnlyTask> newPersons = typicalAddressBook.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalAddressBook.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        AddressBookStub newData = new AddressBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyEzDo {
        private final ObservableList<ReadOnlyTask> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyTask> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
