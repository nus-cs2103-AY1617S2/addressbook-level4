package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueEventList.EventNotFoundException;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WhatsLeft whatsLeft;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given whatsLeft and userPrefs.
     */
    public ModelManager(ReadOnlyWhatsLeft whatsLeft, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(whatsLeft, userPrefs);

        logger.fine("Initializing with WhatsLeft: " + whatsLeft + " and user prefs " + userPrefs);

        this.whatsLeft = new WhatsLeft(whatsLeft);
        filteredTasks = new FilteredList<>(this.whatsLeft.getTaskList());
        filteredEvents = new FilteredList<>(this.whatsLeft.getEventList());
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
    
    @Override
    public synchronized void addEvent(Event event) throws UniqueEventList.DuplicateEventException {
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
            throws UniqueEventList.DuplicateEventException {
        assert editedEvent != null;

        int addressBookIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        whatsLeft.updateEvent(addressBookIndex, editedEvent);
        indicateWhatsLeftChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

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

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.
                    getDescription().description, keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public boolean run(ReadOnlyEvent event) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(event.
                    getDescription().description, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
}
