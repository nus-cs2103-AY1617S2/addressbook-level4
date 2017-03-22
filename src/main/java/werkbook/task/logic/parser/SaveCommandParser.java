//@@author A0162266E
package werkbook.task.logic.parser;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.IncorrectCommand;
import werkbook.task.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Path> path;
        try {
            path = ParserUtil.parsePath(args);
        } catch (InvalidPathException IPE) {
            return new IncorrectCommand(SaveCommand.MESSAGE_INVALID_PATH);
        }
        if (!path.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        try {
            return new SaveCommand(path.get());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
