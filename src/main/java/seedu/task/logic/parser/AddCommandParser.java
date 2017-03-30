package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.task.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Remark;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends CommandParser {

    private String START_DATE, END_DATE, REMARK, LOCATION;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_REMARK,
                PREFIX_LOCATION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Map<Prefix, List<String>> tokenizedArguments = argsTokenizer.getTokenizedArguments();
        START_DATE = tokenizedArguments.containsKey(PREFIX_START_DATE) ? argsTokenizer.getValue(PREFIX_START_DATE).get()
                : Date.DEFAULT_DATE;
        END_DATE = tokenizedArguments.containsKey(PREFIX_END_DATE) ? argsTokenizer.getValue(PREFIX_END_DATE).get()
                : Date.DEFAULT_DATE;
        REMARK = tokenizedArguments.containsKey(PREFIX_REMARK) ? argsTokenizer.getValue(PREFIX_REMARK).get()
                : Remark.DEFAULT_REMARK;
        LOCATION = tokenizedArguments.containsKey(PREFIX_LOCATION) ? argsTokenizer.getValue(PREFIX_LOCATION).get()
                : Location.DEFAULT_LOCATION;
        try {
            return new AddCommand(argsTokenizer.getPreamble().get().replace("\\", ""), START_DATE, END_DATE, REMARK.replace("\\", ""), LOCATION.replace("\\", ""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
