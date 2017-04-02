package seedu.task.logic.commands;

import java.io.IOException;

import net.fortuna.ical4j.data.ParserException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Import a task list ics in Burdens.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " path/to/file.ics";

    public static final String MESSAGE_SUCCESS = "Successfully imported %1$s";
    public static final String MESSAGE_INVALID_FILE_PATH = "The file path provided is not valid!";
    public static final String MESSAGE_ILLEGAL_VALUE = "The data is not compatible with task manager!";

    private final String filePath;

    /**
     * Creates an Import Command.
     */
    public ImportCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
      //@@author A0144813J
        try {
            model.addTasksFromIcsFile(filePath);
        } catch (IOException | ParserException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH));
        } catch (IllegalValueException ive) {
            throw new CommandException(String.format(MESSAGE_ILLEGAL_VALUE));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
      //@@author
    }
}
