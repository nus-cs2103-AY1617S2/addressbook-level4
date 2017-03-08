package seedu.ezdo.logic.commands;

import java.io.File;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.exceptions.CommandException;

/**
 * Changes the save location of ezDo.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of ezDo. "
            + "Parameters: DIRECTORYPATH \n"
            + "Example: " + COMMAND_WORD
            + " C:\\Users\\Tom\\Desktop";

    public static final String MESSAGE_DIRECTORY_PATH_INVALID = "The directory path given is invalid.";
    public static final String MESSAGE_SAVE_TASK_SUCCESS = "New Save Location: %1$s";

    private final String directoryPath;

    /**
     * Creates a SaveCommand using raw values.
     *
     * @throws IllegalValueException if the directory path is invalid
     */
    public SaveCommand(String path) throws IllegalValueException {
        directoryPath = path;
        File directory = new File(path);
        if (directory.exists() == false && !directory.isDirectory()) { // THIS PART SEEMS TO NOT WORK VERY WELL. C:\\// works. TODO
            System.out.println("file.exists has error and not directory");
            throw new IllegalValueException(MESSAGE_DIRECTORY_PATH_INVALID);
        }
    }

    // TODO
    @Override
    public CommandResult execute() throws CommandException {
        assert directoryPath != null;
        return new CommandResult(String.format(MESSAGE_SAVE_TASK_SUCCESS, directoryPath));
    }

}
