package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.todolist.commons.exceptions.IllegalValueException;

import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.EditCommand;
import seedu.todolist.logic.commands.IncorrectCommand;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    //@@author A0163720M, A0165043M
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            Optional<String> startTime = formatAndCheckValidTime(argsTokenizer.getValue(PREFIX_START_TIME));
            Optional<String> endTime = formatAndCheckValidTime(argsTokenizer.getValue(PREFIX_END_TIME));
            if (startTime.isPresent() && endTime.isPresent()) {
                return new AddCommand (
                        argsTokenizer.getPreamble().get(),
                        startTime,
                        endTime,
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            } else if (!startTime.isPresent() && endTime.isPresent()) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        endTime,
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            } else if (startTime.isPresent() && !endTime.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            } else {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                );
            }

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException pe) {
            return new IncorrectCommand(pe.getMessage());
        }
    }
    //@@author
    //@@author A0165043M
    /**
     * This function will check and format the time into "h:mma dd/MM/yyyy" time string
     * if the input doesn't contain time information,
     * this function will append "12:00AM" to the return string
     * @param time
     * @return a valid time string
     * @throws ParseException
     */
    private Optional<String> formatAndCheckValidTime (Optional<String> time) throws ParseException {
        if (!time.equals(Optional.empty()) && !time.get().equals("")) {
            try {
                String[] dateAndTime = time.get().split(" ");
                if (dateAndTime.length == 1) { //date only
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.parse(time.get());
                    Optional<String> midnightPlusDate = Optional.of("12:00AM " + time.get());
                    return midnightPlusDate;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
                    dateFormat.parse(time.get());
                    return time;
                }
            } catch (ParseException e) {
                throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
            }
        } else {
            return time;
        }
    }
    //@@author
}
