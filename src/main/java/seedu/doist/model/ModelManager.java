package seedu.doist.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import javafx.collections.transformation.FilteredList;
import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.Config;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.commons.events.config.AbsoluteStoragePathChangedEvent;
import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.util.CollectionUtil;
import seedu.doist.commons.util.ConfigUtil;
import seedu.doist.commons.util.History;
import seedu.doist.commons.util.StringUtil;
import seedu.doist.logic.commands.ListCommand.TaskType;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskAlphabetComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskCombinedComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskFinishedStatusComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskPriorityComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskTimingComparator;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;
import seedu.doist.model.task.UniqueTaskList.TaskAlreadyFinishedException;
import seedu.doist.model.task.UniqueTaskList.TaskAlreadyUnfinishedException;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    private final Config config;
    private final History<TodoList> todoListHistory = new History<TodoList>();
    private final AliasListMap aliasListMap;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    //@@author A0140887W
    /**
     * Initializes a ModelManager with the given to-do list and userPrefs.
     */
    public ModelManager(ReadOnlyTodoList todoList, ReadOnlyAliasListMap aliasListMap, UserPrefs userPrefs,
                          Config config) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, aliasListMap, userPrefs, config);

        logger.fine("Initializing with To-do List: " + todoList + " aliasListMap: " + aliasListMap
                + " and user prefs " + userPrefs + " and config: " + config);

        this.todoList = new TodoList(todoList);
        this.aliasListMap = new AliasListMap(aliasListMap);
        this.config = config;
        filteredTasks = new FilteredList<>(this.todoList.getTaskList());

        updateFilteredListToShowDefault();
        sortTasksByDefault();
        saveCurrentToHistory();
    }

    public ModelManager() {
        this(new TodoList(), new AliasListMap(), new UserPrefs(), new Config());
    }


    //=========== AliasListMap =============================================================
    /** Raises an event to indicate the alias list model has changed */

    @Override
    public ReadOnlyAliasListMap getAliasListMap() {
        return aliasListMap;
    }

    @Override
    public void setAlias(String alias, String commandWord) {
        aliasListMap.setAlias(alias, commandWord);
        indicateAliasListMapChanged();
    }

    @Override
    public void removeAlias(String alias) {
        aliasListMap.removeAlias(alias);
        indicateAliasListMapChanged();
    }

    @Override
    public List<String> getAliasList(String defaultCommandWord) {
        return aliasListMap.getAliasList(defaultCommandWord);
    }

    @Override
    public List<String> getValidCommandList(String defaultCommandWord) {
        List<String> list = new ArrayList<String>(aliasListMap.getAliasList(defaultCommandWord));
        list.add(defaultCommandWord);
        return list;
    }

    @Override
    public Set<String> getDefaultCommandWordSet() {
        return aliasListMap.getDefaultCommandWordSet();
    }

    @Override
    public void resetToDefaultCommandWords() {
        aliasListMap.setDefaultAliasListMapping();
    }

    private void indicateAliasListMapChanged() {
        raise(new AliasListMapChangedEvent(aliasListMap));
    }

    //@@author A0147980U
    //=========== TodoList =============================================================

    @Override
    public void resetData(ReadOnlyTodoList newData) {
        todoList.resetData(newData);
        indicateTodoListChanged();
    }

    @Override
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        indicateTodoListChanged();
    }

    //@@author A0140887W
    @Override
    public int finishTask(ReadOnlyTask target) throws TaskNotFoundException,
        TaskAlreadyFinishedException {
        assert target != null;
        try {
            todoList.changeTaskFinishStatus(target, true);
        } catch (TaskAlreadyUnfinishedException e) {
            assert false : "finishTask should not try to unfinish tasks!";
        }
        sortTasksByDefault();
        indicateTodoListChanged();
        return getFilteredTaskList().indexOf(target);
    }

    @Override
    public int unfinishTask(ReadOnlyTask target) throws TaskNotFoundException,
        TaskAlreadyUnfinishedException {
        assert target != null;
        try {
            todoList.changeTaskFinishStatus(target, false);
        } catch (TaskAlreadyFinishedException e) {
            assert false : "unfinishTask should not try to finish tasks!";
        }
        sortTasksByDefault();
        indicateTodoListChanged();
        return getFilteredTaskList().indexOf(target);
    }

    @Override
    public synchronized int addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        sortTasksByDefault();
        indicateTodoListChanged();
        return getFilteredTaskList().indexOf(task);
    }

    @Override
    public int updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int todoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        todoList.updateTask(todoListIndex, editedTask);
        sortTasksByDefault();
        indicateTodoListChanged();
        return getFilteredTaskList().indexOf(editedTask);
    }

    @Override
    public void sortTasksByDefault() {
        sortTasks(getDefaultSorting());
    }

    @Override
    public List<SortType> getDefaultSorting() {
        List<SortType> sortTypes = new ArrayList<SortType>();
        sortTypes.add(SortType.TIME);
        sortTypes.add(SortType.PRIORITY);
        sortTypes.add(SortType.ALPHA);
        return sortTypes;
    }

    @Override
    public void sortTasks(List<SortType> sortTypes) {
        todoList.sortTasks(parseSortTypesToComparator(sortTypes));
    }

    @Override
    public ReadOnlyTaskCombinedComparator parseSortTypesToComparator(List<SortType> sortTypes) {
        List<Comparator<ReadOnlyTask>> comparatorList = new ArrayList<Comparator<ReadOnlyTask>>();
        // Finished tasks are always put at the bottom
        comparatorList.add(new ReadOnlyTaskFinishedStatusComparator());
        for (SortType type : sortTypes) {
            if (type.equals(SortType.PRIORITY)) {
                comparatorList.add(new ReadOnlyTaskPriorityComparator());
            } else if (type.equals(SortType.TIME)) {
                comparatorList.add(new ReadOnlyTaskTimingComparator());
            } else if (type.equals(SortType.ALPHA)) {
                comparatorList.add(new ReadOnlyTaskAlphabetComparator());
            }
        }
        return new ReadOnlyTaskCombinedComparator(comparatorList);
    }

    //@@author A0147620L
    public ArrayList<String> getAllNames() {
        return todoList.getTaskNames();
    }

    //@@author
    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowDefault() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        Qualifier[] qualifiers = {new DescriptionQualifier(keywords)};
        updateFilteredTaskList(new PredicateExpression(qualifiers));
    }

    @Override
    public void updateFilteredTaskList(TaskType type, UniqueTagList tags) {
        ArrayList<Qualifier> qualifiers = new ArrayList<Qualifier>();
        if (type != null) {
            qualifiers.add(new TaskTypeQualifier(type));
        }
        if (!tags.isEmpty()) {
            qualifiers.add(new TagQualifier(tags));
        }
        updateFilteredTaskList(new PredicateExpression(qualifiers.toArray(new Qualifier[qualifiers.size()])));
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

        private final Qualifier[] qualifiers;

        PredicateExpression(Qualifier[] qualifiers) {
            this.qualifiers = qualifiers;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            boolean isSatisfied = true;
            for (Qualifier qualifier : qualifiers) {
                isSatisfied = isSatisfied && qualifier.run(task);
            }
            return isSatisfied;
        }

        @Override
        public String toString() {
            return qualifiers.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionKeyWords;

        DescriptionQualifier(Set<String> descKeyWords) {
            this.descriptionKeyWords = descKeyWords;
        }

        //@@author A0147620L
        @Override
        public boolean run(ReadOnlyTask task) {
            return descriptionKeyWords.stream()
                    .filter(keyword -> ((Double.compare(org.apache.commons.lang3.StringUtils.
                            getJaroWinklerDistance(task.getDescription().desc, keyword), 0.90) >= 0)
                            || (StringUtils.containsIgnoreCase(task.getDescription().desc, keyword))))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "desc=" + String.join(", ", descriptionKeyWords);
        }
    }

    //@@author A0147980U
    private class TagQualifier implements Qualifier {
        private UniqueTagList tags;

        public TagQualifier(UniqueTagList tags2) {
            this.tags = tags2;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            for (Tag tag : tags) {
                if (task.getTags().contains(tag)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class TaskTypeQualifier implements Qualifier {
        private TaskType type;

        public TaskTypeQualifier(TaskType type) {
            this.type = type;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (type) {
            case FINISHED:
                return task.getFinishedStatus().getIsFinished();
            case PENDING:
                return !task.getFinishedStatus().getIsFinished() && !task.isOverdue();
            case OVERDUE:
                return task.isOverdue();
            default:
                return true;
            }
        }
    }

    //========== handle undo and re-do operation =================================================
    public void saveCurrentToHistory() {
        todoListHistory.forgetStatesAfter();
        TodoList toSave = new TodoList();
        toSave.resetData(todoList);
        todoListHistory.addToHistory(toSave);
    }

    public boolean recoverPreviousTodoList() {
        boolean isAtMostRecentState = todoListHistory.isAtMostRecentState();
        TodoList previousTodoList = todoListHistory.getPreviousState();
        if (previousTodoList != null) {
            todoList.resetData(previousTodoList);
        } else {
            return false;
        }
        if (isAtMostRecentState) {
            recoverPreviousTodoList();
        }
        indicateTodoListChanged();
        return true;
    }

    public boolean recoverNextTodoList() {
        TodoList nextTodoList = todoListHistory.getNextState();
        if (nextTodoList != null) {
            todoList.resetData(nextTodoList);
        } else {
            return false;
        }
        indicateTodoListChanged();
        return true;
    }

  //@@author A0140887W
  //========== change absolute storage path =================================================
    @Override
    public void changeConfigAbsolutePath(Path path) {
        config.setAbsoluteStoragePath(path.toString());
        try {
            ConfigUtil.saveConfig(config, ConfigUtil.getConfigPath());
            indicateAbsoluteStoragePathChanged();
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
    }

    /** Raises an event to indicate the absolute storage path has changed */
    private void indicateAbsoluteStoragePathChanged() {
        raise(new AbsoluteStoragePathChangedEvent(config.getAbsoluteTodoListFilePath(),
                config.getAbsoluteAliasListMapFilePath(), config.getAbsoluteUserPrefsFilePath()));
    }
}
