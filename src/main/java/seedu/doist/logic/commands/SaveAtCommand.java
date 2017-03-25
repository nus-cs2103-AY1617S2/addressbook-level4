package seedu.doist.logic.commands;

import java.nio.file.Path;

//@@author A0140887W
/**
 * Changes the absolute storage path by changing config
 */
public class SaveAtCommand extends Command {

    public Path absolutePath;

    public static final String DEFAULT_COMMAND_WORD = "save_at";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ":\n" + "Changes the storage path of Doist." + "\n"
            + "You can use both absolute and relative file paths. Use // to seperate directories.\n\t"
            + "Parameters: path " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + " C:/Users";

    public static final String MESSAGE_SUCCESS = "New Storage Path is: %1$s";

    // TODO: can we undo config too?
    public SaveAtCommand(Path absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        // This would trigger an event that will change storage
        model.changeConfigAbsolutePath(absolutePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, absolutePath));
    }
}
