package project.taskcrusher.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import project.taskcrusher.commons.core.ComponentManager;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.events.model.ListsToShowUpdatedEvent;
import project.taskcrusher.commons.events.model.UserInboxChangedEvent;
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

//@@author A0127737X
/**
 * Represents the in-memory model of the user inbox data.
 * All changes to any model should be synchronised.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserInbox userInbox;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private final Stack<UserInbox> undoStack = new Stack<>();
    private final Stack<UserInbox> redoStack = new Stack<>();
    private boolean isLastPerformedActionUndo;
    private boolean isLastSeenListsActiveLists = false; //to make the list showing consistent after user actions

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
        updateFilteredListsToShowAll();
    }

    public ModelManager() {
        this(new UserInbox(), new UserPrefs());
    }

    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        } else {
            redoStack.push(new UserInbox(this.userInbox));
            UserInbox stateToRecover = undoStack.pop();
            resetData(stateToRecover);
            isLastPerformedActionUndo = true;
            return true;
        }
    }

    public boolean redo() {
        if (!isLastPerformedActionUndo) {
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
        signalUiForUpdatedLists();
    }

    @Override
    public ReadOnlyUserInbox getUserInbox() {
        return userInbox;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserInboxChanged() {
        raise(new UserInboxChangedEvent(userInbox));
    }

    //@@author A0127737X
    public void signalUiForUpdatedLists() {
        int eventCount = filteredEvents.size();
        int taskCount = filteredTasks.size();

        raise(new ListsToShowUpdatedEvent(eventCount, taskCount));
    }

    private void showAppropriateList() {
        if (isLastSeenListsActiveLists) {
            updateFilteredListsToShowActiveToDo();
        } else {
            updateFilteredListsToShowAll();
        }
    }
    //=========== Task operations =========================================================================

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        saveUserInboxStateForUndo();
        userInbox.removeTask(target);
        indicateUserInboxChanged();
        updateFilteredListsToShowActiveToDo();
        signalUiForUpdatedLists();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        saveUserInboxStateForUndo();
        userInbox.addTask(task);
        updateFilteredListsToShowActiveToDo();
        isLastSeenListsActiveLists = true;
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        saveUserInboxStateForUndo();
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        userInbox.updateTask(taskListIndex, editedTask);
        showAppropriateList();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void markTask(int filteredTaskListIndex, int markFlag) {
        saveUserInboxStateForUndo();
        userInbox.markTask(filteredTaskListIndex, markFlag);
        updateFilteredListsToShowAll();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void switchTaskToEvent(ReadOnlyTask toDelete, Event toAdd) throws
        DuplicateEventException, TaskNotFoundException {
        assert toDelete != null && toAdd != null;
        saveUserInboxStateForUndo();
        userInbox.removeTask(toDelete);
        userInbox.addEvent(toAdd);
        showAppropriateList();
        indicateUserInboxChanged();
    }

    //=========== Event operations =========================================================================

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        saveUserInboxStateForUndo();
        userInbox.removeEvent(target);
        updateFilteredListsToShowActiveToDo();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
            throws DuplicateEventException {
        assert editedEvent != null;
        saveUserInboxStateForUndo();
        int eventListIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        userInbox.updateEvent(eventListIndex, editedEvent);
        showAppropriateList();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        saveUserInboxStateForUndo();
        userInbox.addEvent(event);
        updateFilteredListsToShowActiveToDo();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void markEvent(int filteredEventListIndex, int markFlag) {
        saveUserInboxStateForUndo();
        userInbox.markEvent(filteredEventListIndex, markFlag);
        updateFilteredListsToShowAll();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void confirmEventTime(int filteredEventListIndex, int timeslotIndex) {
        saveUserInboxStateForUndo();
        int eventListIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
        userInbox.confirmEventTime(eventListIndex, timeslotIndex);
        updateFilteredListsToShowActiveToDo();
        indicateUserInboxChanged();
    }

    @Override
    public synchronized void switchEventToTask(ReadOnlyEvent toDelete, Task toAdd) throws
        DuplicateTaskException, EventNotFoundException {
        assert toDelete != null && toAdd != null;
        saveUserInboxStateForUndo();
        userInbox.removeEvent(toDelete);
        userInbox.addTask(toAdd);
        showAppropriateList();
        indicateUserInboxChanged();
    }

    @Override
    public void saveUserInboxStateForUndo() {
        undoStack.push(new UserInbox(this.userInbox));
        isLastPerformedActionUndo = false;
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //=========== Filtered Event List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    //=========== Combined filtering for UI =================================================================

    @Override
    public void updateFilteredListsToShowAll() {
        isLastSeenListsActiveLists = false;
        filteredEvents.setPredicate(null);
        filteredTasks.setPredicate(null);
        signalUiForUpdatedLists();
    }

    @Override
    public void updateFilteredListsToShowActiveToDo() {
        isLastSeenListsActiveLists = true;
        updateFilteredLists(new PredicateExpression(new CompletionQualifier(false)));
    }

    @Override
    public void updateFilteredListsToShowCompleteToDo() {
        isLastSeenListsActiveLists = false;
        updateFilteredLists(new PredicateExpression(new CompletionQualifier(true)));
    }

    @Override
    public void updateFilteredLists(Set<String> keywords, boolean showCompletedToo) {
        updateFilteredLists(new PredicateExpression(new KeywordQualifier(keywords, showCompletedToo)));
    }

    @Override
    public void updateFilteredLists(Timeslot userInterestedTimeslot) {
        isLastSeenListsActiveLists = true;
        updateFilteredLists(new PredicateExpression(new TimeslotQualifier(userInterestedTimeslot)));
    }

    private void updateFilteredLists(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
        signalUiForUpdatedLists();
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
        private boolean showCompletedItemsToo;

        KeywordQualifier(Set<String> nameKeyWords, boolean showCompletedItemsToo) {
            this.nameKeyWords = nameKeyWords;
            this.showCompletedItemsToo = showCompletedItemsToo;
        }

        @Override
        public boolean run(ReadOnlyUserToDo item) {
            if (showCompletedItemsToo) {
                return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsSubstringIgnoreCase(item.toString(), keyword))
                    .findAny()
                    .isPresent();
            } else {
                return !item.isComplete() && nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsSubstringIgnoreCase(item.toString(), keyword))
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
            if (item.isComplete()) {
                return false;
            } else if (item instanceof ReadOnlyEvent) {
                ReadOnlyEvent event = (ReadOnlyEvent) item;
                return event.hasOverlappingTimeslot(userInterestedTimeslot);
            } else if (item instanceof ReadOnlyTask) {
                ReadOnlyTask task = (ReadOnlyTask) item;
                return task.getDeadline().isWithin(userInterestedTimeslot);
            } else {
                assert false;
                return false;
            }
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
        private boolean showComplete;

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
