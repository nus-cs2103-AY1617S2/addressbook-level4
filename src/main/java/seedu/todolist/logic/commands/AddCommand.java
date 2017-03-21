package seedu.todolist.logic.commands;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.parser.TaskParser;

/**
 * Adds a task to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to-do list. "
            + "Parameters: NAME [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " Reply boss's email t/HighPriority t/Email";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    private String commandText;
    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     * @throws IllegalValueException if any of the raw values are invalid.
     */
    public AddCommand(String rawCommandText) throws IllegalValueException {
        this.toAdd = TaskParser.parseTask(rawCommandText);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            commandText = String.format(MESSAGE_SUCCESS, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}
