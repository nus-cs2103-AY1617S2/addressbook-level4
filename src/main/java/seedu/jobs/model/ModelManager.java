package seedu.jobs.model;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.jobs.commons.core.ComponentManager;
import seedu.jobs.commons.core.LogsCenter;
import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.commons.events.model.TaskBookChangedEvent;
import seedu.jobs.commons.events.storage.SavePathChangedEvent;
import seedu.jobs.commons.util.CollectionUtil;
import seedu.jobs.logic.commands.FindCommand;
import seedu.jobs.logic.commands.ListCommand;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * @throws IllegalTimeException
     */
    public ModelManager(ReadOnlyTaskBook taskBook, UserPrefs userPrefs) throws IllegalTimeException {
        super();
        assert !CollectionUtil.isAnyNull(taskBook, userPrefs);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs);

        this.taskBook = new TaskBook(taskBook);
        filteredTasks = new FilteredList<>(this.taskBook.getTaskList());
    }

    public ModelManager() throws IllegalTimeException {
        this(new TaskBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) throws IllegalTimeException {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void completeTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.completeTask(index, target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalTimeException {
        assert editedTask != null;

        int taskBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskBook.updateTask(taskBookIndex, editedTask);
        indicateTaskBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
  //@@author A0164440M
    @Override
    public void updateFilteredListToShowAll(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, ListCommand.COMMAND_WORD)));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, FindCommand.COMMAND_WORD)));
    }
    //@@author

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //=========== Undo & redo commands =======================================================================
  //@@author A0164440M
    @Override
    public synchronized void undoCommand() throws EmptyStackException {
        taskBook.undoTask();
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void redoCommand() throws EmptyStackException {
        taskBook.redoTask();
        indicateTaskBookChanged();
    }

  //=========== path command =======================================================================
  //@@author A0130979U
    @Override
    public void changePath(String path) throws IOException {
        raise(new SavePathChangedEvent(path, taskBook));
    }
  //@@author
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
        
      //@@author A0164440M
        //Reuse the function to implement filtered list
        private String commandWord;
        private String taskInfo = "";

        NameQualifier(Set<String> nameKeyWords, String commandWord) {
            this.nameKeyWords = nameKeyWords;
            this.commandWord = commandWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (commandWord.equals(FindCommand.COMMAND_WORD)) {
                //Enable find command to find according description and tags
                taskInfo = task.getName().fullName + task.getDescription().toString()
                        + task.getTags().toString();
            }
            if (commandWord.equals(ListCommand.COMMAND_WORD)) {
                //Enable to list completed tasks or in-progress tasks
                if(nameKeyWords.size() == 1 && (nameKeyWords.contains("complete") ||
                        nameKeyWords.contains("in-progress") )) {
                    taskInfo = task.isCompleted() ? "complete" : "in-progress";
                }
              //@@author
            }
            return nameKeyWords.stream()
                    .filter(keyword -> taskInfo.toLowerCase().contains(keyword.toLowerCase()))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
