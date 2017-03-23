package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueLabelList labels;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        labels = new UniqueLabelList();
    }

    public TaskManager() {}

    /**
     * Creates an TaskManager using the Tasks and Labels in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
        this.tasks.sortList();
    }

    public void setLabels(Collection<Label> labels) throws UniqueLabelList.DuplicateLabelException {
        this.labels.setLabels(labels);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task manager should not have duplicate tasks";
        }
        try {
            setLabels(newData.getLabelList());
        } catch (UniqueLabelList.DuplicateLabelException e) {
            assert false : "Task manager should not have duplicate tasks";
        }
        syncMasterLabelListWith(tasks);
        tasks.sortList();
    }

    public void undoData(ObservableList<ReadOnlyTask> oldTaskState, ObservableList<Label> oldLabelState) {
        tasks.removeAll();
        labels.removeAll();
        try {
            setTasks(oldTaskState);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task manager should not have duplicate tasks";
        }
        try {
            setLabels(oldLabelState);
        } catch (UniqueLabelList.DuplicateLabelException e) {
            assert false : "Task manager should not have duplicate tasks";
        }
        syncMasterLabelListWith(tasks);
        tasks.sortList();
    }

    //// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's labels and updates {@link #labels} with any new labels found,
     * and updates the Label objects in the tasks to point to those in {@link #labels}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterLabelListWith(p);
        tasks.add(p);
        tasks.sortList();
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code AddressBook}'s label list will be updated with the labels of {@code editedReadOnlyTask}.
     * @see #syncMasterLabelListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterLabelListWith(editedTask);
        // TODO: the labels master list will be updated even though the below line fails.
        // This can cause the labels master list to have additional labels that are not labeled to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
        tasks.sortList();
    }

    /**
     * Ensures that every label in this task:
     *  - exists in the master list {@link #labels}
     *  - points to a {@link Label} object in the master list
     */
    private void syncMasterLabelListWith(Task task) {
        final UniqueLabelList taskLabels = task.getLabels();
        labels.mergeFrom(taskLabels);

        // Create map with values = label object references in the master list
        // used for checking task label references
        final Map<Label, Label> masterLabelObjects = new HashMap<>();
        labels.forEach(label -> masterLabelObjects.put(label, label));

        // Rebuild the list of task labels to point to the relevant labels in the master label list.
        final Set<Label> correctLabelReferences = new HashSet<>();
        taskLabels.forEach(label -> correctLabelReferences.add(masterLabelObjects.get(label)));
        task.setLabels(new UniqueLabelList(correctLabelReferences));
    }

    /**
     * Ensures that every label in these tasks:
     *  - exists in the master list {@link #labels}
     *  - points to a Label object in the master list
     *  @see #syncMasterLabelListWith(Task)
     */
    private void syncMasterLabelListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterLabelListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //// label-level operations

    public void addLabel(Label t) throws UniqueLabelList.DuplicateLabelException {
        labels.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + labels.asObservableList().size() +  " labels";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        tasks.sortList();
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Label> getLabelList() {
        return new UnmodifiableObservableList<>(labels.asObservableList());
    }

    @Override
    public ObservableList<ReadOnlyTask> getImmutableTaskList() throws CloneNotSupportedException {
        UniqueTaskList oldTaskList = tasks.clone();
        return new UnmodifiableObservableList<>(oldTaskList.asObservableList());
    }

    @Override
    public ObservableList<Label> getImmutableLabelList() throws CloneNotSupportedException {
        UniqueLabelList oldLabelList = labels.clone();
        return new UnmodifiableObservableList<>(oldLabelList.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.tasks.equals(((TaskManager) other).tasks)
                        && this.labels.equalsOrderInsensitive(((TaskManager) other).labels));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, labels);
    }
}
