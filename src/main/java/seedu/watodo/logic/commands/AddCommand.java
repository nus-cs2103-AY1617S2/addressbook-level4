package seedu.watodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.logic.parser.DateTimeParser.TaskType;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK [by/ DATETIME] [from/ START_DATETIME] [to/ END_DATE_TIME] [#TAG]...\n" + "Example: "
            + COMMAND_WORD + " read Lord of the rings by/ next thurs #personal";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private Task toAdd;

    /**
     * Creates an AddCommand using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String startDate, String endDate, Set<String> tags, TaskType taskType)
            throws IllegalValueException {

        assert description != null;
        assert taskType.equals(TaskType.FLOAT) || taskType.equals(TaskType.DEADLINE) ||taskType.equals(TaskType.EVENT);
       
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        switch (taskType) {
        case FLOAT:
            this.toAdd = new Task(new Description(description), new UniqueTagList(tagSet));
            break;
        case DEADLINE:
            this.toAdd = new Task(new Description(description), new DateTime(endDate),
                    new UniqueTagList(tagSet));
            break;
        case EVENT:
            DateTime start = new DateTime(startDate);
            DateTime end = new DateTime(endDate);
            if (start.isLater(end)) {
                throw new IllegalValueException("End date must be later than start date!");
            }
            
            
            
            this.toAdd = new Task(new Description(description), start, end, new UniqueTagList(tagSet));
            break;
        }
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
