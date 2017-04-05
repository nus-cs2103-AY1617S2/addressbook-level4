package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

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
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    //@@ author A0164032U
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_STARTDATE, PREFIX_DATE, PREFIX_EMAIL,
                PREFIX_GROUP);
        argsTokenizer.tokenize(args);
        try {
            if (!argsTokenizer.getEmpty(PREFIX_STARTDATE) && !argsTokenizer.getEmpty(PREFIX_DATE)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_DATE).get(),
                        argsTokenizer.getValue(PREFIX_STARTDATE).get(), argsTokenizer.getValue(PREFIX_EMAIL).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get());
            } else if (!argsTokenizer.getEmpty(PREFIX_DATE)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_DATE).get(),
                        argsTokenizer.getValue(PREFIX_EMAIL).get(), argsTokenizer.getValue(PREFIX_GROUP).get());
            } else {
                return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_EMAIL).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get());
            }

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
