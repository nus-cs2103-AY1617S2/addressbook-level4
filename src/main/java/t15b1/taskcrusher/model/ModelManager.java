package t15b1.taskcrusher.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import t15b1.taskcrusher.commons.core.ComponentManager;
import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.commons.core.UnmodifiableObservableList;
import t15b1.taskcrusher.commons.events.model.AddressBookChangedEvent;
import t15b1.taskcrusher.commons.util.CollectionUtil;
import t15b1.taskcrusher.commons.util.StringUtil;
import t15b1.taskcrusher.model.event.Event;
import t15b1.taskcrusher.model.event.ReadOnlyEvent;
import t15b1.taskcrusher.model.event.UniqueEventList.DuplicateEventException;
import t15b1.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import t15b1.taskcrusher.model.task.ReadOnlyTask;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;
import t15b1.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserInbox userInbox;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given userInbox and userPrefs.
     */
    public ModelManager(ReadOnlyUserInbox userInbox, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(userInbox, userPrefs);

        logger.fine("Initializing with user inbox: " + userInbox + " and user prefs " + userPrefs);

        this.userInbox = new UserInbox(userInbox);
        filteredTasks = new FilteredList<>(this.userInbox.getTaskList());
    }

    public ModelManager() {
        this(new UserInbox(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyUserInbox newData) {
        userInbox.resetData(newData);
        indicateUserInboxChanged();
    }

    @Override
    public ReadOnlyUserInbox getUserInbox() {
        return userInbox;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserInboxChanged() {
        raise(new AddressBookChangedEvent(userInbox));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        userInbox.removeTask(target);
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        userInbox.addTask(task);
        updateFilteredListToShowAll();
        indicateUserInboxChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        userInbox.updateTask(addressBookIndex, editedTask);
        indicateUserInboxChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
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
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    @Override
    public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateEvent(int fileteredEventListIndex, ReadOnlyEvent editedEvent) throws DuplicateEventException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addEvent(Event event) throws EventNotFoundException {
        // TODO Auto-generated method stub
        
    }

}
