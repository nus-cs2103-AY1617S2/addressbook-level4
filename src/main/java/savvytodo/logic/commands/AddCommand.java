package savvytodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Email;
import savvytodo.model.task.Name;
import savvytodo.model.task.Task;
import savvytodo.model.task.Phone;
import savvytodo.model.task.UniqueTaskList;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String phone, String email, String address, Set<String> categories)
            throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Phone(phone),
                new Email(email),
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
