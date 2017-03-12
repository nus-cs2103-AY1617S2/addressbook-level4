package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.IdentificationNumber;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME dl/DEADLINE ds/DESCRIPTION id/IDENTIFICATION NUMBER [t/TAG]....\n"
            + "Example: " + COMMAND_WORD
            + " John Doe dl/11/01/2017 ds/buy eggs id/311 t/lunch t/no eggs";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String deadline, String description, String id, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Deadline(deadline),
                new Description(description),
                new IdentificationNumber(id),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.updateCopy(model.getTaskManager());
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

}
