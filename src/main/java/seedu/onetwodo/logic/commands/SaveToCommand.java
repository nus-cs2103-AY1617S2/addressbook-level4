package seedu.onetwodo.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.ConfigUtil;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.storage.StorageManager;

/**
 * Change the saving location of the task manager to a specified location.
 */
public class SaveToCommand extends Command {

    public static final String COMMAND_WORD = "save to";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the saving location of this task manager to a specified location.\n"
            + "Parameters: PATH (must be a valid file path. File name should ends with .xml)\n"
            + "Example: " + COMMAND_WORD + " data/newDataStorage.xml";

    public static final String MESSAGE_SAVETO_SUCCESS = "Change saving location to: %1$s";
    public static final String MESSAGE_SAVETO_FAILURE = "Failed to save data to %1$s";

    public final String filePath;

    public SaveToCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            File file = new File(filePath);
            if (FileUtil.isFileExists(file)) {
                System.out.println("READY TO OVERWRITE?!");
                // TODO: tell user to put "overwrite behind"
            } else {
                // change config & save config for future .
                // change storage file path.
                // update main window and event center
                Config config = MainApp.getConfig();
                StorageManager storage = (StorageManager) MainApp.getStorage();
                config.setToDoListFilePath(filePath);
                ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
                String updatedFilePath = config.getToDoListFilePath();
                storage.setToDoListFilePath(updatedFilePath);
                ReadOnlyToDoList toDoList = storage.getToDoListStorage().readToDoList().get();
                EventsCenter.getInstance().post(new ToDoListChangedEvent(toDoList));
            }
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_SAVETO_FAILURE + StringUtil.getDetails(ioe));
        } catch (DataConversionException dce) {
            return new CommandResult(MESSAGE_SAVETO_FAILURE + StringUtil.getDetails(dce));
        }

        return new CommandResult(String.format(MESSAGE_SAVETO_SUCCESS, filePath));
    }

}
