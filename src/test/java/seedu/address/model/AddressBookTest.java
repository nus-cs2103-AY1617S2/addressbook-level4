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
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalTestPersons;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getLabelList());
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
        List<Person> newPersons = Arrays.asList(new Person(td.alice), new Person(td.alice));
        List<Label> newLabels = td.alice.getLabels().asObservableList();
        AddressBookStub newData = new AddressBookStub(newPersons, newLabels);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateLabels_throwsAssertionError() {
        AddressBook typicalAddressBook = new TypicalTestPersons().getTypicalAddressBook();
        List<ReadOnlyPerson> newPersons = typicalAddressBook.getPersonList();
        List<Label> newLabels = new ArrayList<>(typicalAddressBook.getLabelList());
        // Repeat the first label twice
        newLabels.add(newLabels.get(0));
        AddressBookStub newData = new AddressBookStub(newPersons, newLabels);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and labels lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Label> labels = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Label> labels) {
            this.persons.setAll(persons);
            this.labels.setAll(labels);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Label> getLabelList() {
            return labels;
        }
    }

}
