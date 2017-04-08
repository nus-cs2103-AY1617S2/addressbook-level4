//@@author A0144885R
package seedu.address.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
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
    public void indicateTaskManagerChanged() {
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

  //@@author A0138377U

    public ArrayList<ReadOnlyTask> getList() {
        ArrayList<ReadOnlyTask> listOfTasks = new ArrayList<>();
        for (ReadOnlyTask task : filteredTasks) {
            listOfTasks.add(task);
        }
        return listOfTasks;
    }

    public void setList(ObservableList<ReadOnlyTask> listOfTasks) {
        taskManager.setTasks(listOfTasks);
    }

    public int getFilteredTasksSize () {
        return filteredTasks.size();
    }

    public ArrayList<ReadOnlyTask> getAllDoneTasks() {
        ArrayList<ReadOnlyTask> listOfTasks = new ArrayList<>();
        for (ReadOnlyTask task : new FilteredList<>(this.taskManager.getTaskList())) {
            if (task.getStatus().status.equals("Done")) {
                listOfTasks.add(task);
            }
        }
        return listOfTasks;
    }

    @Override
    public synchronized void deleteBulkTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
    }
  //@@author A0143504R

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
