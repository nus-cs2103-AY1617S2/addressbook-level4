package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.FinishCommand;
import seedu.whatsleft.logic.commands.IncorrectCommand;
//@@author A0121668A
/**
 * Parses input arguments and creates a new FinishCommand object
 */
public class FinishCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FinishCommand
     * and returns an FinishCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndexAlone(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishCommand.MESSAGE_USAGE));
        }

        return new FinishCommand(index.get());
    }

}
