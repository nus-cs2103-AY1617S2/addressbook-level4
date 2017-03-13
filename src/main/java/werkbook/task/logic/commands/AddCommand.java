package werkbook.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list.\n"
            + "The task must have a task name, description is optional.\n"
            + "The task can optionally have an end date and time "
            + "but is required if it also has a start date and time.\n"
            + "Parameters: Task name [d/Description] [s/Start date and time] [e/End date and time]  [t/Tag]...\n"
            + "Example: " + COMMAND_WORD
            + " Walk the dog d/Take Zelda on a walk around the park s/01/01/2017 1000 e/01/01/2017 1200 t/Important";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String description, String startDateTime, String endDateTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Description(description),
                new StartDateTime(startDateTime),
                new EndDateTime(endDateTime),
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

    @Override
    public boolean isMutable() {
        return true;
    }
}
