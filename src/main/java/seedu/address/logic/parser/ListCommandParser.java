package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_COMPLETED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_INCOMPLETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_START;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;

//@@author A0162877N
/**
* Parses input arguments and creates a new ListCommand object
*/
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DEADLINE,
                    PREFIX_TIMEINTERVAL_START, PREFIX_TIMEINTERVAL_END, PREFIX_STATUS_COMPLETED,
                    PREFIX_STATUS_INCOMPLETE);
            argsTokenizer.tokenize(args);
            if (args.trim().contains(PREFIX_TIMEINTERVAL_START.getPrefix())
                    && args.trim().contains(PREFIX_TIMEINTERVAL_END.getPrefix())) {
                return new ListCommand(argsTokenizer.getValue(PREFIX_TIMEINTERVAL_START).get(),
                        argsTokenizer.getValue(PREFIX_TIMEINTERVAL_END).get());
            } else if (args.trim().contains(PREFIX_DEADLINE.getPrefix())) {
                return new ListCommand(argsTokenizer.getValue(PREFIX_DEADLINE).get());
            } else if (args.trim().contains(PREFIX_STATUS_COMPLETED.getPrefix())) {
                return new ListCommand(new Boolean(true));
            } else if (args.trim().contains(PREFIX_STATUS_INCOMPLETE.getPrefix())) {
                return new ListCommand(new Boolean(false));
            }
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand();
    }

}
