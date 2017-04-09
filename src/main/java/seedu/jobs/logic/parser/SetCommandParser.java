package seedu.jobs.logic.parser;

import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jobs.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.NoSuchElementException;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.logic.commands.AddCommand;
import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.IncorrectCommand;
import seedu.jobs.logic.commands.SetCommand;

public class SetCommandParser extends Parser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_EMAIL, PREFIX_PASSWORD);
        argsTokenizer.tokenize(args);
        try {
            return new SetCommand(
                    argsTokenizer.getValue(PREFIX_EMAIL),
                    argsTokenizer.getValue(PREFIX_PASSWORD)
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
