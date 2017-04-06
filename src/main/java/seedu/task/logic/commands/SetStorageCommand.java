package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.task.MainApp;
import seedu.task.commons.core.Config;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163673Y
/**
 * Set the storage location for the data of this application.
 */
public class SetStorageCommand extends Command {

    public static final String COMMAND_WORD = "setstorage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set the storage location for the data of this application.\n"
            + "Parameters: Storage file path"
            + "Example: " + COMMAND_WORD + " /Users/Josh/Documents/tasks/";
    public static final String MESSAGE_INVALID_DIRECTORY = "%1$s is an invalid directory";
    public static final String MESSAGE_UNABLE_TO_MOVE_CONFIG = "Unable to move storage file to %1$s";
    public static final String MESSAGE_IO_ERROR = "Something has gone wrong, please try again: %1$s";
    public static final String MESSAGE_SET_STORAGE_SUCCESS = "Storage file path set to: %1$s";

    private String storagePath;

    public SetStorageCommand(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        // check if storage path is valid
        File newDirectory = new File(storagePath);
        if (!newDirectory.isDirectory()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DIRECTORY, newDirectory.getAbsolutePath()));
        }

        File oldFile = new File(MainApp.getInstance().getConfig().getAddressBookFilePath());
        String newFilePath = newDirectory.getPath() + "/tasklist.xml";
        File newFile = new File(newFilePath);

        // move current storage file to new file path
        if (oldFile.renameTo(newFile)) {
            // save new config file and update MainApp's config object to hold new storage file path
            Config newConfig = new Config();
            newConfig.setAddressBookFilePath(newFilePath);
            try {
                ConfigUtil.saveConfig(newConfig, Config.DEFAULT_CONFIG_FILE);
                MainApp.getInstance().setTaskListFilePath(newFilePath);
            } catch (IOException e) {
                throw new CommandException(String.format(MESSAGE_IO_ERROR, e.getMessage()));
            }
        } else {
            throw new CommandException(String.format(MESSAGE_UNABLE_TO_MOVE_CONFIG, newDirectory.getAbsolutePath()));
        }

        return new CommandResult(String.format(MESSAGE_SET_STORAGE_SUCCESS, newDirectory.getAbsolutePath()));
    }

}
