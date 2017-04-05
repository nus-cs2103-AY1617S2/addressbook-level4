//@@author A0163559U
package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Saves task data in the specified directory.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves all tasks in specified directory with specified file name. "
            + "Parameters: SAVE_LOCATION\n"
            + "Example: " + COMMAND_WORD
            + " /Users/username/Documents/TaskManager/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Tasks saved in location: %1$s";
    public static final String MESSAGE_INVALID_SAVE_LOCATION = "This save location is invalid: %1$s";
    public static final String MESSAGE_NULL_SAVE_LOCATION = "A save location must be specified.";
    public static final String MESSAGE_DIRECTORY_SAVE_LOCATION = "A save location must also include the file name.";
    public static final String MESSAGE_SAVE_IO_EXCEPTION = "Failed to save file in location: %1$s";

    private final File toSave;
    /**
     * Creates an SaveCommand using raw values.
     *
     * @throws IllegalValueException if the save location is invalid
     * @throws IOException
     */
    public SaveCommand(String fileAsString) throws IllegalValueException {
        if (fileAsString == null || fileAsString.equals("")) {
            throw new IllegalValueException(MESSAGE_NULL_SAVE_LOCATION);
        }
        this.toSave = new File(fileAsString.trim());
        if (toSave.isDirectory()) {
            throw new IllegalValueException(MESSAGE_DIRECTORY_SAVE_LOCATION);
        }
        try {
            createSaveFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IllegalValueException(String.format(MESSAGE_INVALID_SAVE_LOCATION, fileAsString));
        }

    }

    private boolean createSaveFile() throws IOException {
        boolean created = toSave.createNewFile();

        return created;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        //        try {
        //            model.addTask(toAdd);
        //            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        //        } catch (UniqueTaskList.DuplicateTaskException e) {
        //            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        //        }

        System.out.println("Executing save command...");
        try {
            storage.saveTaskListInNewLocation(model.getTaskList(), toSave);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_SAVE_IO_EXCEPTION, toSave.toString()));
        }
        config.setTaskManagerFilePath(toSave.toString());
        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_SAVE_IO_EXCEPTION, toSave.toString()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSave.toString()));

    }

}
