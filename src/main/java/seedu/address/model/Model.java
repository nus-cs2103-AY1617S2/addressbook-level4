package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.UniqueActivityList;
import seedu.address.model.person.UniqueActivityList.DuplicateActivityException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatsLeft newData);

    /** Returns the WhatsLeft */
    ReadOnlyWhatsLeft getWhatsLeft();

    /** Deletes the given activity. */
    void deleteActivity(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given activity */
    void addActivity(Activity activity) throws UniqueActivityList.DuplicateActivityException;

    /**
     * Updates the activity located at {@code filteredActivityListIndex} with {@code editedActivity}.
     *
     * @throws DuplicateActivityException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws IndexOutOfBoundsException if {@code filteredActivityListIndex} < 0 or >= the size of the filtered list.
     */
    void updateActivity(int filteredActivityListIndex, ReadOnlyActivity editedActivity)
            throws UniqueActivityList.DuplicateActivityException;

    /** Returns the filtered activity list as an {@code UnmodifiableObservableList<ReadOnlyActivity>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredActivityList();

    /** Returns the filtered unscheduled task activity list as an
     * {@code UnmodifiableObservableList<ReadOnlyActivity>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredScheduledActivityList();

    /** Returns the filtered list of scheduled activity list as an
     * {@code UnmodifiableObservableList<ReadOnlyActivity>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredUnscheduledActivityList();

    /** Updates the filter of the filtered activity list to show all activities */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered activity list to filter by the given keywords*/
    void updateFilteredActivityList(Set<String> keywords);

    UnmodifiableObservableList<ReadOnlyActivity> getFilteredEventList();

    UnmodifiableObservableList<ReadOnlyActivity> getFilteredDeadlineList();

}
