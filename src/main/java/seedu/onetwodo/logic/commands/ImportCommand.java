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
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;
import seedu.onetwodo.ui.MainWindow;

//@@author A0139343E
/**
 * Import the todo list storage file from a specified location.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import the file of this task manager from a specified location.\n"
            + "Parameters: PATH (must be a valid file path. File name should ends with .xml)\n"
            + MESSAGE_EXAMPLE + "data/someName.xml";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported storage file from location: %1$s";
    public static final String MESSAGE_IMPORT_FAILURE = "Failed to import data from %1$s";
    public static final String MESSAGE_IMPORT_FILE_MISSING = "File does not exist!\n";
    public static final String MESSAGE_IMPORT_SHOULD_BE_XML = "File should have a NAME with .xml behind.\n";


    public final String filePath;
    public boolean isOverWriting;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public synchronized CommandResult execute() throws CommandException {

        try {
            File file = new File(filePath);
            if (!FileUtil.isFileExists(file)) {
                String result = MESSAGE_IMPORT_FILE_MISSING
                        + String.format(MESSAGE_IMPORT_FAILURE, filePath).toString();
                return new CommandResult(result);
            } else {

                Config config = MainApp.getInstance().getConfig();
                saveNewConfig(config);

                String updatedFilePath = config.getToDoListFilePath();
                saveNewStorage(updatedFilePath);
                updateFooter(updatedFilePath);
            }

        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_IMPORT_FAILURE + StringUtil.getDetails(ioe));
        } catch (DataConversionException dce) {
            return new CommandResult(MESSAGE_IMPORT_FAILURE + StringUtil.getDetails(dce));
        }
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath));
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
        Model model = MainApp.getInstance().getModel();
        storageManager.setToDoListFilePath(updatedFilePath);
        ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
        ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();
        toDoListStorage.saveToDoList(toDoList);
        model.resetData(toDoList);
    }

    /**
     * updates the footer that shows current storage location.
     * @param updatedFilePath latest file location for storage.
     */
    private void updateFooter(String updatedFilePath) {
        MainWindow.getStatusBarFooter().setSaveLocation(updatedFilePath);
    }

}
