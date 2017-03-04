package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.parser.CliSyntax.*;
import java.util.NoSuchElementException;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    ////argsTokenizer.getValue(PREFIX_PHONE).get() is an an example of how to use the tokenizer
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_FROM, PREFIX_TO, PREFIX_REMIND, PREFIX_EVERY, PREFIX_AS, PREFIX_BY,  PREFIX_UNDER);
        argsTokenizer.tokenize(args);
        try {
            System.out.println("REaching = " + argsTokenizer.getPreamble().get());
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_FROM).get(),
                    argsTokenizer.getValue(PREFIX_TO).get(),
                    argsTokenizer.getValue(PREFIX_REMIND).get(),
                    argsTokenizer.getValue(PREFIX_EVERY).get(),
                    argsTokenizer.getValue(PREFIX_AS).get(),
                    argsTokenizer.getValue(PREFIX_BY).get(),
                    argsTokenizer.getValue(PREFIX_UNDER).get()
                    );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
