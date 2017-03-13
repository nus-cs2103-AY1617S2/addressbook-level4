package t15b1.taskcrusher.logic.commands;

import java.util.Date;
import java.util.List;

import java.util.HashSet;
import java.util.Set;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.logic.commands.exceptions.CommandException;
import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.shared.Name;
import t15b1.taskcrusher.model.tag.Tag;
import t15b1.taskcrusher.model.tag.UniqueTagList;
import t15b1.taskcrusher.model.task.Deadline;
import t15b1.taskcrusher.model.task.Email;
import t15b1.taskcrusher.model.task.Priority;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;

/**
 * Adds a task to user inbox.
 */
public class AddCommand extends Command {
	
    public static final String COMMAND_WORD = "add";
    public static final String TASK_FLAG = "t";
    public static final String EVENT_FLAG = "e";

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
    public AddCommand(String name, String deadline, String priority, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Deadline(deadline),
                new Priority(priority),
                new Description(description),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, List<Date> deadline, String priority, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Deadline(deadline),
                new Priority(priority),
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
