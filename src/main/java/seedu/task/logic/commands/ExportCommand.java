package seedu.task.logic.commands;
import java.io.IOException;

import net.fortuna.ical4j.validate.ValidationException;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Import a task list ics in Burdens.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " path/to/file.ics";

    public static final String MESSAGE_SUCCESS = "Exported successfully to %1$s";
    public static final String MESSAGE_INVALID_FILE_PATH = "The file path provided is not valid!";

    private final String filePath;

    /**
     * Creates an Export Command.
     */
    public ExportCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
      //@@author A0144813J
        try {
            model.saveTasksToIcsFile(filePath);
        } catch (IOException | ValidationException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
      //@@author
    }
}
