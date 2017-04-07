package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ChangePathNameEvent;
import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0164061N

public class PathCommand extends Command {

    private final String path;

    public static final String COMMAND_WORD = "path";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the path of the save location for "
	    + "Fast Task data \n" + "Parameters: PATHNAME\n" + "Examples: " + COMMAND_WORD
	    + " C:\\Program Files\\DropBox\\saveFile.xml\n " + "                 " + COMMAND_WORD
	    + " /Users/name/Desktop/MyTasks.xml";

    public static final String MESSAGE_SUCCESS = "Successfully changed the save path.";
    public static final String MESSAGE_FAIL = "Not a valid path";

    public PathCommand(String path) {
	this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
	EventsCenter.getInstance().post(new ChangePathNameEvent(path));
	return new CommandResult(MESSAGE_SUCCESS);
    }
}
