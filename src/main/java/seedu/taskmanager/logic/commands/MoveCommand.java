package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.ReadOnlyTaskManager;

/**
 * @@author A0114269E
 * Move the directory of taskmanager.xml file to user-specified path to allow cloud service sync.
 * Overwrite the given file path if a file with same name exists. Old XML file is not deleted.
 * Path matching is case sensitive.
 */
public class MoveCommand extends Command {
    public static final String COMMAND_WORD = "move";
    public static final String ALTERNATIVE_COMMAND_WORD = "movefile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Move the directory of the taskmanager."
            + "xml file to allow user to sync with cloud services. Overwrite will occur for same file name.\n"
            + "Parameters: PATH...\n"
            + "Example: " + COMMAND_WORD + " /User/admin/Documents/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "TaskManager directory moved to : ";
    public static final String MESSAGE_ERROR_BUILDCONFIG = "Failed to build new config";
    public static final String MESSAGE_ERROR_SAVE = "Failed to save TaskManager : '%1$s'";

    private final String newPath;

    public MoveCommand(String path) {
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

        return new CommandResult(MESSAGE_SUCCESS + this.newPath);
    }
}
