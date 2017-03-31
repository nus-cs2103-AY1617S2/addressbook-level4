package savvytodo.logic.commands;

import savvytodo.commons.core.EventsCenter;
import savvytodo.commons.events.storage.LoadStorageFileEvent;
import savvytodo.logic.commands.exceptions.CommandException;

/**
 * Loads a storage XML file from the given filepath
 * @author A0147827U
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads a storage XML file to the task manager. "
            + "Parameters: FILEPATH" + "Example: " + COMMAND_WORD + " " + "/Users/Bob/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Storage file loaded from %1$s";
    public static final String MESSAGE_FILE_NOT_FOUND = "Storage file at %1$s not found/is invalid.";

    private String filePath;

    public LoadCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new LoadStorageFileEvent(filePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

}
