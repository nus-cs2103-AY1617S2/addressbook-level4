//@@author A0162266E
package werkbook.task.logic.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.exceptions.CommandException;

/**
 * Change the save location of the task list
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of the tasks.\n"
            + "The folder path can be relative or absolute.\n"
            + "Parameters: Save Folder Path"
            + "Example: " + COMMAND_WORD
            + " ./NewSaveFolder";

    public static final String MESSAGE_SUCCESS = "Save location changed successfully";
    public static final String MESSAGE_INVALID_PATH = "Specified folder is invalid";
    public static final String MESSAGE_FOLDER_NOT_EXIST = "Specified folder does not exist";
    public static final String MESSAGE_NOT_A_DIRECTORY = "Specified path is not a folder";
    public static final String MESSAGE_DIRECTORY_NOT_WRITABLE = "Specifed folder is not writable";
    public static final String MESSAGE_IO_ERROR = "Error writing to file";

    private final Path newPath;
    /**
     * Creates a SaveCommand using specified path.
     *
     * @throws IllegalValueException if the path is invalid
     */
    public SaveCommand(Path path)
        throws IllegalValueException {
        if (!Files.exists(path)) {
            throw new IllegalValueException(MESSAGE_FOLDER_NOT_EXIST);
        } else if (!Files.isDirectory(path)) {
            throw new IllegalValueException(MESSAGE_NOT_A_DIRECTORY);
        } else if (!Files.isWritable(path)) {
            throw new IllegalValueException(MESSAGE_DIRECTORY_NOT_WRITABLE);
        }

        this.newPath = path.resolve("tasklist.xml");
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            storage.setTaskListFilePath(this.newPath);
            model.indicateTaskListChanged();
        } catch (IOException e) {
            return new CommandResult(MESSAGE_IO_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean isMutable() {
        return false;
    }
}
