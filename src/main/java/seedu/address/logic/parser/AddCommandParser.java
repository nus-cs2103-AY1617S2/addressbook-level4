package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.exceptions.CommandException;

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
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_LABEL);
        argsTokenizer.tokenize(args);
        try {
            if (args.contains("by ")) {
                List<Date> dateList = new DateTimeParser().parse(
                        argsTokenizer.getValue(PREFIX_DEADLINE).get()).get(0).getDates();
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        dateList.get(0).toString(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL))
                );
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
