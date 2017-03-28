package typetask.logic.commands;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Task;

//@@author A0139926R
/**
 * Adds a task to the TaskManager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD2 = "a";
    public static final String COMMAND_WORD3 = "+";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task with and without date to the task list. \n"
            + "<> means compulsory [] means optional"
            + "Parameters: <NAME> by:[DATE][TIME]\n"
            + "Example: " + COMMAND_WORD
            + " Read Harry Potter book 1 ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    //Floating Task
    public AddCommand(String name)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(""), new DueDate(""), false);
    }
    //Deadline Task
    public AddCommand(String name, String dateTime)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(dateTime), new DueDate(""), false);
    }
    //Event Task
    public AddCommand(String name, String date, String endDate)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(date), new DueDate(endDate), false);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.storeTaskManager(COMMAND_WORD);
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
