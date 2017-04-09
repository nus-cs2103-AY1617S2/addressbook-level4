package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.ReadOnlyTaskManager;

// @@author A0114269E
/**
 * Saves the current Task Manager to an XML file at user-specified path.
 * Overwrites the given file path if a file with same name exists. Old XML file is not deleted.
 * Path matching is case sensitive.
 */
public class SaveAsCommand extends Command {
    public static final String COMMAND_WORD = "saveas";
    public static final String ALTERNATIVE_COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Save the current taskmanager to given directory "
            + "to allow user to sync with cloud services. Overwrites existing file name.\n"
            + "Parameters: PATH...\n"
            + "Example: " + COMMAND_WORD + " /User/admin/Documents/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "TaskManager directory saved to : %1$s";
    public static final String MESSAGE_ERROR_BUILDCONFIG = "Failed to build new config";
    public static final String MESSAGE_ERROR_SAVE = "Failed to save TaskManager : '%1$s'";

    private final String newPath;

    public SaveAsCommand(String path) {
        this.newPath = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Config newConfig;
        String configFilePathUsed;
        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            newConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR_BUILDCONFIG);
        }

        newConfig.setTaskManagerFilePath(this.newPath);
        storage.updateTaskManagerStorageDirectory(this.newPath, newConfig);
        ReadOnlyTaskManager newTaskManager = model.getTaskManager();

        try {
            storage.saveTaskManager(newTaskManager);
            ConfigUtil.saveConfig(newConfig, configFilePathUsed);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_SAVE + StringUtil.getDetails(e));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.newPath));
    }
}
