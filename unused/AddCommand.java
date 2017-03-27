package seedu.watodo.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.DeadlineTask;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.EventTask;
import seedu.watodo.model.task.FloatingTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

//@@author A0143076J-unused
//not used because re-implemented the add function to give flexible ordering of the task description
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK [by/ DATETIME] [from/ START_DATETIME] [to/ END_DATE_TIME] [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " read Lord of the rings by/ next thurs #personal";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    public enum TaskType { FLOAT, DEADLINE, EVENT, INVALID }
    private Task toAdd;

    /**
     * Creates an AddCommand using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, boolean hasDeadline, Optional<String> deadline, boolean hasStartDate,
            Optional<String> startDate, boolean hasEndDate, Optional<String> endDate, Set<String> tags)
        throws IllegalValueException {

        assert description != null;

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        TaskType taskType = checkTaskType(hasDeadline, hasStartDate, hasEndDate);

        switch (taskType) {
        case FLOAT:
            this.toAdd = new FloatingTask(new Description(description), new UniqueTagList(tagSet));
            break;
        case DEADLINE:
            this.toAdd = new DeadlineTask(new Description(description), new DateTime(deadline.get()),
                    new UniqueTagList(tagSet));
            break;
        case EVENT:
            this.toAdd = new EventTask(new Description(description), new DateTime(startDate.get()),
                    new DateTime(endDate.get()), new UniqueTagList(tagSet));
            break;
        case INVALID:
        default:
            throw new IllegalValueException("Too many/few DATETIME arguments!");
        }
    }

    /**
     * Checks the type of task(floating, deadline or event) to be added
     * based on the DATETIME parameters entered by the user.
     * @throws IllegalValueException if both deadline and startDate are entered,
     * or if only one of startDate or endDate is entered
     */
    private static TaskType checkTaskType(boolean hasDeadline, boolean hasStartDate, boolean hasEndDate)
            throws IllegalValueException {
        if (!hasDeadline && !hasStartDate && !hasEndDate) {
            return TaskType.FLOAT;
        }
        if (hasDeadline && !hasStartDate && !hasEndDate) {
            return TaskType.DEADLINE;
        }
        if (!hasDeadline && hasStartDate && hasEndDate) {
            return TaskType.EVENT;
        }
        return TaskType.INVALID;
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
