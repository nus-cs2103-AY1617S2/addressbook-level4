package seedu.taskmanager.model.task;

import static seedu.taskmanager.logic.commands.SortCommand.SORT_KEYWORD_STARTDATE;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.exceptions.DuplicateDataException;
import seedu.taskmanager.commons.util.CollectionUtil;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // @@author A0131278H
    public static final String KEYWORD_UNDEFINED = "undefined";
    private String sortCriterion = KEYWORD_UNDEFINED;
    // @@author

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    // @@author A0131278H
    /**
     * Adds a task to the list. Resort the list if it was already sorted.
     *
     * @return index of new task if list is sorted or size-1 if list in unsorted
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        // @@author A0131278H
        if (!sortCriterion.equals(KEYWORD_UNDEFINED)) {
            sortByDate(sortCriterion);
        }
        // @@author
    }

    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedTask}. Resort the list if it was already sorted.
     *
     * @return updatedIndex if the internal list is sorted or input index if
     *         list is unsorted
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the
        // updated task.
        // The right way is to implement observable properties in the Task
        // class.
        // Then, TaskCard should then bind its text labels to those observable
        // properties.
        internalList.set(index, taskToUpdate);
        // @@author A0131278H
        if (!sortCriterion.equals(KEYWORD_UNDEFINED)) {
            sortByDate(sortCriterion);
        }
        // @@author
    }

    // @@author

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Task> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    // @@author A0131278H

    /**
     * Sorts task list based on keywords (StartDate or EndDate). Tasks without
     * start StartDate or EndDate are ranked at the bottom.
     */
    public void sortByDate(String keyword) {
        this.sortCriterion = keyword;
        internalList.sort(new DateComparator(sortCriterion));
    }

    /**
     * Filters task list based on task status (Done or Not Done).
     */
    public ObservableList<Task> getTaskListByStatus(String status) {
        if (status.equals(Status.STATUS_DONE)) {
            return internalList.filtered(StatusPredicate.isDone());
        } else if (status.equals(Status.STATUS_NOT_DONE)) {
            return internalList.filtered(StatusPredicate.isNotDone());
        } else {
            return internalList; // return list of all task if status is invalid
        }
    }

    static class StatusPredicate {

        public static Predicate<Task> isDone() {
            return p -> p.getStatus().toString().equals(Status.STATUS_DONE);
        }

        public static Predicate<Task> isNotDone() {
            return p -> p.getStatus().toString().equals(Status.STATUS_NOT_DONE);
        }
    }

    // @@author A0114523U
    /**
     * Filters task list based on end date (overdue or due today).
     */
    public ObservableList<Task> getTaskListByDate(Date endDate) {
        if (endDate.before(EndDate.today)) {
            return internalList.filtered(DatePredicate.isOverdue());
        } else if (endDate.equals(EndDate.today)) {
            return internalList.filtered(DatePredicate.isToday());
    	} else {
    	    return internalList;
    	}
    }

    static class DatePredicate {
    	public static Predicate<Task> isOverdue() {
            return p -> p.getEndDate().equals(EndDate.today);
    	}

    	public static Predicate<Task> isToday() {
            return p -> p.getEndDate().equals(EndDate.today);
    	}
    }
    // @@author

    class DateComparator implements Comparator<Task> {
        String sortCriterion;

        public DateComparator(String sortCriterion) {
            this.sortCriterion = sortCriterion;
        }

        @Override
        public int compare(Task t1, Task t2) {
            if (sortCriterion.equals(SORT_KEYWORD_STARTDATE)) {
                return this.compareByStartDate(t1, t2);
            } else {
                return this.compareByEndDate(t1, t2);
            }
        }

        private int compareByStartDate(Task t1, Task t2) {
            if (t1.getStartDate().isPresent() && t2.getStartDate().isPresent()) {
                return t1.getStartDate().get().compareTo(t2.getStartDate().get());
            }
            // @@author A0140032E
            if (!t1.getStartDate().isPresent() && !t2.getStartDate().isPresent()) {
                return 0;
            } else if (t1.getStartDate().isPresent()) {
                return -1;
            } else {
                return 1;
            }
            // @@author A0131278H
        }

        private int compareByEndDate(Task t1, Task t2) {
            if (t1.getEndDate().isPresent() && t2.getEndDate().isPresent()) {
                return t1.getEndDate().get().compareTo(t2.getEndDate().get());
            }
            // @@author A0140032E
            if (!t1.getEndDate().isPresent() && !t2.getEndDate().isPresent()) {
                return 0;
            } else if (t1.getEndDate().isPresent()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
