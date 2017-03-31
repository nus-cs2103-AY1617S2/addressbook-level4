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
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the activity-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class WhatsLeft implements ReadOnlyWhatsLeft {

    private final UniqueEventList events;
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
        events = new UniqueEventList();
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public WhatsLeft() {}

    /**
     * Creates an WhatsLeft using the Activities and Tags in the {@code toBeCopied}
     */
    public WhatsLeft(ReadOnlyWhatsLeft toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setEvents(List<? extends ReadOnlyEvent> events)
            throws IllegalValueException {
        this.events.setEvents(events);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyWhatsLeft newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "WhatsLeft should not have duplicate tasks";
        }
        try {
            setEvents(newData.getEventList());
        } catch (UniqueEventList.DuplicateEventException e) {
            assert false : "WhatsLeft should not have duplicate events";
        } catch (IllegalValueException e) {
            assert false : "WhatsLeft should not have events with start date after end date";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "WhatsLeft should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
        syncMasterTagListWith(events);
    }

    //@@author A0148038A
    public void resetEventData() {
        events.clearAll();
    }

    public void resetTaskData() {
        tasks.clearAll();
    }

//// activity-level operations

    /**
     * Adds a task to WhatsLeft.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(t);
        tasks.add(t);
    }

    /**
     * Adds a event to WhatsLeft.
     * Also checks the new event's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the event to point to those in {@link #tags}.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     * @throws DuplicateTimeClashException if another event with time that clashes exist.
     */
    public void addEvent(Event e) throws UniqueEventList.DuplicateEventException {
        syncMasterTagListWith(e);
        events.add(e);
    }

    //@@author A0148038A
    /**
     * Updates the event in the list at position {@code index} with {@code editedReadOnlyEvent}.
     * {@code WhatsLeft}'s tag list will be updated with the tags of {@code editedReadOnlyEvent}.
     * @see #syncMasterTagListWith(Event)
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws DuplicateTimeClashException if the updating event clashes with another event
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(Event eventToEdit, Event editedEvent)
            throws UniqueEventList.DuplicateEventException {
        assert editedEvent != null;

        syncMasterTagListWith(editedEvent);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any event
        // in the event list.
        events.updateEvent(eventToEdit, editedEvent);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code WhatsLeft}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(Task taskToEdit, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert taskToEdit != null;

        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.updateTask(taskToEdit, editedTask);
    }

    //@@author
    /**
     * Marks the task in the list at position {@code index} as complete.
     */
    public void completeTask(ReadOnlyTask taskToMark) {
        tasks.completeTask(taskToMark);
    }

    /**
     * Marks the task in the list at position {@code index} as pending.
     */
    public void redoTask(ReadOnlyTask taskToMark) {
        tasks.RedoTask(taskToMark);

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
     * Ensures that every tag in this event:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Event event) {
        final UniqueTagList eventTags = event.getTags();
        tags.mergeFrom(eventTags);

        // Create map with values = tag object references in the master list
        // used for checking event tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of event tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        eventTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        event.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    /**
     * Ensures that every tag in these events:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Event)
     */
    private void syncMasterTagListWith(UniqueEventList events) {
        events.forEach(this::syncMasterTagListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public boolean removeEvent(ReadOnlyEvent key) throws UniqueEventList.EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueEventList.EventNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + events.asObservableList().size() + " events, "
                                                        + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return new UnmodifiableObservableList<>(events.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WhatsLeft // instanceof handles nulls
                && this.tasks.equals(((WhatsLeft) other).tasks)
                && this.events.equals(((WhatsLeft) other).events)
                && this.tags.equalsOrderInsensitive(((WhatsLeft) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, events, tags);
    }

    public ObservableList<Event> getEvents() {
        return events.getInternalList();
    }

    //@@author A0110491U
    public boolean eventHasClash(Event toAddEvent) {
        return events.containsTimeClash(toAddEvent);
    }
}
