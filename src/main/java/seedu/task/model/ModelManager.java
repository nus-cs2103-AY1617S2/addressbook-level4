package seedu.task.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskListChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.Description;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with address book: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredPersonListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredPersonListIndex);
        taskList.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
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
        @Override
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
        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        //@@author A0164212U
        public int findMatchingTaskIndex(ReadOnlyTask task) {
            int taskIndex = 0;
            Description description = task.getDescription();
            for (int i = 0; i < Model.recurringTaskList.size(); i++) {
                if (Model.recurringTaskList.get(i).getDescription().equals(description)) {
                    taskIndex = i;
                    break;
                }
            }
            return taskIndex;
        }

        /**
         * @param taskOccurrenceList
         * @return returns null if occurrence does not exist
         */
        public RecurringTaskOccurrence findOccurrence(ArrayList<RecurringTaskOccurrence> taskOccurrenceList) {
            RecurringTaskOccurrence occurrence = null;
            boolean isPresent = false;
            for (int j = 0; j < taskOccurrenceList.size(); j++) {
                final int finalIndex = j;
                isPresent = (nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(taskOccurrenceList.get(finalIndex).getStartTiming().value, keyword))
                        .findAny()
                        .isPresent()) ||
                        (nameKeyWords.stream()
                                .filter(keyword -> StringUtil.containsWordIgnoreCase(taskOccurrenceList.get(finalIndex).getEndTiming().value, keyword))
                                .findAny()
                                .isPresent());
                if (isPresent) {
                    occurrence = taskOccurrenceList.get(j);
                    break;
                }
            }
            return occurrence;
        }
        //
        //        public Task createTaskToAdd(ReadOnlyTask task, RecurringTaskOccurrence occurrence) {
        //            Task toAdd = new Task(task.getDescription(),
        //                    task.getPriority(),
        //                    occurrence.getStartTiming(),
        //                    occurrence.getEndTiming(),
        //                    task.getTags(),
        //                    task.isRecurring());
        //            return toAdd;
        //        }

        public boolean searchList(ReadOnlyTask task) {
            boolean isPresent = false;
            if (
                    (nameKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword))
                            .findAny()
                            .isPresent()) ||
                    (nameKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getPriority().value, keyword))
                            .findAny()
                            .isPresent()) ||
                    (nameKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getStartTiming().value, keyword))
                            .findAny()
                            .isPresent()) ||
                    (nameKeyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getEndTiming().value, keyword))
                            .findAny()
                            .isPresent())) {
                isPresent = true;
            }
            return isPresent;
        }


        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isPresent = searchList(task);
            //            if (task.isRecurring() && isPresent == false) {
            //                // find corresponding RecurringTask in recurringTaskList based on description
            //                // find if any RecurringTaskOccurence exist for the dates corresponding to nameKeyWords in task.getOccurences
            //                // use the ReccurringTaskOccurence object in addition to the RecurringTask to create a Task object
            //                // use model.addTask with this task
            //                int taskIndex = findMatchingTaskIndex(task);
            //                ArrayList<RecurringTaskOccurrence> taskOccurrenceList = new ArrayList<RecurringTaskOccurrence>();
            //                taskOccurrenceList = Model.recurringTaskList.get(taskIndex).getOccurrences();
            //                RecurringTaskOccurrence occurrence = findOccurrence(taskOccurrenceList);
            //                if (occurrence != null) {
            //                    Task toAdd = createTaskToAdd(task, occurrence);
            //                    try {
            //                        addTask(toAdd);
            //                    } catch (DuplicateTaskException e) {
            //                        e.printStackTrace();
            //                    }
            //                }
            //            }
            //            isPresent = searchList(task);
            return isPresent;
        }

        //@@author


        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
