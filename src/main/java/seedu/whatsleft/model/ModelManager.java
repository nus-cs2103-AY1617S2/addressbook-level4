package seedu.whatsleft.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.whatsleft.commons.core.ComponentManager;
import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.model.ShowStatusChangedEvent;
import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.CollectionUtil;
import seedu.whatsleft.commons.util.StringUtil;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList;
import seedu.whatsleft.model.activity.UniqueEventList.EventNotFoundException;
import seedu.whatsleft.model.activity.UniqueTaskList;
import seedu.whatsleft.model.activity.UniqueTaskList.TaskNotFoundException;

//@@author A0148038A
/**
 * Represents the in-memory model of the WhatsLeft data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    public static final String DISPLAY_STATUS_COMPLETED = "COMPLETED";
    public static final String DISPLAY_STATUS_ALL = "ALL";
    public static final String DISPLAY_STATUS_PENDING = "PENDING";

    private final WhatsLeft whatsLeft;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private static String previousCommand;
    private static WhatsLeft previousState;
    private static String displayStatus;

    //@@author A0121668A
    /**
     * Initializes a ModelManager with the given whatsLeft and userPrefs.
     */
    public ModelManager(ReadOnlyWhatsLeft whatsLeft, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(whatsLeft, userPrefs);

        logger.fine("Initializing with WhatsLeft: " + whatsLeft + " and user prefs " + userPrefs);

        this.whatsLeft = new WhatsLeft(whatsLeft);
        previousState = new WhatsLeft();
        filteredEvents = new FilteredList<>(this.whatsLeft.getEventList());
        filteredTasks = new FilteredList<>(this.whatsLeft.getTaskList());
        previousCommand = "";
        displayStatus = DISPLAY_STATUS_PENDING;
        updateFilteredListToShowAll();
    }

    //@@author A0148038A
    public ModelManager() {
        this(new WhatsLeft(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWhatsLeft newData) {
        whatsLeft.resetData(newData);
        indicateWhatsLeftChanged();
    }

    @Override
    public void resetEvent() {
        whatsLeft.resetEventData();
        indicateWhatsLeftChanged();
    }

    @Override
    public void resetTask() {
        whatsLeft.resetTaskData();
        indicateWhatsLeftChanged();
    }

    @Override
    public ReadOnlyWhatsLeft getWhatsLeft() {
        return whatsLeft;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWhatsLeftChanged() {
        raise(new WhatsLeftChangedEvent(whatsLeft));
    }
    //@@author A0121668A
    /** Raises an event to indicate the show status has changed */
    private void indicateShowStatusChanged() {
        EventsCenter.getInstance().post(new ShowStatusChangedEvent(getDisplayStatus()));
    }

    @Override
    public void setDisplayStatus(String status) {
        displayStatus = status;
        updateFilteredListToShowAll();
        indicateShowStatusChanged();
    }

    @Override
    public String getDisplayStatus() {
        return displayStatus;
    }
    //@@author
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatsLeft.removeTask(target);
        indicateWhatsLeftChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        whatsLeft.removeEvent(target);
        indicateWhatsLeftChanged();
    }

    // @@author A0121668A
    @Override
    public synchronized void markTaskAsComplete(ReadOnlyTask taskToMark) throws TaskNotFoundException {
        whatsLeft.completeTask(taskToMark);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    @Override
    public synchronized void markTaskAsPending(ReadOnlyTask taskToMark) throws TaskNotFoundException {
        whatsLeft.redoTask(taskToMark);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    // @@author A0148038A
    /**
     * add an event to whatsleft
     *
     * @param an event to add
     * @throws DuplicateEventException to prevent duplicate events
     */
    @Override
    public synchronized void addEvent(Event event)
            throws UniqueEventList.DuplicateEventException {
        whatsLeft.addEvent(event);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    /**
     * add a task to whatsleft
     *
     * @param a task to add
     * @throws DuplicateTaskException to prevent duplicate tasks
     */
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatsLeft.addTask(task);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    /**
     * update/edit an event in whatsleft
     *
     * @param an event to edit and an edited event
     * @throws DuplicateEventException to prevent duplicate events
     */
    @Override
    public void updateEvent(Event eventToEdit, Event editedEvent)
            throws UniqueEventList.DuplicateEventException {
        assert editedEvent != null;

        whatsLeft.updateEvent(eventToEdit, editedEvent);
        indicateWhatsLeftChanged();
    }

    /**
     * update/edit a task in whatsleft
     *
     * @param a task to edit and an edited task
     * @throws DuplicateTaskException to prevent duplicate tasks
     */
    @Override
    public void updateTask(Task taskToEdit, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        whatsLeft.updateTask(taskToEdit, editedTask);
        indicateWhatsLeftChanged();
    }

    // @@author A0110491U
    public void storePreviousCommand(String command) {
        previousCommand = command;
    }

    public static String getPreviousCommand() {
        return previousCommand;
    }

    public static WhatsLeft getPreviousState() {
        return previousState;
    }

    public static void setPreviousState(ReadOnlyWhatsLeft state) {
        previousState = new WhatsLeft(state);
    }

    @Override
    public int findEventIndex(Event event) {
        int currIndex = 0;
        for (ReadOnlyEvent each : filteredEvents) {
            if (each.equals(event)) {
                return currIndex;
            } else {
                currIndex++;
            }
        }
        return currIndex;
    }

    @Override
    public int findTaskIndex(Task task) {
        int currIndex = 0;
        for (ReadOnlyTask each : filteredTasks) {
            if (each.equals(task)) {
                return currIndex;
            } else {
                currIndex++;
            }
        }
        return currIndex;
    }

    // =========== Filtered List Accessors

    // @@author A0148038A
    /**
     * get a sorted list of events
     */
    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        SortedList<ReadOnlyEvent> sortedEvents = new SortedList<ReadOnlyEvent>(filteredEvents);
        sortedEvents.setComparator(ReadOnlyEvent.getComparator());
        return new UnmodifiableObservableList<ReadOnlyEvent>(sortedEvents);
    }

    /**
     * get a sorte list of tasks
     */
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        SortedList<ReadOnlyTask> sortedTasks = new SortedList<ReadOnlyTask>(filteredTasks);
        sortedTasks.setComparator(ReadOnlyTask.getComparator());
        return new UnmodifiableObservableList<ReadOnlyTask>(sortedTasks);
    }

    //@@author A0121668A
    @Override
    public void updateFilteredListToShowAll() {
        try {
            if (displayStatus.equals(DISPLAY_STATUS_ALL)) {
                filteredTasks.setPredicate(null);
                filteredEvents.setPredicate(null);
            } else if (displayStatus.equals(DISPLAY_STATUS_COMPLETED)) {
                filteredTasks.setPredicate(new PredicateExpression(new StatusQualifier(true))::satisfies);
                filteredEvents.setPredicate(new PredicateExpression(new StatusQualifier(true))::satisfies);
            } else if (displayStatus.equals(DISPLAY_STATUS_PENDING)) {
                filteredTasks.setPredicate(new PredicateExpression(new StatusQualifier(false))::satisfies);
                filteredEvents.setPredicate(new PredicateExpression(new StatusQualifier(false))::satisfies);
            } else {
                throw new IllegalValueException("Wrong model manager display status");
            }
        } catch (IllegalValueException e) {
            System.out.print(e);
        }
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, displayStatus)));
    }

    //@@author A0121668A
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords, displayStatus)));
    }

    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }
    //@@author A0121668A
    // ========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        boolean satisfies(ReadOnlyEvent event);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public boolean satisfies(ReadOnlyEvent event) {
            return qualifier.run(event);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        boolean run(ReadOnlyEvent event);

        String toString();
    }

    //@@author A0121668A
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private String displayStatus;

        NameQualifier(Set<String> nameKeyWords, String status) {
            this.nameKeyWords = nameKeyWords;
            this.displayStatus = status;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (displayStatus.equals(DISPLAY_STATUS_ALL)) {
                return nameKeyWords.stream().filter(
                    keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword))
                        .findAny().isPresent();
            } else if (displayStatus.equals(DISPLAY_STATUS_COMPLETED)) {
                return nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword)
                                && task.getStatus())
                        .findAny().isPresent();
            } else if (displayStatus.equals(DISPLAY_STATUS_PENDING)) {
                return nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword)
                                && !task.getStatus())
                        .findAny().isPresent();
            } else {
                try {
                    throw new IllegalValueException("Wrong qualifier display status");
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        @Override
        public boolean run(ReadOnlyEvent event) {
            if (displayStatus.equals(DISPLAY_STATUS_ALL)) {
                return nameKeyWords.stream().filter(
                    keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().description, keyword))
                        .findAny().isPresent();
            } else if (displayStatus.equals(DISPLAY_STATUS_COMPLETED)) {
                return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().description, keyword)
                                && event.isOver())
                        .findAny().isPresent();
            } else if (displayStatus.equals(DISPLAY_STATUS_PENDING)) {
                return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().description, keyword)
                                && !event.isOver())
                        .findAny().isPresent();
            } else {
                try {
                    throw new IllegalValueException("Wrong qualifier display status");
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords) + " Displat Status: " + displayStatus;
        }
    }
    //@@author A0121668A
    private class StatusQualifier implements Qualifier {
        private boolean statusKey;

        StatusQualifier(boolean statusKey) {
            this.statusKey = statusKey;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getStatus() == statusKey;
        }

        @Override
        public boolean run(ReadOnlyEvent event) {
            if (statusKey) {
                return event.isOver();
            } else {
                return !event.isOver();
            }
        }

        @Override
        public String toString() {
            return "status =" + String.valueOf(statusKey);
        }
    }

    //@@author A0110491U
    @Override
    public boolean eventHasClash(Event toAddEvent) {
        return whatsLeft.eventHasClash(toAddEvent);
    }

}
