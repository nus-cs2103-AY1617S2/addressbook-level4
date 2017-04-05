package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.IncorrectCommand;
import seedu.whatsleft.logic.commands.RecurCommand;

public class RecurCommandParser {

    //@@author A0110491U
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndexForRec(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurCommand.MESSAGE_USAGE));
        }

        Optional<String> freq = ParserUtil.parseFreqForRec(args);
        if (!freq.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurCommand.MESSAGE_USAGE));
        }

        Optional<Integer> occur = ParserUtil.parseOccurForRec(args);
        if (!occur.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurCommand.MESSAGE_USAGE));
        }

        return new RecurCommand(index.get(), freq.get(), occur.get());
    }
    //@@author
}
