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
public class LoadStorageCommand extends Command {

    public static final String COMMAND_WORD = "loadstorage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the data for this application from the specified location.\n"
            + "Parameters: Storage file path"
            + "Example: " + COMMAND_WORD + " /Users/Josh/Documents/tasks/";
    public static final String MESSAGE_FILE_NOT_FOUND = "Unable to find %1$s";
    public static final String MESSAGE_IO_ERROR = "Something has gone wrong, please try again: %1$s";
    public static final String MESSAGE_LOAD_STORAGE_SUCCESS = "Loaded tasklist.xml from: %1$s";

    private String storagePath;

    public LoadStorageCommand(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        // load new file
        File newStoragePath = new File(storagePath + "/tasklist.xml");
        if (!newStoragePath.exists()) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, newStoragePath.getAbsolutePath()));
        }

        // save new config file and update MainApp's config object to hold new storage file path
        Config newConfig = new Config();
        newConfig.setAddressBookFilePath(newStoragePath.getPath());
        try {
            ConfigUtil.saveConfig(newConfig, Config.DEFAULT_CONFIG_FILE);
            MainApp.getInstance().setTaskListFilePath(newStoragePath.getPath());
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_ERROR, e.getMessage()));
        }

        return new CommandResult(String.format(MESSAGE_LOAD_STORAGE_SUCCESS, newStoragePath.getAbsolutePath()));
    }

}
