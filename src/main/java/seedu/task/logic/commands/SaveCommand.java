package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.storage.ChangeStorageFilePathEvent;

//@@author A0141928B
/**
 * Changes the destination of the file saved
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves task data to a specified location "
            + "Parameters: PATH_TO_FILE\\FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD
            + " C:\\doTask\\mytasks.xml";

    public static final String MESSAGE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_INVALID_FILE_TYPE = "File name must end in .xml";

    private final String filePath;

    public SaveCommand(String path) {
        assert path != null;
        this.filePath = path.trim();
    }

    @Override
    public CommandResult execute() {
        if (filePath.endsWith(".xml")) {
            EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(filePath));
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } else {
            return new CommandResult(MESSAGE_INVALID_FILE_TYPE);
        }
    }
}
