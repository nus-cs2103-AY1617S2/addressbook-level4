package seedu.taskit.model;

import static seedu.taskit.logic.parser.CliSyntax.DEADLINE;
import static seedu.taskit.logic.parser.CliSyntax.DONE;
import static seedu.taskit.logic.parser.CliSyntax.EVENT;
import static seedu.taskit.logic.parser.CliSyntax.FLOATING;
import static seedu.taskit.logic.parser.CliSyntax.OVERDUE;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_HIGH;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_LOW;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_MEDIUM;
import static seedu.taskit.logic.parser.CliSyntax.TODAY;
import static seedu.taskit.logic.parser.CliSyntax.UNDONE;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.transformation.FilteredList;
import seedu.taskit.commons.core.ComponentManager;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.commons.events.model.TaskManagerChangedEvent;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.commons.exceptions.NoValidStateException;
import seedu.taskit.commons.util.CollectionUtil;
import seedu.taskit.commons.util.StringUtil;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;
import seedu.taskit.model.task.UniqueTaskList.DuplicateMarkingException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    //@A0141011J
    private final Stack<State> prevStates = new Stack<State>();
    private final Stack<State> nextStates = new Stack<State>();
    //@@author generated

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(addressBook);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskManager.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //@@author A0141872E
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateAddressBookChanged();
    }

    public void markTask(ReadOnlyTask taskToMark, String parameter) throws DuplicateMarkingException {
        taskManager.markTask(taskToMark, parameter);
        indicateAddressBookChanged();
    }//@@author

//=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@author A0141872E
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public int updateFilteredTaskList(String parameter) {
        updateFilteredTaskList(new PredicateExpression(new ParameterQualifier(parameter)));
        return filteredTasks.size();
    }
    //@@author generated

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

    //@@author A0097141H
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {


          String[] monthsArr = {"january", "jan", "february", "feb", "march", "mar", "april", "apr", "may", "june", "jun",
                      "july", "jul", "august", "aug", "september", "sept", "sep", "october", "oct",
                      "november", "nov", "december", "dec"};
          ArrayList<String> months = new ArrayList<String>() ;
          Collections.addAll(months, monthsArr);
          //filter by date to see if searching for date



          return
              nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.toStringTitleTagAndDateList(), keyword.toLowerCase()))
                    .findAny()
                    .isPresent() |
                    nameKeyWords.stream()
                    .filter(keyword -> task.toStringTitleTagAndDateList().contains(keyword.toLowerCase()))
                    .findAny()
                   .isPresent() |

                    //this is to find if keywords match dates

               nameKeyWords.stream()
                    .filter(k -> months.contains(k.toLowerCase()))
                    .filter(k -> {
                  try {
                    return (new Date(k)).isMonthEqualsMonth(task.getEnd());
                  } catch (IllegalValueException | NullPointerException e1) {
                    //e1.printStackTrace();
                    return false;
                  }
                })
                    .findAny()
                    .isPresent() |

                    nameKeyWords.stream()
                    .filter(keyword -> {
            try {
              return task.getEnd().isDateEqualsDate(new Date(keyword)) | task.getStart().isDateEqualsDate(new Date(keyword));
            } catch (IllegalValueException e) {
              return false;
            }
          })
                    .findAny()
                    .isPresent()
                    ;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0141872E
    private class ParameterQualifier implements Qualifier {
        private String parameter;

        ParameterQualifier(String parameter) {
            this.parameter=parameter;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (parameter){
            case DONE:
                return task.isDone();

            case UNDONE:
                return !task.isDone();

            case OVERDUE:
                return task.isOverdue() && !task.isDone();

            case PRIORITY_LOW:
            case PRIORITY_MEDIUM:
            case PRIORITY_HIGH:
                return task.getPriority().toString().equals(parameter);

            case TODAY:
                return !task.isDone() && task.getEnd().isDateEqualCurrentDate();

            case FLOATING:
                return !task.isDone() && task.isFloating();

            case EVENT:
                return !task.isDone() && task.isEvent();

            case DEADLINE:
                return !task.isDone() && task.isDeadline();

            default:
                return false;
            }
        }

        @Override
        public String toString() {
            return "parameter=" + String.join(", ", parameter);
        }

    }//@@author


    //@@author A0141011J
    //========== Inner classes/functions used for filtering =================================================

    private static class State {
        final ReadOnlyTaskManager data;
        final Predicate<? super ReadOnlyTask> filterPredicate;

        public State(ModelManager mm) {
            data = new TaskManager(mm.getAddressBook());
            filterPredicate = mm.filteredTasks.getPredicate();
        }
    }

    public void revert() throws NoValidStateException {
        if (prevStates.isEmpty()) {
            throw new NoValidStateException();
        } else {
            nextStates.push(new State(this));
            load(prevStates.pop());
            indicateAddressBookChanged();
        }
    }

    public void redo() throws NoValidStateException {
        if (nextStates.isEmpty()) {
            throw new NoValidStateException();
        } else {
            prevStates.push(new State(this));
            load(nextStates.pop());
            indicateAddressBookChanged();
        }
    }

    public void save() {
        prevStates.push(new State(this));
        nextStates.clear();
    }

    private void load(State state) {
        resetData(state.data);
        filteredTasks.setPredicate(state.filterPredicate);
    }
}
