package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTestPersons;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = new TypicalTestPersons().getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        TypicalTestPersons td = new TypicalTestPersons();
        // Repeat td.alice twice
        List<Task> newPersons = Arrays.asList(new Task(td.alice), new Task(td.alice));
        AddressBookStub newData = new AddressBookStub(newPersons);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }



    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyTask> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyTask> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<ReadOnlyTask> getPersonList() {
            return persons;
        }

    }

}
