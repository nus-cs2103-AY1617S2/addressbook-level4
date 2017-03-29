package seedu.jobs.logic.parser;

import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_END;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_START;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.logic.commands.AddCommand;
import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START, PREFIX_END, PREFIX_RECUR, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble(),
                    argsTokenizer.getValue(PREFIX_START),
                    argsTokenizer.getValue(PREFIX_END),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)),
                    argsTokenizer.getValue(PREFIX_RECUR)
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
