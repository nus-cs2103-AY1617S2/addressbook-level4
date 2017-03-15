package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_START;

import java.util.NoSuchElementException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public static final int VALID_DATEARR_SIZE = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE,
                        PREFIX_TIMEINTERVAL_START, PREFIX_TIMEINTERVAL_END,
                        PREFIX_LABEL);
        argsTokenizer.tokenize(args);
        try {
            if (args.contains(PREFIX_DEADLINE.getPrefix())) {
                String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).get();
                if (deadline.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        deadline.trim(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL))
                );
            } else if (args.contains(PREFIX_TIMEINTERVAL_START.getPrefix())
                    && args.contains(PREFIX_TIMEINTERVAL_END.getPrefix())) {

                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_TIMEINTERVAL_START).get(),
                        argsTokenizer.getValue(PREFIX_TIMEINTERVAL_END).get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL))
                );
            } else {
                return new AddCommand(argsTokenizer.getPreamble().get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)));
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
