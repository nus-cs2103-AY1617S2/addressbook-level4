package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_COMPLETED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_INCOMPLETE;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.MarkCommand;

public class MarkCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the Command
     * and returns a MarkCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_STATUS_COMPLETED, PREFIX_STATUS_INCOMPLETE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        Boolean isCompleted;
        if (args.trim().contains(PREFIX_STATUS_COMPLETED.getPrefix())) {
            isCompleted = true;
        } else if (args.trim().contains(PREFIX_STATUS_INCOMPLETE.getPrefix())) {
            isCompleted = false;
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(index.get(), isCompleted);
    }
}
