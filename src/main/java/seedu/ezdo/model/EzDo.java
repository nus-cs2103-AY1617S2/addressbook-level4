package seedu.ezdo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.parser.DateParser;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Wraps all data at the ezDo level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EzDo implements ReadOnlyEzDo {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public EzDo() {}

    /**
     * Creates an EzDo using the Tasks and Tags in the {@code toBeCopied}
     */
    public EzDo(ReadOnlyEzDo toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void sortTasks(SortCriteria sortCriteria, Boolean isSortedAscending) {
        tasks.sortTasks(sortCriteria, isSortedAscending);
    }

    public void resetData(ReadOnlyEzDo newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "EzDo should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "EzDo should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

//// task-level operations

    /**
     * Adds a task to ezDo.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(p);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code EzDo}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    //@@author A0139248X
    /**
     * Removes the tasks in {@code tasksToKill} from ezDo.
     *
     * @throws UniqueTaskList.TaskNotFoundException if the task is not found
     */
    public boolean removeTasks(ArrayList<ReadOnlyTask> tasksToKill) throws UniqueTaskList.TaskNotFoundException {
        for (int i = 0; i < tasksToKill.size(); i++) {
            tasks.remove(tasksToKill.get(i));
        }
        return true;
    }

    /**
     * Toggles the tasks done status in {@code p}.
     */
    public void toggleTasksDone(ArrayList<Task> p) {
        for (int i = 0; i < p.size(); i++) {
            Task task = p.get(i);
            updateRecurringDates(task);
            moveCurrentTaskToDone(task);
        }
    }

    //@@author A0139177W
    public void toggleTasksSelect(ArrayList<Task> p) {
        for (int i = 0; i < p.size(); i++) {
            Task task = p.get(i);
            task.toggleStart();
        }
    }

    private void moveCurrentTaskToDone(Task task) {
        try {
            task.setRecur(new Recur(""));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        task.toggleDone();
    }

    private void updateRecurringDates(Task task) {

        if (task.getRecur().isRecur()) {
            try {
                String recurIntervalInString = task.getRecur().toString().trim();
                int recurringInterval = Recur.RECUR_INTERVALS.get(recurIntervalInString);

                String startDateInString = task.getStartDate().value;
                String dueDateInString = task.getDueDate().value;

                String startDate = updateDate(recurringInterval, startDateInString);
                String dueDate = updateDate(recurringInterval, dueDateInString);

                tasks.add(new Task(task.getName(), task.getPriority(), new StartDate(startDate),
                        new DueDate(dueDate), task.getRecur(), task.getTags()));

            } catch (IllegalValueException ive) {
                // Do nothing as the date is optional
                // and cannot be parsed as Date object
                ive.printStackTrace();
            }
        }
    }

    private String updateDate(int type, String originalDate) {
        try {
            int recurIntervalIncrement = 1;
            Calendar c = Calendar.getInstance();
            c.setTime(DateParser.USER_OUTPUT_DATE_FORMAT.parse(originalDate));
            c.add(type, recurIntervalIncrement);
            return DateParser.USER_OUTPUT_DATE_FORMAT.format(c.getTime());
        } catch (ParseException pe) {
            // Do nothing as the date is optional
            // and cannot be parsed as Date object
            pe.printStackTrace();
        }
        return originalDate;
    }
    // @@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EzDo // instanceof handles nulls
                && this.tasks.equals(((EzDo) other).tasks)
                && this.tags.equalsOrderInsensitive(((EzDo) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

}
