package seedu.ezdo.model.todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.exceptions.DuplicateDataException;
import seedu.ezdo.commons.util.CollectionUtil;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task that is not done as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        boolean containsDuplicate = false;
        for (int i = 0; i < internalList.size(); i++) {
            ReadOnlyTask taskToCheck = internalList.get(i);
            containsDuplicate = (taskToCheck.equals(toCheck) && !taskToCheck.getDone())
                                                             || containsDuplicate;
        }
        return containsDuplicate;
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndKilled = internalList.remove(toRemove);
        if (!taskFoundAndKilled) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndKilled;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            Task toAdd = new Task(task);
            toAdd.setDone(task.getDone());
            replacement.add(toAdd);
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Task> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    public enum SortCriteria {
        NAME, START_DATE, DUE_DATE, PRIORITY
    }

    /**
     * Sorts the internal list of tasks by the criteria specified.
     *
     * @param sortCriteria A constant that represents the sorting criteria.
     */
    public void sortTasks(SortCriteria sortCriteria) {
        Comparator<Task> taskComparator = null;
        switch (sortCriteria) {
        case NAME:
            taskComparator = new Comparator<Task>() {
                @Override
                public int compare(Task taskOne, Task taskTwo) {
                    String taskOneName = taskOne.getName().toString();
                    String taskTwoName = taskTwo.getName().toString();
                    return taskOneName.compareToIgnoreCase(taskTwoName);
                }
            };
            break;
        case START_DATE:
            taskComparator = new Comparator<Task>() {
                @Override
                public int compare(Task taskOne, Task taskTwo) {
                    String taskOneStartDate = taskOne.getStartDate().toString();
                    String taskTwoStartDate = taskTwo.getStartDate().toString();
                    return compareDateStrings(taskOneStartDate, taskTwoStartDate);
                }
            };
            break;
        case DUE_DATE:
            taskComparator = new Comparator<Task>() {
                @Override
                public int compare(Task taskOne, Task taskTwo) {
                    String taskOneDueDate = taskOne.getDueDate().toString();
                    String taskTwoDueDate = taskTwo.getDueDate().toString();
                    return compareDateStrings(taskOneDueDate, taskTwoDueDate);
                }
            };
            break;

        case PRIORITY:
            taskComparator = new Comparator<Task>() {
                @Override
                public int compare(Task taskOne, Task taskTwo) {
                    String taskOnePriority = taskOne.getPriority().toString();
                    String taskTwoPriority = taskTwo.getPriority().toString();

                    // treat no priority as the lowest value
                    if (taskOnePriority.isEmpty() && taskTwoPriority.isEmpty()) {
                        return 0;
                    } else if (taskOnePriority.isEmpty()) {
                        return 1;
                    } else if (taskTwoPriority.isEmpty()) {
                        return -1;
                    }

                    return taskOnePriority.compareTo(taskTwoPriority);
                }
            };
            break;
        default:
            assert false : "Unsupported sort criteria should not be used";
        }
        assert taskComparator != null;
        FXCollections.sort(internalList, taskComparator);
    }

    /**
     * Compares two dates strings. Both strings must be in the format dd/MM/yyyy hh:mm.
     * Empty strings are considered to be of lower value than non-empty strings.
     * @return an int representing the comparison result of the two date strings.
     * @throws ParseException if any of the date strings cannot be parsed.
     */
    private int compareDateStrings(String dateString1, String dateString2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date1 = null;
        Date date2 = null;

        // empty dates are considered lower in value so that they show at the bottom of the list
        if (dateString1.isEmpty() && dateString2.isEmpty()) {
            return 0;
        } else if (dateString1.isEmpty()) {
            return 1;
        } else if (dateString2.isEmpty()) {
            return -1;
        }

        try {
            date1 = dateFormat.parse(dateString1);
            date2 = dateFormat.parse(dateString2);
        } catch (ParseException pe) {
            assert false : "The date format should not be invalid.";
        }

        return date1.compareTo(date2);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

}
