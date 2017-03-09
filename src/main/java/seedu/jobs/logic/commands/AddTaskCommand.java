package seedu.jobs.logic.commands;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.Name;

public class AddTaskCommand extends Command{
	
	public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to JOBS. "
            + "Parameters: NAME \n"
            + "Example: " + COMMAND_WORD
            + " CS2103 tutorial";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in JOBS";
    private final Task toAdd;
    
	public AddTaskCommand(String taskName)throws IllegalValueException{
		this.toAdd = new Task(new Name(taskName));
	}
	
	@Override
	public CommandResult execute() throws CommandException {
		assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
	}

}
