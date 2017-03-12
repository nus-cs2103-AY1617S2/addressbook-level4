package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static savvytodo.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DATE_END_TIME;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DATE_START_TIME;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static savvytodo.logic.parser.CliSyntax.PREFIX_LOCATION;
import static savvytodo.logic.parser.CliSyntax.PREFIX_NUM_OF_RECURRENCES;
import static savvytodo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_RECURRENCE_TYPE;

import java.util.NoSuchElementException;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.AddCommand;
import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_DATE_START_TIME,
                PREFIX_DATE_END_TIME, PREFIX_RECURRENCE_TYPE, PREFIX_NUM_OF_RECURRENCES, PREFIX_DESCRIPTION,
                PREFIX_LOCATION, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_PRIORITY).get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).get(), argsTokenizer.getValue(PREFIX_LOCATION).get(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
