package seedu.taskboss.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.ui.JumpToCategoryListEvent;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 * Adds a task to the TaskBoss.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_SHORT = "a";
    //@@author A0144904H
    public static final String COMMAND_WORD_2ND_SHORT = "+";

    //@@author
    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Adds a task to TaskBoss. "
            + "Parameters: NAME [p/YES_NO] [sd/START_DATE] [ed/END_DATE] "
            + "[i/INFORMATION] [r/RECURRENCE] [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " Submit report p/yes sd/today 5pm ed/next friday 11.59pm"
            + " i/inform partner r/WEEKLY c/Work c/Project\n"
            + "Example: " + COMMAND_WORD_SHORT
            + " Watch movie sd/feb 19 c/Fun\n"
            + "Example : " + COMMAND_WORD_2ND_SHORT + " Call David sd/tomorrow i/inform"
            + " David of the new updates c/Project";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskBoss";
    public static final String ERROR_INVALID_ORDER_DATES = "Your end date is earlier than start date.";
    //@@author A0144904H
    public static final String BUILT_IN_ALL_TASKS = "Alltasks";
    public static final String BUILT_IN_DONE = "Done";
    public static final String ERROR_CANNOT_ADD_DONE_CATEGORY = "Cannot add Done category";

    //@@author A0143157J
    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws InvalidDatesException
     * @throws BuiltInCategoryException
     */
    public AddCommand(String name, String priorityLevel, String startDateTime, String endDateTime,
            String information, String frequency, Set<String> categories)
                    throws IllegalValueException, InvalidDatesException, BuiltInCategoryException {
        final Set<Category> categorySet = new HashSet<>();

        categoriesSetUp(categories, categorySet);

        Name taskName = new Name(name);
        PriorityLevel priorityLvl = new PriorityLevel(priorityLevel);
        DateTime startDateTimeObj = new DateTime(startDateTime);
        DateTime endDateTimeObj = new DateTime(endDateTime);
        String updatedFreq = initFrequency(frequency);

        checkDatesValidity(startDateTimeObj, endDateTimeObj);

        this.toAdd = new Task(
                taskName,
                priorityLvl,
                startDateTimeObj,
                endDateTimeObj,
                new Information(information),
                new Recurrence(Frequency.valueOf(updatedFreq)),
                new UniqueCategoryList(categorySet)
        );
    }

    /**
     * Throws InvalidDatesException if {@code startDateTimeObj} is later than {@code endDateTimeObj}
     */
    private void checkDatesValidity(DateTime startDateTimeObj, DateTime endDateTimeObj) throws InvalidDatesException {
        if (startDateTimeObj.getDate() != null && endDateTimeObj.getDate() != null &&
                startDateTimeObj.getDate().after(endDateTimeObj.getDate())) {
            throw new InvalidDatesException(ERROR_INVALID_ORDER_DATES);
        }
    }

    /**
     * Initialises value of {@code frequency}
     */
    private String initFrequency(String frequency) {
        String updatedFreq;
        if (frequency.isEmpty()) {
            updatedFreq = Frequency.NONE.toString();
        } else {
            updatedFreq = frequency.toUpperCase().trim();
        }
        return updatedFreq;
    }

    //@@author A0144904H
    /**
     * Sets up the set of categories a task is supposed to have
     * Built in Category "All Tasks" will be assigned to all tasks automatically
     * @param categories the set of categories being assigned to a task
     * @param categorySet the set of categories that is being added after modification occurs
     * @throws IllegalValueException
     * @throws BuiltInCategoryException
     */
    private void categoriesSetUp(Set<String> categories, final Set<Category> categorySet)
            throws IllegalValueException, BuiltInCategoryException {
        categorySet.add(new Category(BUILT_IN_ALL_TASKS));

        for (String categoryName : categories) {
            if ((new Category(categoryName)).equals(new Category(BUILT_IN_DONE))) {
                throw new BuiltInCategoryException(ERROR_CANNOT_ADD_DONE_CATEGORY);
            }
            categorySet.add(new Category(categoryName));
        }
    }

    //@@author A0143157J
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.updateFilteredListToShowAll();
            EventsCenter.getInstance().post(new JumpToCategoryListEvent(new Category("Alltasks")));
            scrollToTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        }

    }

    /**
     * Scrolls to the position of the added task
     */
    private void scrollToTask() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        int targetIndex = lastShownList.indexOf(toAdd);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
    }

}
