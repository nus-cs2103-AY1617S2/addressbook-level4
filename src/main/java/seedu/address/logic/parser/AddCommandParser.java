package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_CAN_ONLY_ADD_ONE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.DateTimeException;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String DEFAULT_START_TIME = "0001";
    private static final String DEFAULT_END_TIME = "2359";
    private static final String DEFAULT_BY_TIME = "2359";

    //@@author A0110491U
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_STARTDATE, PREFIX_ENDDATE,
                        PREFIX_BYDATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_BYTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String priority = argsTokenizer.getValue(PREFIX_PRIORITY).orElse(null);
            String location = argsTokenizer.getValue(PREFIX_LOCATION).orElse(null);
            String startdate = argsTokenizer.getValue(PREFIX_STARTDATE).orElse(null);
            String enddate = argsTokenizer.getValue(PREFIX_ENDDATE).orElse(null);
            String bydate = argsTokenizer.getValue(PREFIX_BYDATE).orElse(null);
            String starttime = argsTokenizer.getValue(PREFIX_STARTTIME).orElse(null);
            String endtime = argsTokenizer.getValue(PREFIX_ENDTIME).orElse(null);
            String bytime = argsTokenizer.getValue(PREFIX_BYTIME).orElse(null);

            //neither of the 2 compulsory attributes are given
            if (priority == null && startdate == null) {
                throw new NoSuchElementException();
            }

            //trying to add task/deadline as well as Event
            if (priority != null && startdate != null) {
                throw new IllegalValueException(MESSAGE_CAN_ONLY_ADD_ONE);
            }

            //Task/Deadline cannot have non-task/deadline attributes
            if (priority != null && (startdate != null || enddate != null ||
                    starttime != null || endtime != null)) {
                throw new IllegalValueException(MESSAGE_CAN_ONLY_ADD_ONE);
            }

            //Event cannot have non-event attributes
            if (startdate != null && (priority != null || bydate != null || bytime != null)) {
                throw new IllegalValueException(MESSAGE_CAN_ONLY_ADD_ONE);
            }

            //default enddate to same startdate
            if (startdate != null && enddate == null) {
                enddate = startdate;
            }

            //default block whole day
            if (starttime == null && endtime == null) {
                starttime = DEFAULT_START_TIME;
                endtime = DEFAULT_END_TIME;
            }

            //default block to end of day
            if (starttime != null && endtime == null) {
                endtime = DEFAULT_END_TIME;
            }

            //default block from start of day
            if (endtime != null && starttime == null) {
                starttime = DEFAULT_START_TIME;
            }

            //default task bytime if bydate is given
            if (bydate != null && bytime == null) {
                bytime = DEFAULT_BY_TIME;
            }

            //default bydate if bytime is given
            if (bytime != null && bydate == null) {
                bydate = StringUtil.getTodayDateInString();
            }

            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    priority,
                    starttime,
                    startdate,
                    endtime,
                    enddate,
                    bydate,
                    bytime,
                    location,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(dte.getLocalizedMessage());
        }
    }

}
