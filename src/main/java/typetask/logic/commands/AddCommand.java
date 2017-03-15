package typetask.logic.commands;


import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.Date;
import typetask.model.task.Name;
import typetask.model.task.Task;
import typetask.model.task.Time;


/**
 * Adds a task to the TaskManager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD2 = "a";
    public static final String COMMAND_WORD3 = "+";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME \n"
            + "Example: " + COMMAND_WORD
            + " Read Harry Potter book 1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new Date(""), new Time(""));
    }

    public AddCommand(String name, String date, String time)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new Date(date), new Time(time));
    }

    public AddCommand(String name, String date)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new Date(date), new Time(""));
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
