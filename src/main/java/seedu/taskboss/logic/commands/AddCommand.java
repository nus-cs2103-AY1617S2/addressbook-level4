package seedu.taskboss.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.DefaultCategoryException;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Adds a task to TaskBoss. "
            + "Parameters: NAME [p/YES_NO] sd/START_DATE] [ed/END_DATE] "
            + "[i/INFORMATION] [r/RECURRENCE] [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " Submit report p/yes sd/today 5pm ed/next friday 11.59pm"
            + " i/inform partner r/WEEKLY c/Work c/Project\n"
            + "Example: " + COMMAND_WORD_SHORT
            + " Watch movie sd/feb 19 c/Fun";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskBoss";
    public static final String ERROR_INVALID_DATES = "Your end date is earlier than start date.";
    //@@author A0144904H
    public static final String DEFAULT_ALL_TASKS = "AllTasks";
    public static final String DEFAULT_DONE = "Done";
    public static final String ERROR_CANNOT_ADD_DONE_CATEGORY = "Cannot add Done category";

    //@@author
    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws InvalidDatesException
     * @throws DefaultCategoryException
     */
    public AddCommand(String name, String priorityLevel, String startDateTime, String endDateTime,
            String information, String frequency, Set<String> categories)
                    throws IllegalValueException, InvalidDatesException, DefaultCategoryException {
        final Set<Category> categorySet = new HashSet<>();

        categoriesSetUp(categories, categorySet);

        //@@author A0143157J
        String updatedFreq;
        if (frequency.isEmpty()) {
            updatedFreq = Frequency.NONE.toString();
        } else {
            updatedFreq = frequency.toUpperCase().trim();
        }

        Name taskName = new Name(name);
        PriorityLevel priorityLvl = new PriorityLevel(priorityLevel);
        DateTime startDateTimeObj = new DateTime(startDateTime);
        DateTime endDateTimeObj = new DateTime(endDateTime);

        if (startDateTimeObj.getDate() != null && endDateTimeObj.getDate() != null &&
                startDateTimeObj.getDate().after(endDateTimeObj.getDate())) {
            throw new InvalidDatesException(ERROR_INVALID_DATES);
        }

        //@@author
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

    //@@author A0144904H
    /**
     * Sets up the set of categories a task is supposed to have
     * default Category "All Tasks" will be assigned to all tasks automatically
     * @param categories the set of categories being assigned to a task
     * @param categorySet the set of categories that is being added after modification occurs
     * @throws IllegalValueException
     * @throws DefaultCategoryException
     */
    private void categoriesSetUp(Set<String> categories, final Set<Category> categorySet)
            throws IllegalValueException, DefaultCategoryException {
        categorySet.add(new Category(DEFAULT_ALL_TASKS));
        if (categories.contains(DEFAULT_DONE)) {
            throw new DefaultCategoryException(ERROR_CANNOT_ADD_DONE_CATEGORY);
        }

        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
    }

    //@@author A0144904h
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;
        try {
            model.addTask(toAdd);
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            int targetIndex = lastShownList.indexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        }

    }

}
