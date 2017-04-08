package seedu.taskmanager.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.storage.XmlTaskManagerStorage;

public class ChangeSaveLocationCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(ChangeSaveLocationCommand.class);

    public static final String COMMAND_WORD = "SAVE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of task manager.\n"
            + "Type HELP for user guide with detailed explanations of all commands\n";

    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK_MANAGER_FILE_PATH = "This file is already saved at this location";
    public static final String MESSAGE_CREATED_NEW_CONFIG_FILE = "TEST";

    private final File toSave;

    // @@author A0142418L
    public ChangeSaveLocationCommand(File location) {
        this.toSave = location;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Config config = new Config();
            config.setTaskManagerFilePath(toSave.toString());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);

            XmlTaskManagerStorage.setTaskManagerFilePath(toSave.toString());
            model.saveTaskManager();

            return new CommandResult(String.format(MESSAGE_SUCCESS, toSave));
        } catch (Config.DuplicateTaskManagerFilePathException dtmfpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK_MANAGER_FILE_PATH);
        } catch (IOException ioe) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(ioe));
            throw new CommandException(MESSAGE_CREATED_NEW_CONFIG_FILE);
        }
    }

}
