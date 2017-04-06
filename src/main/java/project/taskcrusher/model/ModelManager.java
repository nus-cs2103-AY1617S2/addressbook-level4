package project.taskcrusher.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import project.taskcrusher.commons.core.ComponentManager;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.events.model.AddressBookChangedEvent;
import project.taskcrusher.commons.events.model.ListsToShowUpdatedEvent;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.commons.util.StringUtil;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList.DuplicateEventException;
import project.taskcrusher.model.event.UniqueEventList.EventNotFoundException;
import project.taskcrusher.model.shared.ReadOnlyUserToDo;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;
import project.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;
import project.taskcrusher.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the user inbox data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final boolean LIST_EMPTY = true;

    private final UserInbox userInbox;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private final Stack<UserInbox> undoStack = new Stack<>();
    private final Stack<UserInbox> redoStack = new Stack<>();
    private boolean isLastPerformedActionIsUndo = false;

    /**
     * Initialises a ModelManager with the given userInbox and userPrefs.
     */
    public ModelManager(ReadOnlyUserInbox userInbox, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(userInbox, userPrefs);

        logger.fine("Initializing with user inbox: " + userInbox + " and user prefs " + userPrefs);

        this.userInbox = new UserInbox(userInbox);
        filteredTasks = new FilteredList<>(this.userInbox.getTaskList());
        filteredEvents = new FilteredList<>(this.userInbox.getEventList());
        updateFilteredListsToShowActiveToDo();
    }

    public ModelManager() {
        this(new UserInbox(), new UserPrefs());
    }

  //@@author A0163639W
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        } else {
            redoStack.push(new UserInbox(this.userInbox));
            UserInbox stateToRecover = undoStack.pop();
            resetData(stateToRecover);
            isLastPerformedActionIsUndo = true;
            return true;
        }
    }

    public boolean redo() {
        if (!isLastPerformedActionIsUndo) {
            return false;
        } else if (redoStack.isEmpty()) {
            return false;
        } else {
            undoStack.push(new UserInbox(this.userInbox));
            UserInbox stateToRecover = redoStack.pop();
            resetData(stateToRecover);
            return true;
        }
    }

    //@@author
    @Override
    public void resetData(ReadOnlyUserInbox newData) {
        userInbox.resetData(newData);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public ReadOnlyUserInbox getUserInbox() {
        return userInbox;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserInboxChanged() {
        raise(new AddressBookChangedEvent(userInbox));
    }

    //@@author A0127737X
    public void prepareListsForUi() {
        boolean isTaskListToShowEmpty = false, isEventListToShowEmpty = false;
        if (filteredEvents.isEmpty()) {
            isEventListToShowEmpty = LIST_EMPTY;
        }
        if (filteredTasks.isEmpty()) {
            isTaskListToShowEmpty = LIST_EMPTY;
        }
        raise(new ListsToShowUpdatedEvent(isEventListToShowEmpty, isTaskListToShowEmpty));
    }

    public synchronized void updateOverdueStatus() {
        userInbox.updateOverdueStatus();
    }

    //=========== Task operations =========================================================================

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        saveUserInboxStateForUndo();
        userInbox.removeTask(target);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        saveUserInboxStateForUndo();
        userInbox.addTask(task);
        updateFilteredTaskListToShowAll();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        saveUserInboxStateForUndo();
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        userInbox.updateTask(taskListIndex, editedTask);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void markTask(int filteredTaskListIndex, int markFlag) {
        saveUserInboxStateForUndo();
        userInbox.markTask(filteredTaskListIndex, markFlag);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void markEvent(int filteredEventListIndex, int markFlag) {
        saveUserInboxStateForUndo();
        userInbox.markEvent(filteredEventListIndex, markFlag);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    //=========== Event operations =========================================================================

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        saveUserInboxStateForUndo();
        userInbox.removeEvent(target);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
            throws DuplicateEventException {
        assert editedEvent != null;
        saveUserInboxStateForUndo();
        int eventListIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        userInbox.updateEvent(eventListIndex, editedEvent);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        saveUserInboxStateForUndo();
        userInbox.addEvent(event);
        updateFilteredEventListToShowAll();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void confirmEventTime(int filteredEventListIndex, int timeslotIndex) {
        saveUserInboxStateForUndo();
        int eventListIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        userInbox.confirmEventTime(eventListIndex, timeslotIndex);
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void switchTaskToEvent(ReadOnlyTask toDelete, Event toAdd) throws
        DuplicateEventException, TaskNotFoundException {
        assert toDelete != null && toAdd != null;
        saveUserInboxStateForUndo();
        userInbox.removeTask(toDelete);
        userInbox.addEvent(toAdd);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    @Override
    public synchronized void switchEventToTask(ReadOnlyEvent toDelete, Task toAdd) throws
        DuplicateTaskException, EventNotFoundException {
        assert toDelete != null && toAdd != null;
        saveUserInboxStateForUndo();
        userInbox.removeEvent(toDelete);
        userInbox.addTask(toAdd);
        indicateUserInboxChanged();
        prepareListsForUi();
    }

    public UnmodifiableObservableList<ReadOnlyEvent> getEventsWithOverlappingTimeslots(Timeslot candidate) {
        return new UnmodifiableObservableList<>(userInbox.getEventsWithOverlappingTimeslots(candidate));
    }

    @Override
    public void saveUserInboxStateForUndo() {
        undoStack.push(new UserInbox(this.userInbox));
        isLastPerformedActionIsUndo = false;
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredTasks.setPredicate(null);
        prepareListsForUi();
    }

    //=========== Filtered Event List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEvents.setPredicate(null);
        prepareListsForUi();
    }

    //====================== Combined filtering =================================================

    @Override
    public void updateFilteredListsShowAll() {
        filteredEvents.setPredicate(null);
        filteredTasks.setPredicate(null);
        prepareListsForUi();
    }

    @Override
    public void updateFilteredListsToShowActiveToDo() {
        updateFilteredLists(new PredicateExpression(new CompletionQualifier(false)));
    }

    @Override
    public void updateFilteredListsToShowCompleteToDo() {
        updateFilteredLists(new PredicateExpression(new CompletionQualifier(true)));
    }

    private void updateFilteredLists(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
        prepareListsForUi();
    }

    @Override
    public void updateFilteredLists(Set<String> keywords, boolean showCompletedToo) {
        updateFilteredLists(new PredicateExpression(new KeywordQualifier(keywords, showCompletedToo)));
    }

    @Override
    public void updateFilteredLists(Timeslot userInterestedTimeslot) {
        updateFilteredLists(new PredicateExpression(new TimeslotQualifier(userInterestedTimeslot)));
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyUserToDo item);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyUserToDo item) {
            return qualifier.run(item);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyUserToDo item);
        String toString();
    }

    private class KeywordQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private boolean showCompletedToo;

        KeywordQualifier(Set<String> nameKeyWords, boolean showCompletedToo) {
            this.nameKeyWords = nameKeyWords;
            this.showCompletedToo = showCompletedToo;
        }

        @Override
        public boolean run(ReadOnlyUserToDo item) {
            if (showCompletedToo) {
                return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(item.toString(), keyword))
                    .findAny()
                    .isPresent();
            } else {
                return !item.isComplete() && nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(item.toString(), keyword))
                        .findAny()
                        .isPresent();
            }
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    /**
     * checks if:
     * (1) if the ToDo item is an active task, its deadline falls within the given timeslot
     * (2) if the ToDO item is an active event, its timeslots overlaps with the given timeslot
     */
    private class TimeslotQualifier implements Qualifier {
        private Timeslot userInterestedTimeslot;

        TimeslotQualifier(Timeslot timeslot) {
            assert timeslot != null;
            this.userInterestedTimeslot = timeslot;
        }

        @Override
        public boolean run(ReadOnlyUserToDo item) {
            if (item instanceof ReadOnlyEvent) {
                ReadOnlyEvent event = (ReadOnlyEvent) item;
                if (event.isComplete()) {
                    return false;
                } else if (event.hasOverlappingTimeslot(userInterestedTimeslot)) {
                    return true;
                } else {
                    return false;
                }
            } else if (item instanceof ReadOnlyTask) {
                ReadOnlyTask task = (ReadOnlyTask) item;
                if (task.isComplete()) {
                    return false;
                } else if (task.getDeadline().isWithin(userInterestedTimeslot)) { // more OOP way
                    return true;
                } else {
                    return false;
                }
            }
            assert false;
            return false; //should not reach here
        }

        @Override
        public String toString() {
            return "user-interested timeslot is " + userInterestedTimeslot.toString();
        }
    }
    /**
     * checks if the given UserToDo is marked as complete or incomplete
     */
    private class CompletionQualifier implements Qualifier {
        boolean showComplete;
        CompletionQualifier(boolean showComplete) {
            this.showComplete = showComplete;
        }

        @Override
        public boolean run(ReadOnlyUserToDo item) {
            if (item.isComplete()) {
                return this.showComplete ? true : false;
            }
            return this.showComplete ? false : true;
        }

        @Override
        public String toString() {
            return "separate between ongoing and completed tasks and events";
        }
    }
}
