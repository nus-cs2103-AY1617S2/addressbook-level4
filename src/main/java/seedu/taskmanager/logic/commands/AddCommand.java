package seedu.taskmanager.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.UniqueTaskList;

//@@author A0139520L
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "ADD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager.\n"
    // + "Example: " + COMMAND_WORD
    // + " eat lunch ON thursday\n"
            + "Type HELP for user guide with detailed explanations of all commands";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_BLOCKED_OUT_TIME = "This task cannot be added as time clashes with another event";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String taskName, String startDate, String startTime, String endDate, String endTime,
            Set<String> categories) throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        this.toAdd = new Task(new TaskName(taskName), new StartDate(startDate), new StartTime(startTime),
                new EndDate(endDate), new EndTime(endTime), new Boolean(false), new UniqueCategoryList(categorySet));
    }

    // @@author A0142418L
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.updateFilteredListToShowAll();
            if (toAdd.isEventTask()) {
                int clashedTaskIndex = model.isBlockedOutTime(toAdd);
                if (clashedTaskIndex != -1) {
                    model.addTask(toAdd);
                    String clashFeedback = "Clash with task: Index " + Integer.toString(clashedTaskIndex) + "\n";
                    return new CommandResult(clashFeedback + String.format(MESSAGE_SUCCESS, toAdd));
                }
            }
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage() + MESSAGE_BLOCKED_OUT_TIME);
        }
    }
}
