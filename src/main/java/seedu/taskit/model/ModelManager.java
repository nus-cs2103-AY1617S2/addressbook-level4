package seedu.taskit.model;

import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import javafx.collections.transformation.FilteredList;
import seedu.taskit.commons.core.ComponentManager;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.commons.events.model.AddressBookChangedEvent;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.commons.exceptions.NoValidStateException;
import seedu.taskit.commons.util.CollectionUtil;
import seedu.taskit.commons.util.StringUtil;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyTask> filteredTasks;



    //@A0141011J
    private final Stack<State> prevStates = new Stack<State>();
    private final Stack<State> nextStates = new Stack<State>();
    //@@author

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        addressBook.updateTask(addressBookIndex, editedTask);
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

    //@@author

    //@@author A0141872E
    private class ParameterQualifier implements Qualifier {
        private String parameter;

        ParameterQualifier(String parameter) {
            this.parameter=parameter;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (parameter){
                case "done":
                    return task.isDone();

                case "undone":
                    return !task.isDone();

                case "overdue":
                    return task.isOverdue() && !task.isDone();

                case "low":
                    return task.getPriority().toString().equals("low");

                case "medium":
                    return task.getPriority().toString().equals("medium");

                case "high":
                    return task.getPriority().toString().equals("high");

                case "today":
                    return !task.isDone() && task.getEnd().isDateEqualCurrentDate();

                default:
                    return false;
            }
        }

        @Override
        public String toString() {
            return "parameter=" + String.join(", ", parameter);
        }

    }


    //@@author A0141011J
    //========== Inner classes/functions used for filtering =================================================

    private static class State {
        final ReadOnlyAddressBook data;
        final Predicate<? super ReadOnlyTask> filterPredicate;

        public State(ModelManager mm) {
            data = new AddressBook(mm.getAddressBook());
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
