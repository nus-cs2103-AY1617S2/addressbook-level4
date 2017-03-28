package typetask.logic.commands;


import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.DueDate;
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
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task with and without date to the task list. \n"
            + " Does not allow time without date. \n"
            + "Parameters: NAME by:DATE @TIME\n"
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
        this.toAdd = new Task(new Name(name), new DueDate(""), new DueDate(""),
                new Time(""), new Time(""), false);
    }
    //Task with date and time
    public AddCommand(String name, String date, String time)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(date), new DueDate(""),
                new Time(time), new Time(""), false);
    }
    //Task with date
    public AddCommand(String name, String date)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(date), new DueDate(""),
                new Time(""), new Time(""), false);
    }
    //Task with time
    public AddCommand(String name, String time, int noDate)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(""), new DueDate(""),
                new Time(time), new Time(""), false);
    }
    //Event Task with no time
    public AddCommand(String name, String date, String endDate, int noTime)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(date), new DueDate(endDate),
                new Time(""), new Time(""), false);
    }
    //Event Task with time
    public AddCommand(String name, String date, String endDate, String time, String endTime)
            throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new DueDate(date), new DueDate(endDate),
                new Time(time), new Time(endTime), false);
    }
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.storeTaskManager(COMMAND_WORD);
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
