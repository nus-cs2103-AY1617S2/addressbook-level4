package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SetStorageCommand;

//@@author A0163673Y
/**
* Parses input arguments and creates a new SetStorageCommand object.
*/
public class SetStorageCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SetStorageCommand
     * and returns an SetStorageCommand object for execution.
     */
    public Command parse(String arg) {
        try {
            arg = arg.trim();
            if (arg.isEmpty()) {
                throw new NoSuchElementException();
            }
            return new SetStorageCommand(arg);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStorageCommand.MESSAGE_USAGE));
        }
    }
}
