package seedu.geekeep.logic.parser;

import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_ENDING_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_STARTING_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.logic.commands.AddCommand;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_STARTING_DATETIME, PREFIX_ENDING_DATETIME, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String title = argsTokenizer.getPreamble().get();
            Optional<String> startDateTime = argsTokenizer.getValue(PREFIX_STARTING_DATETIME);
            Optional<String> endDateTime = argsTokenizer.getValue(PREFIX_ENDING_DATETIME);
            Optional<String> location = argsTokenizer.getValue(PREFIX_DESCRIPTION);
            return new AddCommand(
                    title,
                    startDateTime,
                    endDateTime,
                    location,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
