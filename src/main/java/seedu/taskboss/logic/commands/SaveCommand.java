package seedu.taskboss.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.taskboss.logic.commands.exceptions.CommandException;

//@@author A0138961W
/**
 * Saves the data at specific filepath. Creates filepath if it does not exist
 */

public class SaveCommand extends Command {

    public static final String SYMBOL_ASTERISK = "*";
    public static final String SYMBOL_LEFT = "<";
    public static final String SYMBOL_RIGHT = ">";
    public static final String SYMBOL_QUESTION = "?";
    public static final String SYMBOL_BAR = "|";
    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_WORD_SHORT = "sv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                + ": Saves the data at specific filepath.\n"
                + "Example: " + COMMAND_WORD
                + " C://Users/Desktop/TaskBoss\n"
                + "Example: " + COMMAND_WORD
                + " /User/Username/Desktop/TaskBoss";

    public static final String MESSAGE_SUCCESS = "The data has been saved!";

    public static final String MESSAGE_INVALID_FILEPATH = "The filepath is invalid.";

    private final String filepath;

    public SaveCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert storage != null;

        if (filepath.contains(SYMBOL_BAR) || filepath.contains (SYMBOL_LEFT) ||
                filepath.contains (SYMBOL_RIGHT) || filepath.contains (SYMBOL_ASTERISK) ||
                    filepath.contains (SYMBOL_QUESTION)) {
            throw new CommandException(MESSAGE_INVALID_FILEPATH);
        }

        File f = new File(filepath);
        if (!f.canWrite()) {
            throw new CommandException(MESSAGE_INVALID_FILEPATH);
        }

        try {
            Path path = Paths.get(filepath);
            storage.setFilePath(path.toAbsolutePath().toString());
            model.saveTaskboss();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_INVALID_FILEPATH);
        }
    }
}
