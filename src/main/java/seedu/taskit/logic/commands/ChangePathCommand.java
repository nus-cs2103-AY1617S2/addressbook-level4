//@@author A0141011J

package seedu.taskit.logic.commands;

import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.taskit.commons.core.EventsCenter;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.events.storage.StorageFilePathChangedEvent;
import seedu.taskit.logic.commands.CommandResult;

public class ChangePathCommand extends Command {
    private Logger logger = LogsCenter.getLogger(ChangePathCommand.class.getName());

    public static final String COMMAND_WORD = "path";

    public static final String MESSAGE_SUCCESS = "File path changed to ";

    public static final String MESSAGE_INVALID_PATH = "The new file path specified is invalid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the file path for the task manager to be stored.\n"
            + "Parameters: FILEPATH (must be a string)\n"
            + "Example: " + COMMAND_WORD + "newfolder";

    private String newFilePath;

    public ChangePathCommand(String newPath) {
        newFilePath = newPath.trim().replace("\\", "/") + "/taskit.xml";
        logger.info("New file path: " + this.newFilePath);
    }

    public CommandResult execute() {
        EventsCenter.getInstance().post(new StorageFilePathChangedEvent(newFilePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS + Paths.get(newFilePath).toAbsolutePath()));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
