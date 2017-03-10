package seedu.taskcrusher.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskcrusher.commons.exceptions.IllegalValueException;
import seedu.taskcrusher.logic.commands.exceptions.CommandException;
import seedu.taskcrusher.model.shared.Description;
import seedu.taskcrusher.model.shared.Name;
import seedu.taskcrusher.model.tag.Tag;
import seedu.taskcrusher.model.tag.UniqueTagList;
import seedu.taskcrusher.model.task.Email;
import seedu.taskcrusher.model.task.Priority;
import seedu.taskcrusher.model.task.Task;
import seedu.taskcrusher.model.task.UniqueTaskList;

/**
 * Adds a task to user inbox.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    //TODO these messages
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the active list. "
            + "Parameters: TASK_NAME dt/DEADLINE p/PRIORITY //DESCRIPTION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Greet Akshay dt/today p/3 //this is a must t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the active list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
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
