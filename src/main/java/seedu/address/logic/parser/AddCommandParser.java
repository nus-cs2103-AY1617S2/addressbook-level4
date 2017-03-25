package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;


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
                new ArgumentTokenizer(PREFIX_DEADLINE_DATETIME, PREFIX_START_DATETIME, PREFIX_END_DATETIME,
                                      PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String nameArgs = argsTokenizer.getPreamble().get();
            Optional<String> deadlineDateTimeArgs = argsTokenizer.getValue(PREFIX_DEADLINE_DATETIME);
            Optional<String> startDateTimeArgs = argsTokenizer.getValue(PREFIX_START_DATETIME);
            Optional<String> endDateTimeArgs = argsTokenizer.getValue(PREFIX_END_DATETIME);

            return new AddCommand(nameArgs, deadlineDateTimeArgs, startDateTimeArgs, endDateTimeArgs,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
