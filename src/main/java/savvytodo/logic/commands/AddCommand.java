package savvytodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Description;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME p/PRIORTY d/DESCRIPTION a/ADDRESS  [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Dinner p/low d/with family a/311, Clementi Ave 2, #02-25 c/friends c/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String description, String address, Set<String> categories)
            throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new Description(description),
                new Address(address),
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
