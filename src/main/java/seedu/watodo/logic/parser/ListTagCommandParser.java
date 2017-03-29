package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.ListTagCommand;

//@@author A0139872R
/**
 * Parses input arguments and creates a new ListTagCommand object
 */
public class ListTagCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListTagCommand
     * and returns an ListTagCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new ListTagCommand(
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTagCommand.MESSAGE_USAGE));
        }
    }

}
