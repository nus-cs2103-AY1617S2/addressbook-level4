//@@author A0163720M
package seedu.todolist.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.ConfigUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.storage.StorageManager;

/*
 * Updates the save file location
 */
public class SaveFileCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(SaveFileCommand.class);
    private String saveFilePath;
    public static final String MESSAGE_CONFIG_ERROR = "Error handling the config file.";
    public static final String MESSAGE_SUCCESS = "Save file updated!";
    public static final String COMMAND_WORD = "savefile";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the save file location.\n"
            + "Parameters: PATH (must be a valid path string pointing to existing .xml file)\n" + "Example: "
            + COMMAND_WORD + " data/newtodolist.xml";

    public SaveFileCommand(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            // If the file does not exist, create it (as per Ashkay's recommendations)
            File f = new File(saveFilePath);
            final boolean fileCreated = f.createNewFile();

            if (fileCreated) {
                logger.info("File " + saveFilePath + " did not exist. Created file instead.");
            }

            // There should only be one instance of config each session - grab a
            // handle on that specific one
            Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
            String todoListFilePath = config.getTodoListFilePath();
            String userPrefsFilePath = config.getUserPrefsFilePath();
            StorageManager storageManager = new StorageManager(todoListFilePath,  userPrefsFilePath);
            storageManager.updateSaveFilePath(saveFilePath);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DataConversionException e) {
            // Catch for data conversion from Optional<Config> to Config
            throw new CommandException(MESSAGE_CONFIG_ERROR);
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
    }
}
//@@author
