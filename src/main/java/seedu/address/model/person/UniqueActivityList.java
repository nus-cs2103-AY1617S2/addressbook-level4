package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(ReadOnlyActivity toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds an activity to the list.
     *
     * @throws DuplicateActivityException if the activity to add is a duplicate of an existing activity in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the activity in the list at position {@code index} with {@code editedActivity}.
     *
     * @throws DuplicateActivityException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateActivity(int index, ReadOnlyActivity editedActivity) throws DuplicateActivityException {
        assert editedActivity != null;

        Activity activityToUpdate = internalList.get(index);
        if (!activityToUpdate.equals(editedActivity) && internalList.contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        activityToUpdate.resetData(editedActivity);
        // TODO: The code below is just a workaround to notify observers of the updated activity.
        // The right way is to implement observable properties in the Activity class.
        // Then, ActivityCard should then bind its text labels to those observable properties.
        internalList.set(index, activityToUpdate);
    }

    /**
     * Removes the equivalent activity from the list.
     *
     * @throws ActivityNotFoundException if no such activity could be found in the list.
     */
    public boolean remove(ReadOnlyActivity toRemove) throws ActivityNotFoundException {
        assert toRemove != null;
        final boolean activityFoundAndDeleted = internalList.remove(toRemove);
        if (!activityFoundAndDeleted) {
            throw new ActivityNotFoundException();
        }
        return activityFoundAndDeleted;
    }

    public void setActivities(UniqueActivityList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setActivities(List<? extends ReadOnlyActivity> activities) throws DuplicateActivityException {
        final UniqueActivityList replacement = new UniqueActivityList();
        for (final ReadOnlyActivity activity : activities) {
            replacement.add(new Activity(activity));
        }
        setActivities(replacement);
    }

    public UnmodifiableObservableList<Activity> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateActivityException extends DuplicateDataException {
        protected DuplicateActivityException() {
            super("Operation would result in duplicate activities");
        }
    }

    /**
     * Signals that an operation targeting a specified activity in the list would fail because
     * there is no such matching activity in the list.
     */
    public static class ActivityNotFoundException extends Exception {}

}
