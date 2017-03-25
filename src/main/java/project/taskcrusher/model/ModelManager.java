package project.taskcrusher.model;

import java.util.Date;
import java.util.ArrayList;
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
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList.DuplicateEventException;
import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import project.taskcrusher.model.shared.UserItem;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;
import project.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;
/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserInbox userInbox;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private final FilteredList<ReadOnlyTask> filteredDeleted;
    private final FilteredList<ReadOnlyTask> filteredAdded;

    /**
     * Initializes a ModelManager with the given userInbox and userPrefs.
     */
    public ModelManager(ReadOnlyUserInbox userInbox, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(userInbox, userPrefs);

        logger.fine("Initializing with user inbox: " + userInbox + " and user prefs " + userPrefs);

        this.userInbox = new UserInbox(userInbox);
        filteredTasks = new FilteredList<>(this.userInbox.getTaskList());
        filteredEvents = new FilteredList<>(this.userInbox.getEventList());
        filteredDeleted = new FilteredList<>(this.userInbox.getDeletedList());
        filteredAdded = new FilteredList<>(this.userInbox.getAddedList());
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
    public ReadOnlyUserInbox getUserInbox(){
        	return userInbox;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserInboxChanged() {
        raise(new AddressBookChangedEvent(userInbox));
    }

    //=========== Task operations =========================================================================

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException{
    	try{userInbox.removeTask(target);}
    	catch(UniqueTaskList.DuplicateTaskException e){}
        indicateUserInboxChanged();
    }
    
    public synchronized void deleteUndoTask(ReadOnlyTask target) throws TaskNotFoundException{
    	try{userInbox.removeUndoTask(target);}
    	catch(UniqueTaskList.DuplicateTaskException e){}
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        userInbox.addTask(task);
        updateFilteredTaskListToShowAll();
        indicateUserInboxChanged();
    }
    
    public synchronized void addUndoTask(Task task) throws UniqueTaskList.DuplicateTaskException{
        try{userInbox.addUndoTask(task);}
        catch(UniqueTaskList.TaskNotFoundException e){}
        updateFilteredListToShowAll();
        indicateUserInboxChanged();
    }
    
   

    @Override
    public synchronized void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        userInbox.updateTask(taskListIndex, editedTask);
        indicateUserInboxChanged();
    }

    //=========== Event operations =========================================================================

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        userInbox.removeEvent(target);
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
            throws DuplicateEventException {
        assert editedEvent != null;

        int eventListIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        userInbox.updateEvent(eventListIndex, editedEvent);
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        userInbox.addEvent(event);
        updateFilteredTaskListToShowAll();
        indicateUserInboxChanged();
    }

    public UnmodifiableObservableList<ReadOnlyEvent> getEventsWithOverlappingTimeslots(Timeslot candidate) {
        return new UnmodifiableObservableList<>(userInbox.getEventsWithOverlappingTimeslots(candidate));
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAddedList() {
        return new UnmodifiableObservableList<>(filteredAdded);
    }
    
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeletedList() {
        return new UnmodifiableObservableList<>(filteredDeleted);
    }

    @Override
    public void updateFilteredTaskListToShowAll() {
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

    //=========== Filtered Event List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredEventList(Timeslot userInterestedTimeslot) {
        updateFilteredEventList(new PredicateExpression(new TimeslotQualifier(userInterestedTimeslot)));
    }

    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(UserItem item);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(UserItem item) {
            return qualifier.run(item);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(UserItem item);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(UserItem item) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(item.getName().toString(), keyword))
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
        public boolean run(UserItem item) {
            assert item instanceof ReadOnlyTask;
            ReadOnlyTask task = (ReadOnlyTask) item;

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

    private class TimeslotQualifier implements Qualifier {
        private Timeslot userInterestedTimeslot;

        TimeslotQualifier(Timeslot timeslot) {
            assert timeslot != null;
            this.userInterestedTimeslot = timeslot;
        }

        @Override
        public boolean run(UserItem item) {
            assert item instanceof ReadOnlyEvent;
            ReadOnlyEvent event = (ReadOnlyEvent) item;
            if (event.hasOverlappingTimeslot(userInterestedTimeslot)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "user-interested timeslot is " + userInterestedTimeslot.toString();
        }
    }

	@Override
	public void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
		// TODO Auto-generated method stub
		
	}
}
