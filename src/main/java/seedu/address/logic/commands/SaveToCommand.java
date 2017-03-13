package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/*
 * Changes task manager save location to specified file path.
 */
public class SaveToCommand extends Command {

    public static final String COMMAND_WORD = "saveto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " to: Saves to the specified directory.\n"
            + "Parameters: dir_location\n" + "Example: " + COMMAND_WORD + "to .\\example_folder";
    public static final String MESSAGE_SUCCESS = "Save location has been changed to: %1$s";
    public static final String MESSAGE_CONFIG_SAVE_FAILURE = "Unable to save config file.";
    public static final String MESSAGE_WRITE_ACCESS_DENIED = "No write permissions to: %1$s";

    private static final String TASK_MANAGER_FILE_NAME = "taskmanager.xml";

    private final String saveToDir;

    /**
     * Creates an SaveToCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public SaveToCommand(String dirLocation) {
        this.saveToDir = dirLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String path = new File(saveToDir, TASK_MANAGER_FILE_NAME).getAbsolutePath();

        if (FileUtil.isWritable(path)) {
            storage.setTaskManagerFilePath(path);
            config.setTaskManagerFilePath(path);
            model.updateSaveLocation();
        } else {
            throw new CommandException(String.format(MESSAGE_WRITE_ACCESS_DENIED, path));
        }

        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CONFIG_SAVE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, path));
    }

}
