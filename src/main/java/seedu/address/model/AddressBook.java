package seedu.address.model;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setPersons(List<? extends ReadOnlyTask> persons)
            throws UniquePersonList.DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        assert newData != null;
        try {
            setPersons(newData.getPersonList());
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniquePersonList.DuplicatePersonException {
        persons.add(p);
    }

    /**
     * Updates the person in the list at position {@code index} with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updatePerson(int index, ReadOnlyTask editedReadOnlyPerson)
            throws UniquePersonList.DuplicatePersonException {
        assert editedReadOnlyPerson != null;

        Task editedPerson = new Task(editedReadOnlyPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.updatePerson(index, editedPerson);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Task)
     */
    public boolean removePerson(ReadOnlyTask key) throws UniquePersonList.PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }


//// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, ";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getPersonList() {
        return new UnmodifiableObservableList<>(persons.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons);
    }
}
