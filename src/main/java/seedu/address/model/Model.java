package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addTask(Task person) throws UniqueTaskList.DuplicatePersonException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updatePerson(int filteredPersonListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicatePersonException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    //@@author A0164889E
    /** Returns the filtered task complete list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonListComplete();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords for FindCommand*/
    void updateFilteredPersonList(Set<String> keywords);

    //@@author A0164889E
    /** Updates the filter of the filtered task list to filter by the given keywords for GroupCommand*/
    void updateFilteredPersonListGroup(Set<String> keywords);

    //@@author A0164466X
    /** Updates the filter of the filtered person list to show all complete tasks */
    void updateFilteredListToShowComplete();

    /** Updates the filter of the filtered person list to show all incomplete tasks */
    void updateFilteredListToShowIncomplete();

    //@@author A0163848R

    /** Undoes the last modification made to the AddressBook. Returns if there is anything to undo. */
    boolean undoLastModification();

    /** Redoes the last modification made to the AddressBook. Returns if there is anything to redo. */
    boolean redoLastModification();

    /** Adds the current AddressBook state to the undo/redo history */
    void addToHistory(ReadOnlyTaskManager state);

    /** Adds entries from the given YTomorrow to the current YTomorrow and updates equivalent entries. */
    void mergeYTomorrow(ReadOnlyTaskManager add);
}
