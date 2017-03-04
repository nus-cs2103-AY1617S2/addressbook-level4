package seedu.ezdo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.Address;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
<<<<<<< HEAD
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.Person;
import seedu.ezdo.model.todo.UniquePersonList;
=======
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.Phone;
import seedu.ezdo.model.todo.UniqueTaskList;
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e

/**
 * Adds a task to ezDo.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

<<<<<<< HEAD
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: NAME p/PRIORITY e/EMAIL a/ADDRESS  [t/TAG]...\n"
=======
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to ezDo. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e
            + "Example: " + COMMAND_WORD
            + " John Doe p/1 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in ezDo";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
<<<<<<< HEAD
    public AddCommand(String name, int priority, String email, String address, Set<String> tags)
=======
    public AddCommand(String name, String phone, String email, String startDate, Set<String> tags)
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new Email(email),
                new StartDate(startDate),
                new UniqueTagList(tagSet)
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
