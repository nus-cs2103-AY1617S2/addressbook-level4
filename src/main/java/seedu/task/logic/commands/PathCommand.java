package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

/**
 * @author A0164061N
 *
 */

public class PathCommand extends Command {
	
	private final String path;
	 
	public static final String COMMAND_WORD = "path";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the path of the save location for "
		    + "task manager data \n" + "Parameters: PATHNAME\n" + "Example: " 
			+ COMMAND_WORD + " C:\\Program Files\\DropBox";

	public static final String MESSAGE_SUCCESS = "Successfully changed the save path.";
	public static final String MESSAGE_FAIL = "Not a valid path";
	
    public PathCommand(String path) {
		this.path = path;
	}
    
    public String getPath(){
    	return path;
    }
	
	@Override
	public CommandResult execute() throws CommandException {
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
