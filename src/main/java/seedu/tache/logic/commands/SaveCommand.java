//@@author A0139961U
package seedu.tache.logic.commands;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.exceptions.CommandException;

/**
 * Adds a task to the task manager.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of the"
            + "data files based on the directory entered or selected.\n"
            + "Parameters: DIRECTORY \n"
            + "Example: " + COMMAND_WORD
            + " C:\\Users\\user\\Desktop";

    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";

    public final String newPath;

    /**
     * Creates a SaveCommand using the directory entered by the user.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public SaveCommand(String newDirectory) {
        this.newPath = newDirectory;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert storage != null;
        storage.setTaskManagerFilePath(newPath + "\\data\\taskmanager.xml");
        return new CommandResult(String.format(MESSAGE_SUCCESS, newPath));
    }
}

