package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.model.task.Date.DATE_VALIDATION_REGEX2;
import seedu.taskmanager.commons.util.CurrentDate;

import java.util.NoSuchElementException;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
     
	public static final String EMPTY_FIELD ="EMPTY_FIELD";
	
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_DEADLINE, PREFIX_STARTTIME, PREFIX_ENDTIME/*, PREFIX_CATEGORY*/);
        argsTokenizer.tokenize(args);
        try {
        	String taskname = argsTokenizer.getPreamble().get();
        	String date = argsTokenizer.getValue(PREFIX_DATE).orElse(EMPTY_FIELD);
        	String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).orElse(EMPTY_FIELD);
        	String starttime = argsTokenizer.getValue(PREFIX_STARTTIME).orElse(EMPTY_FIELD);
        	String endtime = argsTokenizer.getValue(PREFIX_ENDTIME).orElse(EMPTY_FIELD);
        	
        	if (date.matches(DATE_VALIDATION_REGEX2)) date = CurrentDate.getNewDate(date);
        	
            return new AddCommand(
                    taskname,
                    date,
                    deadline,
                    starttime,
                    endtime
//                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
