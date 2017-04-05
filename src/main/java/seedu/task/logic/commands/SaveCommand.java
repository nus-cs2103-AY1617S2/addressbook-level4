//@@author A0163559U
package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Saves task data in the specified directory.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all tasks in specified directory with specified file name. "
            + "Parameters: SAVE_LOCATION\n"
            + "Example: " + COMMAND_WORD
            + " /Users/username/Documents/TaskManager/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Tasks saved in location: %1$s";
    public static final String MESSAGE_INVALID_SAVE_LOCATION = "This save location is invalid.";

    private final File toSave;
    /**
     * Creates an SaveCommand using raw values.
     *
     * @throws IllegalValueException if the save location is invalid
     * @throws IOException
     */
    public SaveCommand(String fileAsString) throws IllegalValueException {
        if (fileAsString.equals("") || fileAsString == null) {
            throw new IllegalValueException(MESSAGE_INVALID_SAVE_LOCATION);
        }
        this.toSave = new File(fileAsString);
        try {
            createSaveFile();
        } catch (IOException ioe) {
            throw new IllegalValueException(MESSAGE_INVALID_SAVE_LOCATION);
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
        return null;
    }

}
