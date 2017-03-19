package project.taskcrusher.model;

import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import project.taskcrusher.commons.core.ComponentManager;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.events.model.AddressBookChangedEvent;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.commons.util.StringUtil;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.UniqueEventList.DuplicateEventException;
import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

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

    @Override
    public void updateFilteredTaskList(Date dateUpTo) {
        updateFilteredTaskList(new PredicateExpression(new DeadlineQualifier(dateUpTo)));
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

    private class DeadlineQualifier implements Qualifier {
        private Date dateUpTo;

        DeadlineQualifier(Date date) {
            assert date != null;
            this.dateUpTo = date;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            //has no deadline
            if (!task.getDeadline().getDate().isPresent()) {
                return false;
            }
            Date deadline = task.getDeadline().getDate().get();
            assert deadline != null;
            if (deadline.before(dateUpTo)) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "date up to =" + dateUpTo.toString();
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

	@Override
	public void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
