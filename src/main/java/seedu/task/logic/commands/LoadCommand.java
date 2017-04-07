package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.storage.LoadDataEvent;
import seedu.task.commons.events.ui.ChangePathNameEvent;
import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163845X
public class LoadCommand extends Command {

    private final String path;

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": loads a save file for " + "Fast Task data \n"
            + "Parameters: PATHNAME\n" + "Example: " + COMMAND_WORD + " C:\\Program Files\\DropBox\\saveFile.xml\n "
            + "               " + COMMAND_WORD + " /Users/name/Desktop/MyTasks.xml";

    public static final String MESSAGE_SUCCESS = "Successfully loaded the save file.";
    public static final String MESSAGE_FAIL = "Not a valid path";

    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        System.out.println("test");
        try {
            EventsCenter.getInstance().post(new LoadDataEvent(path));
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAIL);
        }
    }

}
