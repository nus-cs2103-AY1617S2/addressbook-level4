package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.Phone;
import seedu.address.model.task.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
<<<<<<< HEAD
 * Adds a task to the address book.
=======
 * Adds a task to the task manager.
>>>>>>> master
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
<<<<<<< HEAD
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
=======
            + "Parameters: TASK NAME p/START DATE & TIME e/END DATE & TIME a/ADDITIONAL DESCRIPTION  [t/TAG]...\n"
>>>>>>> master
            + "Example: " + COMMAND_WORD
            + " Task A p/12-3-2020 23:59 e/15-3-2020 23:59 a/secret mission A t/secret";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
<<<<<<< HEAD
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book";
=======
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the Task Manager";
>>>>>>> master

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String phone, String email, String address, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Phone(phone),
                new Email(email),
                new Description(address),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
