package seedu.tasklist.logic.commands;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.tasklist.logic.commands.exceptions.CommandException;

/**
 * Load user data file for task list.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": loads data from specified location.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/mytasklist.xml";

    public static final String MESSAGE_SUCCESS = "Data successfully loaded from: %1$s";
    public static final String MESSAGE_INVALID_PATH = "The file path specified is invalid: %1$s";
    public static final String MESSAGE_FAILURE = "The file cannot be loaded: %1$s";

    private String filePath;
    private static final String XML = ".xml";

    public LoadCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
       assert filePath != null;
       assert model != null;

        if (!isValidPath(filePath)) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        }

        try {
            model.loadTaskList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, filePath));
        }
    }

    /**
     * Returns true if the given path is a valid path
     */
    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
            return isXML(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    /**
     * Returns true if the given path ends with the .xml extension.
     */
    private static boolean isXML(String path) {
        return path.endsWith(XML);
    }



}
