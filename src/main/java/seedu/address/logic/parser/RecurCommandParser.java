package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.RecurCommand;

public class RecurCommandParser {

    public static final String MESSAGE_RECUR_TASK_SUCCESS = "Recur task: %1$s";

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
}
