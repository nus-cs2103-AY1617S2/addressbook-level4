package seedu.onetwodo.logic.commands;

import java.io.IOException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.ConfigUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;

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

    public final String filePath;
    public boolean isOverWriting;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            Config config = MainApp.getConfig();
            StorageManager storageManager = (StorageManager) MainApp.getStorage();
            ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();

            // set to new path
            // save new updates into config.json
            config.setToDoListFilePath(filePath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);

            // set to new path
            // copy data from new file to read it
            String updatedFilePath = config.getToDoListFilePath();
            storageManager.setToDoListFilePath(updatedFilePath);
            ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();
            toDoListStorage.saveToDoList(toDoList);

            EventsCenter.getInstance().post(new ToDoListChangedEvent(toDoList));

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

}
