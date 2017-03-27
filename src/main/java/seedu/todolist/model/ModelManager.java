package seedu.todolist.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.events.model.ViewListChangedEvent;
import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<Task> filteredTasks;
    private boolean isViewIncomplete, isViewComplete, isViewOverdue, isViewUpcoming, isViewAll;
    private static final String RESET = "reset";
    private static final String DELETE = "delete";
    private static final String ADD = "add";
    private static final String COMPLETE = "complete";
    private static final String UPDATE = "update";


    /**
     * Initializes a ModelManager with the given to-do list and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with to-do list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
        getFilteredIncompleteTaskList();
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
        isViewAll = true;
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged(RESET);
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged(String typeOfCommand) {
        int index = toDoList.getTaskList().toArray().length;
        raise(new ToDoListChangedEvent(toDoList, index, typeOfCommand));
    }

    private void indicateToDoListChanged(String typeOfCommand, int index) {
        raise(new ToDoListChangedEvent(toDoList, index, typeOfCommand));
    }

    //@@author A0144240W
    /** Raises an event to indicate that the filteredList has changed */
    private void indicateViewListChanged(String typeOfList) {
        raise(new ViewListChangedEvent(typeOfList));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        toDoList.removeTask(target);
        indicateToDoListChanged(DELETE);
    }

    @Override
    public synchronized void completeTask(int filteredTaskListIndex, Task target) throws TaskNotFoundException {
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.completeTask(toDoListIndex, target);
        indicateToDoListChanged(COMPLETE);
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        getFilteredIncompleteTaskList();
        indicateToDoListChanged(ADD);
       // EventsCenter.getInstance().post(new JumpToListRequestEvent(index - 1));

    }

    @Override
    public void updateTask(int filteredTaskListIndex, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoList.updateTask(toDoListIndex, editedTask);
        indicateToDoListChanged(UPDATE, toDoListIndex);
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }


    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredIncompleteTaskList() {
        resetViews();
        isViewIncomplete = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return !task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_INCOMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredCompleteTaskList() {
        resetViews();
        isViewComplete = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_COMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredOverdueTaskList() {
        resetViews();
        isViewOverdue = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return isOverdue(task);
        });
        indicateViewListChanged(ListCommand.TYPE_OVERDUE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0139633B
    public boolean isOverdue(Task task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date currentDate = new Date();
        if (task.getEndTime() != null) {
            String taskDateString = task.getEndTime().toString();
            try {
                Date taskDate = dateFormat.parse(taskDateString);
                return currentDate.compareTo(taskDate) > 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }


    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredUpcomingTaskList() {
        resetViews();
        isViewUpcoming = true;
        //get tasks that are incomplete and are not overdue
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return isUpcoming(task);
        });
        System.out.println(filteredTasks);
        SortedList<Task> sortedList = new SortedList<Task>(filteredTasks, dateComparator);
        System.out.println(sortedList);
        indicateViewListChanged(ListCommand.TYPE_UPCOMING);
        return new UnmodifiableObservableList<>(sortedList);
    }

    private boolean isUpcoming(Task task) {
      //get current time and compare with the task's end time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date currentDate = new Date();
        if (task.getEndTime() != null) {
            String taskDateString = task.getEndTime().toString();
            try {
                Date taskDate = dateFormat.parse(taskDateString);
                return currentDate.compareTo(taskDate) <= 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    //Comparator for Date
    //@@author A0139633B
    Comparator<? super Task> dateComparator = new Comparator<Task>() {
        @Override
        public int compare(Task firstTask, Task secondTask) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
            String firstTaskDueDateString = firstTask.getEndTime().toString();
            String secondTaskDueDateString = secondTask.getEndTime().toString();
            try {
                Date firstTaskDueDate = dateFormat.parse(firstTaskDueDateString);
                Date secondTaskDueDate = dateFormat.parse(secondTaskDueDateString);
                return firstTaskDueDate.compareTo(secondTaskDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0; //dummy value
            }
        }
    };

    @Override
    public void updateFilteredListToShowAll() {
        resetViews();
        isViewAll = true;
        indicateViewListChanged(ListCommand.TYPE_ALL);
        filteredTasks.setPredicate(null);
    }

    //@@author A0144240W
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        if (isViewIncomplete) {
            filteredTasks.setPredicate(findInIncomplete(keywords));
        } else if (isViewComplete) {
            filteredTasks.setPredicate(findInComplete(keywords));
        } else if (isViewOverdue) {
            filteredTasks.setPredicate(findInOverdue(keywords));
        } else if (isViewUpcoming) {
            filteredTasks.setPredicate(findInUpcoming(keywords));
        } else {
            filteredTasks.setPredicate(findInAll(keywords));
        }
    }


  //=========== Methods to help in filtering task list with given keywords ==============================

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the incomplete view
     * @param keywords
     * @return
     */
    private Predicate<Task> findInIncomplete(Set<String> keywords) {
        return p -> !p.isComplete() && containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the complete view
     * @param keywords cannot be null
     *
     */
    private Predicate<Task> findInComplete(Set<String> keywords) {
        return p -> p.isComplete() && containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the overdue view
     * @param keywords cannot be null
     *
     */
    private Predicate<Task> findInOverdue(Set<String> keywords) {
        return p -> isOverdue(p) && containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the upcoming view
     * @param keywords cannot be null
     *
     */
    private Predicate<Task> findInUpcoming(Set<String> keywords) {
        return p -> isUpcoming(p) && containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the all view
     * @param keywords cannot be null
     *
     */
    private Predicate<Task> findInAll(Set<String> keywords) {
        return p -> containsIgnoreCase(p, keywords);
    }


    //@@author A0144240W
    /**
     * Returns true if the task has keywords in its tags or name
     * @param keywords cannot be null
     *
     */
    private boolean containsIgnoreCase(Task task, Set<String> keywords) {
        for (String word : keywords) {
            String preppedWord = word.trim();
            if (preppedWord.startsWith("t/")) {
                String preppedTag = preppedWord.substring(2);
                if (matchTag(task.getTags().toSet(), preppedTag)) {
                    return true;
                }
            } else {
                if (matchName(task.getName().toString(), preppedWord)) {
                    return true;
                }
            }
        }
        return false;
    }

    //@@author A0144240W
    /**
     * Returns true if the set of tags contain the searchTag
     * Ignores case, a full tag match is required.
     * @return
     */
    private boolean matchTag(Set<Tag> tags, String searchTag) {
        for (Tag tag : tags) {
            if (tag.getTagName().toLowerCase().equals(searchTag.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    //@@author A0144240W
    /**
     * Returns true if the taskName contains the searchName
     * Ignores case, a full word match is not required.
     * @param taskName must not be null
     * @param searchName must not be null
     *
     */
    private boolean matchName(String taskName, String searchName) {
        if (taskName.toLowerCase().contains(searchName.toLowerCase())) {
            return true;
        }
        return false;
    }

    //@@author A0144240W
    /**
     * Resets the boolean values of the views to false.
     */
    private void resetViews() {
        isViewComplete = false;
        isViewIncomplete = false;
        isViewOverdue = false;
        isViewUpcoming = false;
        isViewAll = false;
    }
}




