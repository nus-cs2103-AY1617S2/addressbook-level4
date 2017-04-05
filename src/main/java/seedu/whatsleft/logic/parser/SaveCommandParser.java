package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.IncorrectCommand;
import seedu.whatsleft.logic.commands.SaveCommand;

//@@author A0121668A
/**
 * Parses input arguments and creates a new RedoCommand object
 */

public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an RedoCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String> filePath = ParserUtil.parseFilePath(args);
        if (!filePath.isPresent() || filePath.get().equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        return new SaveCommand(filePath.get());
    }
}
