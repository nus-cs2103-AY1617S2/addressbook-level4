package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //@@author A0163720M, A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TAG, PREFIX_RECUR);
        argsTokenizer.tokenize(args);
        Optional<String> emptyStr = Optional.empty();
        if (argsTokenizer.getValue(PREFIX_RECUR) == emptyStr) { //normal add
            try {
                Optional<String> startTime = argsTokenizer.getValue(PREFIX_START_TIME);
                Optional<String> endTime = argsTokenizer.getValue(PREFIX_END_TIME);

                startTime = formatAndCheckValidTime(startTime);
                endTime = formatAndCheckValidTime(endTime);
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
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            EditCommand.MESSAGE_USAGE));
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
        } else { //recurring add
            try {
                Optional<String[]> recurValue = Optional.of(argsTokenizer.getValue(PREFIX_RECUR).get().split(" "));
                Date startDay = formatAndCheckValidDate(Optional.of(recurValue.get()[0]));
                Date endMonth = getMonth(recurValue.get()[1]);
                Date startTime = getTimeRecur(argsTokenizer.getValue(PREFIX_START_TIME));
                Date endTime = getTimeRecur(argsTokenizer.getValue(PREFIX_END_TIME));
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        startTime,
                        endTime,
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)),
                        startDay,
                        endMonth);
            } catch (ParseException pe) {
                return new IncorrectCommand(pe.getMessage());
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
    }
    //@@author
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
    private Date formatAndCheckValidDate (Optional<String> time) throws ParseException {
        if (!time.equals(Optional.empty()) && !time.get().equals("")) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.parse(time.get());
            } catch (ParseException e) {
                throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
            }
        } else {
            throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
        }
    }
    private Date getMonth (String month) throws ParseException {
        if (month.split("/").length > 2) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_RECURRING_DATE, 0);
        }
        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        return dateFormat.parse(month);
    }
    //@@author A0165043M-unused
	/**
     * RecurringAddCommand is not following the abstraction occurrence design pattern,
	 * so it's not used in final product
     */
    private Date getTimeRecur (Optional<String> time) throws ParseException {
        if (time.get().contains(" ")) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
        }
        DateFormat dateFormat = new SimpleDateFormat("h:mma");
        return dateFormat.parse(time.get());
    }
    //@@author
}
