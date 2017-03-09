package seedu.watodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.Address;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Email;
import seedu.watodo.model.task.FloatingTask;
import seedu.watodo.model.task.Phone;
import seedu.watodo.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a floating task to the task manager. "
            + "Parameters: TASK [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " read Lord of the Rings #personal";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final FloatingTask toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new FloatingTask(
                new Description(description),
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
