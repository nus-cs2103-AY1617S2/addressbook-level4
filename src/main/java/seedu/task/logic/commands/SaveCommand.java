//@@author A0163559U
package seedu.task.logic.commands;

import java.io.File;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Saves all tasks in the specified directory.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all tasks in specified directory. "
            + "Parameters: SAVE_DIRECTORY\n"
            + "Example: " + COMMAND_WORD
            + " /Users/username/Documents/TaskManager/taskmanger.xml";

    public static final String MESSAGE_SUCCESS = "Tasks saved in directory: %1$s";
    //    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final File toSave;
    /**
     * Creates an SaveCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public SaveCommand(String fileAsString) throws IllegalValueException {
        this.toSave = new File(fileAsString);
        if (!isValidSaveLocation(toSave)) {
            throw new IllegalValueException("Save location invalid.");
        }
    }

    private boolean isValidSaveLocation(File file) {
        return false;
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
        return null;
    }

}
