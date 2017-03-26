package seedu.address.model;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueEventList.DuplicateTimeClashException;
import seedu.address.model.person.UniqueEventList.EventNotFoundException;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WhatsLeft whatsLeft;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private static String previousCommand;
    private static WhatsLeft previousState;
    private static String displayStatus;

    /**
     * Initializes a ModelManager with the given whatsLeft and userPrefs.
     */
    public ModelManager(ReadOnlyWhatsLeft whatsLeft, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(whatsLeft, userPrefs);

        logger.fine("Initializing with WhatsLeft: " + whatsLeft + " and user prefs " + userPrefs);

        this.whatsLeft = new WhatsLeft(whatsLeft);
        previousState = new WhatsLeft();
        filteredTasks = new FilteredList<>(this.whatsLeft.getTaskList());
        filteredEvents = new FilteredList<>(this.whatsLeft.getEventList());
        previousCommand = "";
        displayStatus = "Pending";
        updateFilteredListToShowAll();
    }

    public ModelManager() {
        this(new WhatsLeft(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWhatsLeft newData) {
        whatsLeft.resetData(newData);
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

    @Override
    public void setDisplayStatus(String status) {
        displayStatus = status;
        updateFilteredListToShowAll();
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

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

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatsLeft.addTask(task);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    // @@author A0121668A
    @Override
    public synchronized void MarkTaskAsComplete(int filteredTaskListIndex) throws TaskNotFoundException {
        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        whatsLeft.completeTask(addressBookIndex);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }
    // @@author

    @Override
    public synchronized void addEvent(Event event)
            throws UniqueEventList.DuplicateEventException, DuplicateTimeClashException {
        whatsLeft.addEvent(event);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        whatsLeft.updateTask(addressBookIndex, editedTask);
        indicateWhatsLeftChanged();
    }

    @Override
    public void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
            throws UniqueEventList.DuplicateEventException, DuplicateTimeClashException {
        assert editedEvent != null;

        int addressBookIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        whatsLeft.updateEvent(addressBookIndex, editedEvent);
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
    //@@author

    // =========== Filtered List Accessors
    
    // @@author A0148038A
    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<ReadOnlyEvent>(filteredEvents);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(filteredTasks);
    }

    // @Override
    // public void updateFilteredListToShowIncomplete() {
    // filteredTasks.setPredicate(new PredicateExpression(new
    // StatusQualifier(false))::satisfies);
    // filteredEvents.setPredicate(null);
    // }

    // @Override
    // public void updateFilteredListToShowComplete() {
    // filteredTasks.setPredicate(new PredicateExpression(new
    // StatusQualifier(true))::satisfies);
    // filteredEvents.setPredicate(null);
    // }

    @Override
    public void updateFilteredListToShowAll() {
        try {
            if (displayStatus.equals("All")) {
                filteredTasks.setPredicate(null);
            } else if (displayStatus.equals("Completed")) {
                filteredTasks.setPredicate(new PredicateExpression(new StatusQualifier(true))::satisfies);
            } else if (displayStatus.equals("Pending")) {
                filteredTasks.setPredicate(new PredicateExpression(new StatusQualifier(false))::satisfies);
            } else {
                throw new IllegalValueException("Wrong model manager display status");
            }
        } catch (IllegalValueException e) {
            System.out.print(e);
        }
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, displayStatus)));
    }

    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords, displayStatus)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private String displayStatus;

        NameQualifier(Set<String> nameKeyWords, String status) {
            this.nameKeyWords = nameKeyWords;
            this.displayStatus = status;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (displayStatus.equals("All")) {
                return nameKeyWords.stream().filter(
                        keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword))
                        .findAny().isPresent();
            } else if (displayStatus.equals("Completed")) {
                return nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword)
                                && task.getStatus())
                        .findAny().isPresent();
            } else if (displayStatus.equals("Pending")) {
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
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().description, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords) + " Displat Status: " + displayStatus;
        }
    }

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
            return true;
        }

        @Override
        public String toString() {
            return "status =" + String.valueOf(statusKey);
        }
    }

}
