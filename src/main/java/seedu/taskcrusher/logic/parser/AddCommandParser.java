package seedu.taskcrusher.logic.parser;

import static seedu.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.taskcrusher.commons.exceptions.IllegalValueException;
import seedu.taskcrusher.logic.commands.AddCommand;
import seedu.taskcrusher.logic.commands.Command;
import seedu.taskcrusher.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_DEADLINE_TIME, PREFIX_TAG, PREFIX_PRIORITY, PREFIX_DESCRIPTION);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
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
