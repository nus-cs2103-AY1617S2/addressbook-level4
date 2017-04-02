//@@author A0141011J
package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.taskit.logic.commands.ChangePathCommand;
import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ChangePathCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<String> newFilePath = Optional.of(args);
        if (!newFilePath.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePathCommand.MESSAGE_USAGE));
        }

        return new ChangePathCommand(newFilePath.get());
    }
}
