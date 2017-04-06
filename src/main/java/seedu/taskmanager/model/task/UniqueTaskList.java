package seedu.taskmanager.model.task;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.exceptions.DuplicateDataException;
import seedu.taskmanager.commons.util.CollectionUtil;

/**
 * A list of Tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    // @@author A0142418L
    /**
     * Adds a task to the list.
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

        internalList.add(findSortedPositionToAdd(toAdd), toAdd);
    }

    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedTask}.
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
        // Then, PersonCard should then bind its text labels to those observable
        // properties.

        // internalList.set(index, taskToUpdate);

        internalList.remove(index);
        internalList.add(findSortedPositionToAdd(taskToUpdate), taskToUpdate);
    }

    // @@author A0139520L
    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void markTask(int index, boolean isComplete) throws DuplicateTaskException {
        if (isComplete != internalList.get(index).getIsMarkedAsComplete()) {

            Task taskToMark = new Task(internalList.get(index).getTaskName(), internalList.get(index).getStartDate(),
                    internalList.get(index).getStartTime(), internalList.get(index).getEndDate(),
                    internalList.get(index).getEndTime(), internalList.get(index).getIsMarkedAsComplete(),
                    internalList.get(index).getCategories());

            taskToMark.setIsMarkedAsComplete(isComplete);

            if (!isComplete && internalList.contains(taskToMark)) {
                throw new DuplicateTaskException();
            }

            // TODO: The code below is just a workaround to notify observers of
            // the
            // updated task.
            // The right way is to implement observable properties in the Task
            // class.
            // Then, PersonCard should then bind its text labels to those
            // observable
            // properties.
            internalList.set(index, taskToMark);
        } else {
            throw new NoSuchElementException();
        }
    }

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

    // @@author A0142418L
    /**
     * Compares the starting date and time of 2 event tasks.
     *
     * @return true if 1st event task is earlier than the 2nd event task based
     *         on the startDate and startTime
     * @return false, if otherwise.
     */
    private boolean isAddEventEarlierAddListIndex(Task toAdd, ReadOnlyTask readOnlyTask) {
        if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                readOnlyTask.getStartDate().value.substring(readOnlyTask.getStartDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getStartDate().value.substring(toAdd.getStartDate().value.length() - 2).compareTo(
                    readOnlyTask.getStartDate().value.substring(readOnlyTask.getStartDate().value.length() - 2)) == 0) {
                if (toAdd.getStartDate().value
                        .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                        .compareTo(readOnlyTask.getStartDate().value.substring(
                                readOnlyTask.getStartDate().value.length()
                                        - 5,
                                readOnlyTask.getStartDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getStartDate().value
                            .substring(toAdd.getStartDate().value.length() - 5, toAdd.getStartDate().value.length() - 3)
                            .compareTo(readOnlyTask.getStartDate().value.substring(
                                    readOnlyTask.getStartDate().value.length()
                                            - 5,
                                    readOnlyTask.getStartDate().value.length() - 3)) == 0) {
                        if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                .compareTo(readOnlyTask.getStartDate().value.substring(0,
                                        readOnlyTask.getStartDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getStartDate().value.substring(0, toAdd.getStartDate().value.length() - 6)
                                    .compareTo(readOnlyTask.getStartDate().value.substring(0,
                                            readOnlyTask.getStartDate().value.length() - 6)) == 0) {
                                if (toAdd.getStartTime().value.compareTo(readOnlyTask.getStartTime().value) < 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Compares the due date of 2 deadline tasks.
     *
     * @return true if 1st deadline task is earlier than the 2nd deadline task
     *         based on the endDate and endTime
     * @return false, if otherwise.
     */
    private boolean isAddDeadlineEarlierAddListIndex(Task toAdd, ReadOnlyTask readOnlyTask) {
        if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2).compareTo(
                readOnlyTask.getEndDate().value.substring(readOnlyTask.getEndDate().value.length() - 2)) < 0) {
            return true;
        } else {
            if (toAdd.getEndDate().value.substring(toAdd.getEndDate().value.length() - 2).compareTo(
                    readOnlyTask.getEndDate().value.substring(readOnlyTask.getEndDate().value.length() - 2)) == 0) {
                if (toAdd.getEndDate().value
                        .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                        .compareTo(
                                readOnlyTask.getEndDate().value.substring(readOnlyTask.getEndDate().value.length() - 5,
                                        readOnlyTask.getEndDate().value.length() - 3)) < 0) {
                    return true;
                } else {
                    if (toAdd.getEndDate().value
                            .substring(toAdd.getEndDate().value.length() - 5, toAdd.getEndDate().value.length() - 3)
                            .compareTo(readOnlyTask.getEndDate().value.substring(
                                    readOnlyTask.getEndDate().value.length()
                                            - 5,
                                    readOnlyTask.getEndDate().value.length() - 3)) == 0) {
                        if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6)
                                .compareTo(readOnlyTask.getEndDate().value.substring(0,
                                        readOnlyTask.getEndDate().value.length() - 6)) < 0) {
                            return true;
                        } else {
                            if (toAdd.getEndDate().value.substring(0, toAdd.getEndDate().value.length() - 6)
                                    .compareTo(readOnlyTask.getEndDate().value.substring(0,
                                            readOnlyTask.getEndDate().value.length() - 6)) == 0) {
                                if (toAdd.getEndTime().value.compareTo(readOnlyTask.getEndTime().value) < 0) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Finds the sorted position to add new task to the existing list of task.
     * List of tasks is sorted firstly based on type of task and then by chronological order of the task
     *
     * Event tasks sorted by startDate startTime.
     * Deadline tasks sorted by endDate endTime.
     * Floating tasks are just added to the bottom of the list as there is no time element within a floating task.
     *
     * @return The sorted position index to add the new task in the sorted list
     *         of tasks.
     */
    private int findSortedPositionToAdd(Task toAdd) {
        int addIndex = 0;
        if (!internalList.isEmpty()) {
            if (toAdd.isEventTask()) {
                while (internalList.get(addIndex).isEventTask()) {
                    if (isAddEventEarlierAddListIndex(toAdd, internalList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == internalList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isDeadlineTask()) {
                while (internalList.get(addIndex).isEventTask()) {
                    addIndex++;
                    if (addIndex == internalList.size()) {
                        break;
                    }
                }
                while ((addIndex != internalList.size()) && internalList.get(addIndex).isDeadlineTask()) {
                    if (isAddDeadlineEarlierAddListIndex(toAdd, internalList.get(addIndex))) {
                        break;
                    }
                    addIndex++;
                    if (addIndex == internalList.size()) {
                        break;
                    }
                }
            }

            if (toAdd.isFloatingTask()) {
                addIndex = internalList.size();
            }
        }
        return addIndex;
    }
}
