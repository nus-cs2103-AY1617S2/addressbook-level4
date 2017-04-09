package seedu.task.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;
    //@@author A0139161J
    private FilteredList<ReadOnlyTask> completedTasks;
    private FilteredList<ReadOnlyTask> overdueTasks;
    //@@author
    /**
     * Initializes a ModelManager with the given task manager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);
        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        //@@author A0139161J
        completedTasks = new FilteredList<>(this.taskManager.getCompletedTaskList());
        overdueTasks = new FilteredList<>(this.taskManager.getOverdueTaskList());
        //@@author
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
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    //@@author A0139161J
    @Override
    public void insertTasktoIndex(int indexToBeRestored, Task deletedTask)
            throws DuplicateTaskException {
        taskManager.addTaskToIndex(indexToBeRestored, deletedTask);
    }

    @Override
    public void loadList(ObservableList<ReadOnlyTask> list) throws DuplicateTaskException {
        taskManager.setTasks(list);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCompletedTaskList() {
        return new UnmodifiableObservableList<>(completedTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getOverdueList() {
        return new UnmodifiableObservableList<>(overdueTasks);
    }

    @Override
    public void completeTask(Task t) throws DuplicateTaskException, TaskNotFoundException {
        taskManager.transferTaskToComplete(t);
        indicateTaskManagerChanged();
    }

    @Override
    public void uncompleteTask(Task t) throws DuplicateTaskException, TaskNotFoundException {
        taskManager.transferTaskFromComplete(t);
        indicateTaskManagerChanged();
    }

    @Override
    public void deleteCompletedTask(ReadOnlyTask t) throws TaskNotFoundException {
        taskManager.deleteCompletedTask(t);
        indicateTaskManagerChanged();
    }

    //@@author

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
        updateFilteredTaskList(new PredicateExpression(new TotalQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0135762A
    @Override
    public void updateExactFilteredTaskList(Set<String> keywords) {
        updateExactFilteredTaskList(new PredicateExpression(new ExactQualifier(keywords)));
    }

    private void updateExactFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    //@@author

    @Override
    public synchronized void truncateOverdueList() {
        overdueTasks = new FilteredList<ReadOnlyTask>(taskManager.getTaskList());
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy @ HH:mm");
        Date currentDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);

        Predicate<? super ReadOnlyTask> pred  = s -> {
            try {
                return df.parse(s.getDate().toString()).before(currentDate);
            } catch (ParseException e) {
                return false;
            }
        };
        overdueTasks.setPredicate(pred);
        try {
            taskManager.setOverdueTasks(overdueTasks);
        } catch (DuplicateTaskException e) {
            assert false : "There will be no duplicated tasks";
        }
    }

    //@@author A0135762A
    @Override
    public void updateUpcomingTaskList() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy @ HH:mm");
        Date currentDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);

        Predicate<? super ReadOnlyTask> pred  = s -> {
            try {
                return df.parse(s.getDate().toString()).after(currentDate) &&
                        df.parse(s.getDate().toString()).before(c.getTime());
            } catch (ParseException e) {
                return false;
            }
        };
        filteredTasks.setPredicate(pred);
    }

    //@@author A0139322L
    @Override
    public void updateFilteredTagTaskList(String tagName) throws IllegalValueException {
        Tag tag;
        tag = new Tag(tagName);

        Predicate<? super ReadOnlyTask> pred = s -> s.getTags().contains(tag);
        filteredTasks.setPredicate(pred);
    }

    //@@author A0135762A
    @Override
    public void updatePriorityTaskList() {
        Predicate<? super ReadOnlyTask> pred  = s -> s.getPriority().value.equals("1");
        filteredTasks.setPredicate(pred);
    }

    @Override
    public void updateOverdueTaskList() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy @ HH:mm");
        Date currentDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);

        Predicate<? super ReadOnlyTask> pred  = s -> {
            try {
                return df.parse(s.getDate().toString()).before(currentDate);
            } catch (ParseException e) {
                return false;
            }
        };
        filteredTasks.setPredicate(pred);
    }
    //@@author

    //@@author A0135762A
    //========== Inner classes/methods used for Near Match Search =================================================

    // The Levenshtein distance is a string metric for measuring the difference between two sequences.
    private class LevenshteinDistance {
        private int minimum(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }

        private int computeLevenshteinDistance(String lhs, String rhs) {
            int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

            for (int i = 0; i <= lhs.length(); i++) {
                distance[i][0] = i;
            }
            for (int j = 1; j <= rhs.length(); j++) {
                distance[0][j] = j;
            }

            for (int i = 1; i <= lhs.length(); i++) {
                for (int j = 1; j <= rhs.length(); j++) {
                    distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1,
                    distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
                }
            }

            return distance[lhs.length()][rhs.length()];
        }

        public boolean validLevenshteinDistance(String lhs, String rhs) {
            // Levenshtein Distance will only be computed if a string consist of more than 3 letters.
            if (rhs.length() < 4) {
                return false;
            }

            for (String word : lhs.split(" ")) {
                int value = computeLevenshteinDistance(word, rhs);
                // Maximum one letter difference allowed.
                if (value < 2) {
                    return true;
                }
            }

            return false;
        }
    }

    // A Transcription Error is a specific type of data entry error that is commonly made by human operators.
    private class TranspositionError {
        private String swap(String str, int index1, int index2) {
            char[] c = str.toCharArray();
            char temp = c[index1];
            c[index1] = c[index2];
            c[index2] = temp;

            String swapString = new String(c);
            return swapString;
        }

        public boolean validTranspositionError(String lhs, String rhs) {
            // Transcription Error will only be computed if a string consist of more than 3 letters.
            if (rhs.length() < 4) {
                return false;
            }

            for (String word : lhs.split(" ")) {
                for (int i = 0; i < rhs.length() - 1; i++) {
                    if (word.equals(swap(rhs, i, i + 1))) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    // Take into account all the criteria and return the result.
    private boolean finalNearMatchSearch(String lhs, String rhs) {
        String newLHS = lhs.toLowerCase();
        String newRHS = rhs.toLowerCase();

        LevenshteinDistance result1 = new LevenshteinDistance();
        TranspositionError result2 = new TranspositionError();

        if (StringUtil.containsWordIgnoreCase(newLHS, newRHS)) {
            return true;
        } else if (newLHS.contains(newRHS)) {
            return true;
        } else if (result1.validLevenshteinDistance(newLHS, newRHS)) {
            return true;
        } else if (result2.validTranspositionError(newLHS, newRHS)) {
            return true;
        } else {
            return false;
        }
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

    private class TotalQualifier implements Qualifier {
        private Set<String> totalKeyWords;

        TotalQualifier(Set<String> totalKeyWords) {
            this.totalKeyWords = totalKeyWords;
        }

        //@@author-A0139322L
        @Override
        public boolean run(ReadOnlyTask task) {
            return totalKeyWords.stream()
                    .filter(keyword -> finalNearMatchSearch(task.getTaskName().taskName, keyword)
                            || finalNearMatchSearch(task.getInfo().value, keyword))
                    .findAny()
                    .isPresent();
        }
        //@@author

        @Override
        public String toString() {
            return "keywords =" + String.join(", ", totalKeyWords);
        }
    }

    //@@author A0135762A
    private class ExactQualifier implements Qualifier {
        private Set<String> exactKeyWords;

        ExactQualifier(Set<String> totalKeyWords) {
            this.exactKeyWords = totalKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return exactKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().taskName, keyword)
                            || StringUtil.containsWordIgnoreCase(task.getInfo().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "keywords =" + String.join(", ", exactKeyWords);
        }
    }
    //@@author
}
