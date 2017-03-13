package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String priority, startDate, endDate;

        try {
        	priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
        } catch (NoSuchElementException e) {
        	priority = null;
        }

        try {
        	startDate = argsTokenizer.getValue(PREFIX_START_DATE).get();
        } catch (NoSuchElementException e) {
        	startDate = null;
        }

        try {
        	endDate = argsTokenizer.getValue(PREFIX_END_DATE).get();
        } catch (NoSuchElementException e) {
        	endDate = null;
        }

        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    priority, startDate, endDate,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
