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
 * Change the directory of taskmanager.xml file to user-specified path to allow cloud service sync.
 * Path matching is case sensitive.
 */
public class ChangeDirectoryCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String ALTERNATIVE_COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the directory of the taskmanager."
            + "xml file to allow user to sync with cloud services\n"
            + "Parameters: PATH...\n"
            + "Example: " + COMMAND_WORD + " /User/admin/Documents/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "TaskManager directory changed to : ";
    public static final String MESSAGE_ERROR_BUILDCONFIG = "Failed to build new config";
    public static final String MESSAGE_ERROR_SAVECONFIG = "Failed to save config file : '%1$s'";
    public static final String MESSAGE_INVALID_DATA = "Invalid XML file: Unable to load";
    public static final String MESSAGE_ERROR_READ_TASKMANAGER = "Failed to read TaskManager";

    private final String newPath;

    public ChangeDirectoryCommand(String path) {
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
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager newTaskManager;

        try {
            taskManagerOptional = storage.readTaskManager(this.newPath);
            newTaskManager = taskManagerOptional.orElse(model.getTaskManager());
            storage.updateTaskManagerStorageDirectory(this.newPath, newConfig);
            model.resetData(newTaskManager);
            model.updateFilteredListToShowAll();
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_INVALID_DATA);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READ_TASKMANAGER);
        }

        try {
            ConfigUtil.saveConfig(newConfig, configFilePathUsed);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_SAVECONFIG + StringUtil.getDetails(e));
        }

        return new CommandResult(MESSAGE_SUCCESS + this.newPath);
    }
}
