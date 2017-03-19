package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Information;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
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
    public AddCommand(String taskName, String deadline, String priorityLevel, String info, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new TaskName(taskName),
                new Deadline(deadline),
                new PriorityLevel(priorityLevel),
                new Information(info),
                new UniqueTagList(tagSet)
        );
        toAdd.setParserInfo("add");
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            GlobalStack gStack = GlobalStack.getInstance();
            gStack.getUndoStack().push(toAdd);
            /**Debugging purpose
             * gStack.printStack();
             */
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
}
