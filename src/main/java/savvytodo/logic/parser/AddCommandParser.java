package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static savvytodo.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static savvytodo.logic.parser.CliSyntax.PREFIX_LOCATION;
import static savvytodo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_RECURRENCE;

import java.util.NoSuchElementException;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.AddCommand;
import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.IncorrectCommand;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Priority;

/**
 * @author A0140016B
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_DATE_TIME,
                PREFIX_RECURRENCE, PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);

        try {


            return new AddCommand(argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).orElse(Priority.Level.Medium.toString()),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(Description.DESCRIPTION_DEFAULT_VALUES),
                    argsTokenizer.getValue(PREFIX_LOCATION).orElse(Location.LOCATION_DEFAULT_VALUES),
                    ParserUtil.getDateTimeFromArgs(argsTokenizer.getValue(PREFIX_DATE_TIME)),
                    ParserUtil.getRecurrenceFromArgs(argsTokenizer.getValue(PREFIX_RECURRENCE)),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
