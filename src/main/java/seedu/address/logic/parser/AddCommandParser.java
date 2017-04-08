package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

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
    //@@author A0164032U
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_GROUP);
        argsTokenizer.tokenize(args);
        try {
            if (!argsTokenizer.getEmpty(PREFIX_START_DATE) && !argsTokenizer.getEmpty(PREFIX_END_DATE)) {
                
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_START_DATE).get(),
                        argsTokenizer.getValue(PREFIX_END_DATE).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );
                
            } else if (!argsTokenizer.getEmpty(PREFIX_END_DATE)) {
                
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_END_DATE).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );
                
            } else if (!argsTokenizer.getEmpty(PREFIX_START_DATE)) {
                
                throw new NoSuchElementException("");
                
            } else {
                
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
