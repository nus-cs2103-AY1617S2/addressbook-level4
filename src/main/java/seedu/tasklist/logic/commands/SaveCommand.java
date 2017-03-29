package seedu.tasklist.logic.commands;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.tasklist.commons.core.Config;
import seedu.tasklist.commons.util.ConfigUtil;

//@@author A0141993X
/**
 * Save user data file for task list.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the directory and file name of the data that is being saved.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/mytasklist.xml";

    public static final String MESSAGE_SUCCESS = "File is successfully saved to: %1$s";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path: %1$s";
    public static final String MESSAGE_FAILURE = "Unable to save file at specified path: %1$s";

    private final String filePath;
    private static final String XML = ".xml";

    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        assert filePath != null;
        assert model != null;

        if (!isValidPath(filePath)) {
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, filePath));
        }

        try {
            model.saveTaskList(filePath);
            updateFilePathOfConfigFile();
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

    /**
     * Updates the file path of config file with the new file path
     * specified by user, which gets reflected in the UI.
     *
     * @throws IOException
     */
    private void updateFilePathOfConfigFile() throws IOException {
        assert filePath != null;
        Config config = new Config();
        config.setTaskListFilePath(filePath);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

}
