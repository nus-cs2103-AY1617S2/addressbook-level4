package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.IncorrectCommand;

//@@author A0146809W

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
            new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START, PREFIX_END, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);

        boolean doesStartDateExist = argsTokenizer.getValue(PREFIX_START).isPresent();
        boolean doesEndDateExist = argsTokenizer.getValue(PREFIX_END).isPresent();

        try {
            if (doesStartDateExist && doesEndDateExist) {
                //for event
                return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).orElse("low"),
                    argsTokenizer.getValue(PREFIX_START).get(),
                    argsTokenizer.getValue(PREFIX_END).get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            } else if (doesEndDateExist) {
                //for task
                return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).orElse("low"),
                    argsTokenizer.getValue(PREFIX_END).get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            } else if (!doesStartDateExist && !doesEndDateExist) {
                //for floating task
                return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).orElse("low"),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
