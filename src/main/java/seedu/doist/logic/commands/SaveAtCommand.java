package seedu.doist.logic.commands;

import java.io.File;

import seedu.doist.logic.commands.exceptions.CommandException;

//@@author A0140887W
/**
 * Changes the absolute storage path by changing config
 */
public class SaveAtCommand extends Command {

    public File file;

    public static final String DEFAULT_COMMAND_WORD = "save_at";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ":\n" + "Changes the storage folder of Doist." + "\n"
            + "You can use both absolute and relative file paths to a folder. Use // to seperate directories.\n\t"
            + "Parameters: path " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + " C:/Users";

    public static final String MESSAGE_SUCCESS = "New Storage Path is: %1$s";
    public static final String MESSAGE_FILE_EXISTS = "A file already exists with the same name."
            + " Do give a path to a folder!";
    public static final String MESSAGE_INVALID_PATH = "Invalid folder path entered! \n%1$s";

    public SaveAtCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert configModel != null;
        if (file.exists() && !file.isDirectory()) {
            throw new CommandException(MESSAGE_FILE_EXISTS);
        }
        // This would trigger an event that will change storage
        configModel.changeConfigAbsolutePath(file.toPath().toAbsolutePath());
        return new CommandResult(String.format(MESSAGE_SUCCESS, file));
    }
}
