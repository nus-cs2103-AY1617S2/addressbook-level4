package savvytodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME [dt/START_DATE = END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] "
            + "[r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Project Meeting dt/05/10/2016 1400 = 05/10/2016 1800 r/weekly 2 c/CS2103 "
            + "d/Discuss about roles and milestones";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String description, String location, String[] dateTime,
            String[] recurrence, Set<String> categories) throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new Description(description),
                new Location(location),
                new UniqueCategoryList(categorySet),
                new DateTime(dateTime),
                new Recurrence(recurrence)
        );

        this.toAdd.setStatus(new Status());
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
