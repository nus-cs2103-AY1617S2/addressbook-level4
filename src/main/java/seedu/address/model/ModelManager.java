//@@author A0144885R
package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;
    private TaskManager taskManagerCopy;
    private String flag;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with address book: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        this.taskManagerCopy = new TaskManager(taskManager);
        this.flag = "empty copy";
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
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

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }
  //@@author A0143504R
    public TaskManager getCopy() {
        return taskManagerCopy;
    }

    public void updateCopy(ReadOnlyTaskManager newData) {
        taskManagerCopy = new TaskManager(newData);
    }

    public void updateFlag(String newFlag) {
        flag = newFlag;
    }

    public String getFlag() {
        return this.flag;
    }
    //
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
    public void updateFilteredTaskListByKeywords(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TaskQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    public void updateFilteredTaskListByDate(Deadline deadline) {
        updateFilteredTaskList(new PredicateExpression(new TaskQualifierByDate(deadline)));
    }

    @Override
    public void sort(String arg) {
        if (filteredTasks.size() == 0 || filteredTasks == null) {
            return;
        }

        if (arg.equals("name")) {
            bubbleSortName(filteredTasks.size() - 1);
        } else {
            bubbleSortDate(filteredTasks.size() - 1);
        }

    }

    private void bubbleSortName(int upper) {
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int k = 0; k < upper; k++) {
                if (getCName(k).compareToIgnoreCase(getCName(k + 1)) > 0) {
                    exchange(k , k + 1);
                    flag = true;
                }
            }
        }
    }

    private void bubbleSortDate(int upper) {
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int k = 0; k < upper; k++) {
                if (getCTime(k) < getCTime(k + 1)) {
                    exchange(k , k + 1);
                    flag = true;
                }
            }
        }
    }

    private String getCName(int task) {
        return filteredTasks.get(task).getName().toString();
    }

    private long getCTime(int task) {
        try {
            return filteredTasks.get(task).getDeadline().date.getBeginning().getTime();
        } catch (NullPointerException e) {
            return Long.MAX_VALUE;
        }
    }

    private void exchange (int i, int j) {
        ReadOnlyTask temp = filteredTasks.get(i);
        temp = new Task(temp.getName(), temp.getDeadline(), temp.getDescription(), temp.getTags());
        taskManager.updateTask(i, filteredTasks.get(j));
        taskManager.updateTask(j, temp);
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

    private class TaskQualifier implements Qualifier {
        private Set<String> keyWords;

        TaskQualifier(Set<String> keyWords) {
            this.keyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (keyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().name, keyword))
                    .findAny()
                    .isPresent())
                    || (keyWords.stream()
                       .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().description, keyword))
                       .findAny()
                       .isPresent())
                    || (keyWords.stream()
                            .filter(keyword -> StringUtil.containsWordIgnoreCase(
                                    task.getDeadline().date.toString(), keyword))
                            .findAny()
                            .isPresent());
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", keyWords);
        }
    }

    private class TaskQualifierByDate implements Qualifier {
        private Deadline deadline;

        TaskQualifierByDate(Deadline deadline) {
            this.deadline = deadline;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return DateUtil.isDeadlineMatch(task.getDeadline(), deadline);
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", deadline.toString());
        }
    }

}
