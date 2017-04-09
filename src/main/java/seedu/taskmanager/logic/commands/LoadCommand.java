package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;

// @@author A0114269E
/**
 * Loads Task Manager from user-specified path XML file and changes the
 * directory to that file path. If no XML is found, starting a new Task Manager
 * with new XML file at given file path Path matching is case sensitive.
 */
public class LoadCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String ALTERNATIVE_COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the directory of the taskmanager."
            + "xml file to allow user to sync with cloud services\n" + "Parameters: PATH...\n" + "Example: "
            + COMMAND_WORD + " /User/admin/Documents/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "TaskManager successfully loaded : %1$s";
    public static final String MESSAGE_NEW_FILE = "WARNING! No XML file is found\n"
            + "Starting a new Task Manager with XML file at : %1$s\n";
    public static final String MESSAGE_ERROR_BUILDCONFIG = "Failed to build new config";
    public static final String MESSAGE_ERROR_SAVECONFIG = "Failed to save config file : '%1$s'";
    public static final String MESSAGE_INVALID_DATA = "Invalid XML file: Unable to load.";
    public static final String MESSAGE_ERROR_IO = "Failed to read/write TaskManager.";

    private final String newPath;

    public LoadCommand(String path) {
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

        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager newTaskManager;
        String oldStorageDirectory = storage.getTaskManagerFilePath();

        try {
            taskManagerOptional = storage.readTaskManager(this.newPath);
            newTaskManager = taskManagerOptional.orElse(new TaskManager());
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_INVALID_DATA);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_IO);
        }

        newConfig.setTaskManagerFilePath(this.newPath);
        storage.updateTaskManagerStorageDirectory(this.newPath, newConfig);

        try {
            storage.saveTaskManager(newTaskManager);
            ConfigUtil.saveConfig(newConfig, configFilePathUsed);
        } catch (IOException e) {
            newConfig.setTaskManagerFilePath(oldStorageDirectory);
            storage.updateTaskManagerStorageDirectory(oldStorageDirectory, newConfig);
            throw new CommandException(MESSAGE_ERROR_IO);
        }

        model.resetData(newTaskManager);
        model.updateFilteredListToShowAll();
        if (!taskManagerOptional.isPresent()) {
            return new CommandResult(String.format(MESSAGE_NEW_FILE, this.newPath));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.newPath));
    }
}
