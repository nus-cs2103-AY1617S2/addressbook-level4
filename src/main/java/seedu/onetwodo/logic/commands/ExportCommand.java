package seedu.onetwodo.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.FileUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.storage.ToDoListStorage;

//@@author A0139343E
/**
 * Export the todo list storage file to a specified location.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_WORD_OVERWRITE = "overwrite";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " ["
            + COMMAND_WORD_OVERWRITE + "] ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export the file of this task manager to a specified location.\n"
            + "Parameters: PATH (must be a valid file path. File name should ends with .xml)\n"
            + MESSAGE_EXAMPLE + "data/someName.xml";

    public static final String MESSAGE_OVERWRITE_WARNING = "Warning: File name already exist! If you "
            + "wish to overwrite, add the word \"overwrite\".\n"
            + MESSAGE_EXAMPLE + "%1$s";
    public static final String MESSAGE_EXPORT_SUCCESS = "Exported storage file to location: %1$s";
    public static final String MESSAGE_EXPORT_FAILURE = "Failed to export data to %1$s";
    public static final String MESSAGE_EXPORT_SHOULD_BE_XML = "File should have a NAME with .xml behind.\n";
    public static final String MESSAGE_EXPORT_MAKE_FILE_FAIL = "Failed to create file. Make sure "
            + "the input does not contain invalid character.\n";

    public final String filePath;
    public boolean isOverWriting;

    public ExportCommand(String filePath) {
        this.filePath = filePath;
        this.isOverWriting = false;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            File file = new File(filePath);
            if (!FileUtil.isValidPathName(filePath)) {
                return new CommandResult(MESSAGE_EXPORT_MAKE_FILE_FAIL);
            } else if (FileUtil.isFileExists(file) && !isOverWriting) {
                String result = String.format(MESSAGE_OVERWRITE_WARNING, filePath).toString();
                throw new CommandException(result);
            } else {

                Config config = MainApp.getInstance().getConfig();
                StorageManager storageManager = (StorageManager) MainApp.getInstance().getStorage();
                ToDoListStorage toDoListStorage = storageManager.getToDoListStorage();
                String currentFilePath = config.getToDoListFilePath();
                ReadOnlyToDoList toDoList = toDoListStorage.readToDoList().get();

                // set new file path
                // copy data to new file
                // set back file path
                storageManager.setToDoListFilePath(filePath);
                toDoListStorage.saveToDoList(toDoList);
                storageManager.setToDoListFilePath(currentFilePath);

            }
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_EXPORT_FAILURE + StringUtil.getDetails(ioe));
        } catch (DataConversionException dce) {
            return new CommandResult(MESSAGE_EXPORT_FAILURE + StringUtil.getDetails(dce));
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    public void setIsOverWriting(boolean result) {
        this.isOverWriting = result;
    }

}
