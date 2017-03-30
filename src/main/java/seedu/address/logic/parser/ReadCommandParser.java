package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ReadCommand;

//@@author A0121668A
/**
 * Parses input arguments and creates a new RedoCommand object
 */

public class ReadCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ReadCommand
     * and returns an RedoCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String> filePath = ParserUtil.parseFilePath(args);
        if (!filePath.isPresent() || filePath.get().equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCommand.MESSAGE_USAGE));
        }
        return new ReadCommand(filePath.get());
    }
}
