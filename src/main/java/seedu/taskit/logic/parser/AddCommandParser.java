package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_TO;

import java.util.NoSuchElementException;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.AddCommand;
import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

	//@@author A0163996J
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {

        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_BY, PREFIX_FROM, PREFIX_TO, PREFIX_PRIORITY, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String start = null;
        String end = null;
        String priority = null;
        //TODOD better way to make these fields optional
        try {
            start = argsTokenizer.getValue(PREFIX_FROM).get();
            end = argsTokenizer.getValue(PREFIX_TO).get();
        } catch (NoSuchElementException nse) {
            try {
                end = argsTokenizer.getValue(PREFIX_BY).get();
            } catch (NoSuchElementException nsee1) {}
        }
        try {
            priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
        } catch (NoSuchElementException nsee2) {}
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    start,
                    end,
                    priority,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
