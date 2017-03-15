package seedu.jobs.model;

import java.util.Set;

import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.model.task.Person;
import seedu.jobs.model.task.ReadOnlyPerson;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.UniqueTaskList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person task) throws UniqueTaskList.DuplicatePersonException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updatePerson(int filteredPersonListIndex, ReadOnlyPerson editedPerson)
            throws UniqueTaskList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
