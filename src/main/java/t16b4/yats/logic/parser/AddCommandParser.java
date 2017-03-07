package t16b4.yats.logic.parser;

import static t16b4.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_LOCATION;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_START_TIME;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_END_TIME;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_PERIOD;
import static t16b4.yats.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import t16b4.yats.commons.exceptions.IllegalValueException;
import t16b4.yats.logic.commands.AddCommand;
import t16b4.yats.logic.commands.Command;
import t16b4.yats.logic.commands.IncorrectCommand;
import t16b4.yats.logic.parser.ArgumentTokenizer.Prefix;

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
                new ArgumentTokenizer(PREFIX_LOCATION, PREFIX_PERIOD, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_LOCATION).get(),
                    argsTokenizer.getValue(PREFIX_PERIOD).get(),
                    argsTokenizer.getValue(PREFIX_START_TIME).get(),
                    argsTokenizer.getValue(PREFIX_END_TIME).get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).get(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
