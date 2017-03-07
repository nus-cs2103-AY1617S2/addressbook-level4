package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Information;
import seedu.address.model.person.PriorityLevel;
import seedu.address.model.person.TaskName;
import seedu.address.model.person.UniqueTagList;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.Task;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: TASK_NAME d/DEADLINE p/PRIORITY_LEVEL [i/ANY_INFO] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Walk the dog d/07032017 p/1 i/Also makes sure he shits t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String deadline, String priorityLevel, String information, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        // Ensure Person class is refactored @qi xiang
        this.toAdd = new Task(
                new TaskName(taskName),
                new Deadline(deadline),
                new PriorityLevel(priorityLevel),
                new Information(information),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTagList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
