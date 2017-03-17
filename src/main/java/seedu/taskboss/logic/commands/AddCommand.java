package seedu.taskboss.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 * Adds a task to the TaskBoss.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to TaskBoss. "
            + "Parameters: NAME [p/PRIORITY_LEVEL] [sd/START_DATE] [ed/END_DATE] "
            + "[i/INFORMATION] [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " n/Submit report p/3 sd/today 5pm ed/next friday 11.59pm i/inform partner c/Work c/Project";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskBoss";
    public static final String ERROR_INVALID_DATES = "Your end date is earlier than start date.";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws InvalidDatesException
     */
    public AddCommand(String name, String priorityLevel, String startDateTime, String endDateTime,
            String information, Set<String> categories) throws IllegalValueException, InvalidDatesException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        DateTime startDateTimeObj = new DateTime(startDateTime);
        DateTime endDateTimeObj = new DateTime(endDateTime);

        if (startDateTimeObj.getDate() != null && endDateTimeObj.getDate() != null &&
                startDateTimeObj.getDate().after(endDateTimeObj.getDate())) {
            throw new InvalidDatesException(ERROR_INVALID_DATES);
        }

        this.toAdd = new Task(
                new Name(name),
                new PriorityLevel(priorityLevel),
                startDateTimeObj,
                endDateTimeObj,
                new Information(information),
                new UniqueCategoryList(categorySet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
