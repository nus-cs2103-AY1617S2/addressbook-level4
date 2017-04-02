//@@author A0144813J
package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Changes the path of the storage file.
 */
public class UseCommand extends Command {

    public static final String COMMAND_WORD = "use";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " path/to/file.xml"
            + " or " + COMMAND_WORD + " path/to/file.json";

    public static final String MESSAGE_SUCCESS_STORAGE_FILE_PATH = "Storage file switched to %1$s";
    public static final String MESSAGE_SUCCESS_USER_PREFS_FILE_PATH = "User prefs file switched to %1$s";
    public static final String MESSAGE_INVALID_FILE_PATH = "The file path provided is not valid!\n"
            + "%1$s";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "The storage file data is incompatible!";

    private final String filePath;

    /**
     * Creates an Use Command
     */
    public UseCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (filePath.endsWith(".json")) {
            model.indicateUserPrefsFilePathChanged(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS_USER_PREFS_FILE_PATH, filePath));
        } else if (filePath.endsWith(".xml")) {
            try {
                model.indicateTaskManagerFilePathChanged(filePath);
            } catch (DataConversionException e) {
                throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
            } catch (IOException e) {
                throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH, MESSAGE_USAGE));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_STORAGE_FILE_PATH, filePath));
        } else {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH, MESSAGE_USAGE));
        }
    }

}
