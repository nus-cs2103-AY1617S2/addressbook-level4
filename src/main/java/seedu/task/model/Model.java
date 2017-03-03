package seedu.task.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.Task;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the AddressBook */
    ReadOnlyTaskBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Task person) throws UniqueTaskList.DuplicatePersonException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updatePerson(int filteredPersonListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
