package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163845X
public class FilterCommand extends Command {
    private String status;
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "Results filtered";
    public static final String MESSAGE_FAILURE = "Failed to filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " command filters the list to show only completed " + "or ongoing tasks\n" + "Parameters: completed, ongoing\n" + "Examples: " + COMMAND_WORD + " completed\n "
	    + "                 " + COMMAND_WORD + " ongoing ";
    public static final String MESSAGE_FAIL = "Not a valid command";
    public FilterCommand(String status) {
	this.status = status;
    }

    @Override
    public CommandResult execute() throws CommandException {
	assert model != null;
	try {
	    System.out.println(status);
	    model.filterStatus(status);
	    return new CommandResult(MESSAGE_SUCCESS);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return new CommandResult(MESSAGE_FAIL);
	}
    }

}
