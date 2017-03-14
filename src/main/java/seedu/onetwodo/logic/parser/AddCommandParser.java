package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String startDate = argsTokenizer.getValue(PREFIX_START_DATE).orElse("");
            String endDate = argsTokenizer.getValue(PREFIX_END_DATE).orElse("");
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    startDate,
                    endDate,
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
}
