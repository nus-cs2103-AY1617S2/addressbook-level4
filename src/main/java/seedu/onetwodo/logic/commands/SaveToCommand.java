package seedu.onetwodo.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.ConfigUtil;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;
import seedu.onetwodo.ui.MainWindow;

//@@author A0139343E
/**
 * Change the saving location of the task manager to a specified location.
 */
public class SaveToCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_WORD_OVERWRITE = "overwrite";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " ["
            + COMMAND_WORD_OVERWRITE + "] ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the saving location of this task manager to a specified location.\n"
            + "Parameters: PATH (must be a valid file path. File name should ends with .xml)\n"
            + MESSAGE_EXAMPLE + "data/newDataStorage.xml";

    public static final String MESSAGE_OVERWRITE_WARNING = "Warning: File name already exist! If you "
            + "wish to overwrite, add the word \"overwrite\".\n"
            + MESSAGE_EXAMPLE + "%1$s";
    public static final String MESSAGE_SAVETO_SUCCESS = "Change saving location to: %1$s";
    public static final String MESSAGE_SAVETO_FAILURE = "Failed to save data to %1$s";
    public static final String MESSAGE_SAVETO_SHOULD_BE_XML = "File should have a NAME with .xml behind.\n";
    public static final String MESSAGE_SAVETO_MAKE_FILE_FAIL = "Failed to create file. Make sure "
            + "the input does not contain invalid character.\n";


    public final String filePath;
    public boolean isOverWriting;

    public SaveToCommand(String filePath) {
        this.filePath = filePath;
        this.isOverWriting = false;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            File file = new File(filePath);
            if (!FileUtil.isValidPathName(filePath)) {
                return new CommandResult(MESSAGE_SAVETO_MAKE_FILE_FAIL);

            } else if (FileUtil.isFileExists(file) && !isOverWriting) {
                String result = String.format(MESSAGE_OVERWRITE_WARNING, filePath).toString();
                return new CommandResult(result);
            } else {

                Config config = MainApp.getInstance().getConfig();
                saveNewConfig(config);

                String updatedFilePath = config.getToDoListFilePath();
                saveNewStorage(updatedFilePath);
                updateFooter(updatedFilePath);
            }
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_SAVETO_FAILURE + StringUtil.getDetails(ioe));
        } catch (DataConversionException dce) {
            return new CommandResult(MESSAGE_SAVETO_FAILURE + StringUtil.getDetails(dce));
        }
        return new CommandResult(String.format(MESSAGE_SAVETO_SUCCESS, filePath));
    }

    public void setIsOverWriting(boolean result) {
        this.isOverWriting = result;
    }

    /**
     * set to new path and save new updates into config.json.
     * @param config config used by MainApp.
     */
    private void saveNewConfig(Config config) throws IOException {
        config.setToDoListFilePath(filePath);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    /**
     * set new path and copy data to new file.
     * @param updatedFilePath location to be saved for new file.
     * @return
     */
    private void saveNewStorage(String updatedFilePath) throws IOException, DataConversionException {
        StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();

        storageManager.setToDoListFilePath(updatedFilePath);
        toDoListStorage.saveToDoList(toDoList);
    }

    /**
     * updates the footer that shows current storage location.
     * @param updatedFilePath latest file location for storage.
     */
    private void updateFooter(String updatedFilePath) {
        MainWindow.getStatusBarFooter().setSaveLocation(updatedFilePath);
    }

}
