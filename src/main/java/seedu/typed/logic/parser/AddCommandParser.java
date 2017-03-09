package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
        	if (argsTokenizer.getValue(PREFIX_DATE).equals(Optional.empty())) {
        		return new AddCommand(argsTokenizer.getPreamble().get(), null,
        				ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        	} else {
        		return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_DATE).get(),
        				ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        	}
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
