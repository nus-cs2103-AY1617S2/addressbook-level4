package seedu.watodo.logic.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.watodo.commons.core.Config;
import seedu.watodo.commons.core.EventsCenter;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.events.storage.StorageFilePathChangedEvent;
import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.commons.util.ConfigUtil;
import seedu.watodo.commons.util.StringUtil;
import seedu.watodo.storage.XmlTaskListStorage;

//@@author A0141077L
/**
 * Changes the save location of the TaskManager data.
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "saveas";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves Watodo task list to the new specified file path "
            + "and loads task list from that location in the future.\n"
            + "File path must end with .xml\n"
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " data/watodo2.xml";

    public static final String MESSAGE_DUPLICATE_FILE_PATH = "New storage file location must be "
            + "different from the current one";
    public static final String MESSAGE_INVALID_FILE_PATH_FORMAT = "File name must end with .xml";
    public static final String MESSAGE_SUCCESS = "Storage file location moved to %1$s";

    private String oldFilePath;
    private String newFilePath;
    private Config currConfig;
    private static final Logger logger = LogsCenter.getLogger(SaveAsCommand.class);


    public SaveAsCommand(String newFilePath) {
        assert newFilePath != null;
        this.currConfig = getConfig();
        this.oldFilePath = currConfig.getWatodoFilePath();
        this.newFilePath = newFilePath;
    }

    private Config getConfig() {
        Config initialisedConfig;

        try {
            Optional<Config> optionalConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            initialisedConfig = optionalConfig.orElse(new Config());
        } catch (DataConversionException dce) {
            initialisedConfig = new Config();
        }
        return initialisedConfig;
    }

    @Override
    public CommandResult execute() { //TODO Catch exceptions
        try {
            checkFilePaths();
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
            return new CommandResult(ive.getMessage());
        }

        try {
            copyFileData();
            updateFilePath();
        } catch (IOException ioe) {
            return new CommandResult (ioe.getMessage());
        }

        EventsCenter.getInstance().post(new StorageFilePathChangedEvent(this.newFilePath));
        logger.log(Level.INFO, "Storage file location moved successfully.");
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.newFilePath));
    }

    private void checkFilePaths() throws IllegalValueException {
        if (this.oldFilePath.equals(this.newFilePath)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_FILE_PATH);
        }
    }

    private void copyFileData() throws IOException {
        try {
            XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(oldFilePath);
            xmlTaskListStorage.saveTaskList(model.getTaskManager(), newFilePath);

        } catch (IOException ioe) {
            ioe.printStackTrace(); //TODO Check this vs StringUtil.getDetails
        }
    }

    private void updateFilePath() throws IOException {
        try {
            currConfig.setWatodoFilePath(newFilePath);
            ConfigUtil.saveConfig(currConfig, Config.DEFAULT_CONFIG_FILE);

        } catch (IOException ioe) {
            logger.warning("Failed to save config file: " + StringUtil.getDetails(ioe)); //TODO After warning leave it?
        }
    }

}
