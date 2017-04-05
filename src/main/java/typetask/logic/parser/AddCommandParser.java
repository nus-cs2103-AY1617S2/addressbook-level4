package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE;
import static typetask.commons.core.Messages.MESSAGE_INVALID_START_AND_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.AddCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;

//@@author A0139926R
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    private final int floatingTask = 0;
    private final int deadlineTaskWithTime = 1;
    private final int deadlineTaskWithDate = 2;
    private final int eventTask = 3;
    private final int invalidEvent = 4;
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_PRIORITY);
        argsTokenizer.tokenize(args);
        try {
            int taskType = checkTaskType(argsTokenizer);
            return getCorrectAddCommand(argsTokenizer, taskType);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    private Command getCorrectAddCommand(ArgumentTokenizer argsTokenizer, int taskType) throws IllegalValueException {
        if (taskType == eventTask) {
            List<Date> startDate = DateParser.getDate(argsTokenizer.getValue(PREFIX_START_DATE).get());
            List<Date> endDate = DateParser.getDate(argsTokenizer.getValue(PREFIX_END_DATE).get());
            String priority = new String("Low");
            if (!DateParser.checkValidDateFormat(startDate)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE));
            }
            if (!DateParser.checkValidDateFormat(endDate)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE));
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent())  {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
            }
            if (DateParser.checkValidSchedule(startDate, endDate)) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        DateParser.getDateString(startDate),
                        DateParser.getDateString(endDate), priority
                        );
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_START_AND_END_DATE));
            }
        } else if (taskType == deadlineTaskWithDate) {
            List<Date> deadline = DateParser.getDate(argsTokenizer.getValue(PREFIX_DATE).get());
            String priority = new String("Low");
            if (!DateParser.checkValidDateFormat(deadline)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_DATE));
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent())  {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent())  {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
            }
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    DateParser.getDateString(deadline), priority
                    );
        } else if (taskType == deadlineTaskWithTime) {
            List<Date> deadline = DateParser.getDate(argsTokenizer.getValue(PREFIX_TIME).get());
            String priority = new String("Low");
            if (!DateParser.checkValidDateFormat(deadline)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE_FORMAT_FOR_DATE));
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent())  {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
            }
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    DateParser.getDateString(deadline), priority
                    );
        } else if (taskType == invalidEvent) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            String priority = new String("Low");
            if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent())  {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
            }
            return new AddCommand(
                    argsTokenizer.getPreamble().get(), priority
                    );
        }
    }
    //@@author A0139926R
    public int checkTaskType(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return eventTask;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
            return deadlineTaskWithDate;
        } else if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTime;
        } else if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() ||
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return invalidEvent;
        } else {
            return floatingTask;
        }
    }

}
