package seedu.doit.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.model.EventManagerChangedEvent;
import seedu.doit.commons.events.model.FloatingTaskManagerChangedEvent;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.model.item.Event;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.Item;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueEventList.DuplicateEventException;
import seedu.doit.model.item.UniqueEventList.EventNotFoundException;
import seedu.doit.model.item.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.doit.model.item.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FloatingTaskManager floatingTaskManager;
    private final EventManager eventManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyFloatingTask> filteredFloatingTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, ReadOnlyFloatingTaskManager floatingTaskManager,
            ReadOnlyEventManager eventManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        this.floatingTaskManager = new FloatingTaskManager(floatingTaskManager);
        this.eventManager = new EventManager(eventManager);

        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        filteredFloatingTasks = new FilteredList<>(this.floatingTaskManager.getFloatingTaskList());
        filteredEvents = new FilteredList<>(this.eventManager.getEventList());

    }

    public ModelManager() {
        this(new TaskManager(), new FloatingTaskManager(), new EventManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    private void indicateFloatingTaskManagerChanged() {
        raise(new FloatingTaskManagerChangedEvent(floatingTaskManager));
    }

    private void indicateEventManagerChanged() {
        raise(new EventManagerChangedEvent(eventManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        String[] test = new String[] {"test"};
        updateFilteredTaskList(new HashSet<>(Arrays.asList(test)));
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        indicateFloatingTaskManagerChanged();
        indicateEventManagerChanged();
    }

    @Override
    public synchronized void deleteFloatingTask(ReadOnlyFloatingTask target) throws FloatingTaskNotFoundException {
        floatingTaskManager.removeFloatingTask(target);
        String[] test = new String[] {"test"};
        updateFilteredTaskList(new HashSet<>(Arrays.asList(test)));
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        indicateFloatingTaskManagerChanged();
        indicateEventManagerChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        eventManager.removeEvent(target);
        String[] test = new String[] {"test"};
        updateFilteredTaskList(new HashSet<>(Arrays.asList(test)));
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        indicateFloatingTaskManagerChanged();
        indicateEventManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        String[] test = new String[] {"test"};
        updateFilteredTaskList(new HashSet<>(Arrays.asList(test)));
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addFloatingTask(FloatingTask floatingTask) throws DuplicateFloatingTaskException {
        floatingTaskManager.addFloatingTask(floatingTask);
        updateFilteredListToShowAll();
        indicateFloatingTaskManagerChanged();
    }

    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        eventManager.addEvent(event);
        updateFilteredListToShowAll();
        indicateEventManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateFloatingTask(int filteredFloatingTaskListIndex, ReadOnlyFloatingTask editedFloatingTask)
        throws DuplicateFloatingTaskException {
        assert editedFloatingTask != null;

        int floatingTaskManagerIndex = filteredFloatingTasks.getSourceIndex(filteredFloatingTaskListIndex);
        floatingTaskManager.updateFloatingTask(floatingTaskManagerIndex, editedFloatingTask);
        indicateFloatingTaskManagerChanged();
    }

    @Override
    public void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent) throws DuplicateEventException {
        assert editedEvent != null;

        int eventManagerIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        eventManager.updateEvent(eventManagerIndex, editedEvent);
        indicateEventManagerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList() {
        return new UnmodifiableObservableList<>(filteredFloatingTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredFloatingTasks.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredFloatingTasks.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(Item task);

        @Override
        String toString();
    }

    interface Qualifier {
        boolean run(Item task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Item task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(Item task) {
            return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                .findAny()
                .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
