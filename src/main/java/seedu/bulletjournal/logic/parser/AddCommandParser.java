package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_BEGINTIME;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.logic.commands.AddCommand;
import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    //@@author A0105748B
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_STATUS, PREFIX_BEGINTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DEADLINE).isPresent() ?
                            argsTokenizer.getValue(PREFIX_DEADLINE).get() : null,
                    argsTokenizer.getValue(PREFIX_STATUS).isPresent() ?
                            argsTokenizer.getValue(PREFIX_STATUS).get() : null,
                    argsTokenizer.getValue(PREFIX_BEGINTIME).isPresent() ?
                            argsTokenizer.getValue(PREFIX_BEGINTIME).get() : null,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
