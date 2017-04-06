package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME [due/DATE] [starts/START ends/END] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy milk and eggs due/08/09/2017 1400"
            + " starts/today 1000 ends/tomorrow 1200 t/home t/important";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String inputDueDate, String startDate, String endDate, Set<String> tags)
            throws IllegalValueException {
        Duration duration = (startDate == "" || endDate == "") ?
                null :
                new Duration(startDate, endDate);
        DueDate dueDate = (inputDueDate == "") ?
                null :
                new DueDate(inputDueDate);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Long id = System.currentTimeMillis();
        this.toAdd = new Task(
                new Description(description),
                dueDate,
                duration,
                new UniqueTagList(tagSet),
                new Complete(false),
                new TaskId(id)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            mementos.addUndoMementoAndClearRedo(new TaskMemento(null, toAdd));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
}
