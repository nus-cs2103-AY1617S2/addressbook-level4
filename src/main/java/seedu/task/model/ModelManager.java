package seedu.task.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.History;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.FilePathChangedEvent;
import seedu.task.commons.events.model.LoadNewFileEvent;
import seedu.task.commons.events.model.LoadNewFileSuccessEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.commons.util.TaskUtil;
import seedu.task.model.task.Date;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of KIT data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final History history;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     * 
     * @throws IllegalValueException
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());

        this.history = History.getInstance();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    // @@author A0140063X
    @Override
    public void resetData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0140063X
    @Override
    public void loadData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        raise(new TaskManagerChangedEvent(taskManager, history.getBackupFilePath()));
    }

    // @@author A0140063X
    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    // @@author A0140063X
    /**
     * Raises an event to indicate the model has changed
     * 
     * @param backupFilePath
     */
    private void indicateTaskManagerChanged(String backupFilePath) {
        history.handleTaskManagerChanged(backupFilePath);
        raise(new TaskManagerChangedEvent(taskManager, backupFilePath));
    }

    // @@author
    /** Raises an event to indicate the file path has changed */
    private void indicateFilePathChanged(String newPath) {
        raise(new FilePathChangedEvent(newPath, taskManager));
    }

    private void indicateLoadChanged(String loadPath) {
        raise(new LoadNewFileEvent(loadPath, taskManager));
        raise(new FilePathChangedEvent(loadPath, taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0139975J
    @Override
    public synchronized void isDoneTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        int taskManagerIndex = filteredTasks.getSourceIndex(index);
        taskManager.updateDone(taskManagerIndex, target);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0139975J
    @Override
    public synchronized void unDoneTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        int taskManagerIndex = filteredTasks.getSourceIndex(index);
        taskManager.updateUnDone(taskManagerIndex, target);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTaskToFront(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0140063X
    @Override
    public void addMultipleTasks(ArrayList<Task> tasks) {
        for (Task task : tasks) {
            try {
                taskManager.addTaskToFront(task);
            } catch (DuplicateTaskException e) {
                logger.info("Duplicate task " + task.getName() + " from google calendar not added.");
            }
        }

        updateFilteredListToShowAll();
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author
    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws IllegalValueException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    @Override
    public void sortTaskList() {
        taskManager.sortTaskList();
        indicateTaskManagerChanged("");
    }

    @Override
    public void changeFilePath(String newPath) {
        indicateFilePathChanged(newPath);
        indicateTaskManagerChanged("");
    }

    @Override
    public void loadFromLocation(String loadPath) {
        indicateLoadChanged(loadPath);
    }

    // =========== Filtered Task List Accessors =============================================================

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
        updateFilteredTaskList(new PredicateExpression(new StringQualifier(keywords, false)));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, boolean isExact) {
        updateFilteredTaskList(new PredicateExpression(new StringQualifier(keywords, isExact)));
    }

    @Override
    public void updateFilteredTaskList(String keyword) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }

    // @@author A0139975J-reused
    @Override
    public void updateFilteredTaskList(boolean value) {
        updateFilteredTaskList(new PredicateExpression(new DoneQualifier(value)));
    }

    // @@author A0139975J-reused
    @Override
    public void updateFilteredTaskList(Date date) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(date)));

    }
    
    @Override 
    public void updateFilteredTaskListFloat() {
        updateFilteredTaskList(new PredicateExpression(new FloatDateQualifier()));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Date date, boolean isexact) {
        // TODO Auto-generated method stub
        updateFilteredTaskList(new PredicateExpression(new StringAndDateQualifier(keywords, date)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering =================================================

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

    // @@author A0142487Y
    /**
     * This qualifier is specifically for strings,including name, location,remark and tags. Returns true if there is any
     * match.
     * 
     * @author Xu
     *
     */
    private class StringQualifier implements Qualifier {
        private boolean isExact = false;
        private Set<String> keywords;

        StringQualifier(Set<String> keywords, boolean isExact) {
            this.isExact = isExact;
            this.keywords = keywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isExact) {
                return StringUtil.containsExactWordsIgnoreCase(task.getName().fullName, keywords)
                        || StringUtil.containsExactWordsIgnoreCase(task.getRemark().toString(), keywords);
            } else {
                for (String keyword : keywords) {
                    if (!TaskUtil.doesTaskContainKeyword(task, keyword)) {
                        return false;
                    }
                }
                return true;
                // return keywords.stream()
                // .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)
                // || StringUtil.containsWordIgnoreCase(task.getRemark().toString(), keyword)
                // || StringUtil.containsSubstringIgnoreCase(task.getName().fullName, keyword)
                // || StringUtil.containsSubstringIgnoreCase(task.getRemark().toString(), keyword)
                // || StringUtil.containsSubstringIgnoreCase(task.getLocation().toString(), keyword)
                // || StringUtil.containsWordIgnoreCase(task.getLocation().toString(), keyword)
                // || StringUtil.containsSubstringIgnoreCase(task.getTags().toString(), keyword))
                // .findAny().isPresent();
                // || CollectionUtil.doesAnyStringMatch(task.getTags().getGenericCollection(), keywords);
            }
        }

        @Override
        public String toString() {
            return "Target to be searched for =" + String.join(", ", keywords);
        }
    }

    // @@author A0142487Y
    private class StringAndDateQualifier implements Qualifier {
        private boolean isExact = false;
        private Set<String> keyWords;
        private StringQualifier stringQualifier;
        private DateQualifier dateQualifier;

        StringAndDateQualifier(Set<String> keywords, Date date) {
            assert date != null;
            assert keywords != null;
            this.keyWords = keywords;
            this.stringQualifier = new StringQualifier(keywords, isExact);
            this.dateQualifier = new DateQualifier(date);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isExact) {
                return StringUtil.containsExactWordsIgnoreCase(task.getName().fullName, keyWords)
                        || StringUtil.containsExactWordsIgnoreCase(task.getRemark().toString(), keyWords)
                        || StringUtil.containsExactWordsIgnoreCase(task.getLocation().toString(), keyWords);
            } else {
                return this.dateQualifier.date.isNull() ? this.stringQualifier.run(task)
                        : this.stringQualifier.run(task) && this.dateQualifier.run(task);

            }
        }
    }

    // @@author A0142487Y-reused
    private class TagQualifier implements Qualifier {

        private String tagKeyword;

        TagQualifier(String tagKeyWord) {
            this.tagKeyword = tagKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return CollectionUtil.doesAnyStringMatch(task.getTags().getGenericCollection(), tagKeyword);
        }

        @Override
        public String toString() {
            return "Tag =" + tagKeyword;
        }
    }

    // @@author A0139975J
    private class DateQualifier implements Qualifier {

        private Date date;

        // @@author A0139975J
        DateQualifier(Date date) {
            this.date = date;
        }

        // @@author A0139975J
        @Override
        public boolean run(ReadOnlyTask task) {
            if (date.isNull()) {
                return false;
            }
            return task.getEndDate().equalsIgnoreTime(date) || task.getStartDate().equalsIgnoreTime(date);
        }
    }
    
    //@@author A0139975J
    private class FloatDateQualifier implements Qualifier {

        private Date date;

        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getEndDate().isNull() && task.getStartDate().isNull()) {
                return true;
            }
            return false;
        }
    }
    
    //@@author A0139975J
    private class DoneQualifier implements Qualifier {

        private boolean value;

        // @@author A0139975J
        DoneQualifier(boolean value) {
            this.value = value;
        }

        // @@author A0139975J
        @Override
        public boolean run(ReadOnlyTask task) {
            if (this.value & task.isDone()) {
                return true;
            } else if (!this.value & !task.isDone()) {
                return true;
            } else {
                return false;
            }

        }
    }

    // @@author
    @Override
    @Subscribe
    public void handleLoadNewFileSuccessEvent(LoadNewFileSuccessEvent event) {
        taskManager.resetData(event.readOnlyTaskManager);
        logger.info("Resetting data from new load location.");
    }

}
