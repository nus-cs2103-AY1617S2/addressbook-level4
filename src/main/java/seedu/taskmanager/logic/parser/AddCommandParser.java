package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX2;

import java.util.NoSuchElementException;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.DateTimeUtil;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

// @@author A0139520L
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String INVALID_TIME = "Invalid input for time\n" + "Time must be between 0000 and 2359";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_ON, PREFIX_BY, PREFIX_FROM, PREFIX_TO,
                PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        try {
            String taskName = argsTokenizer.getPreamble().get();
            String onPrefixInput = argsTokenizer.getValue(PREFIX_ON).orElse(EMPTY_FIELD);
            String byPrefixInput = argsTokenizer.getValue(PREFIX_BY).orElse(EMPTY_FIELD);
            String fromPrefixInput = argsTokenizer.getValue(PREFIX_FROM).orElse(EMPTY_FIELD);
            String toPrefixInput = argsTokenizer.getValue(PREFIX_TO).orElse(EMPTY_FIELD);
            String startDate = EMPTY_FIELD;
            String startTime = EMPTY_FIELD;
            String endDate = EMPTY_FIELD;
            String endTime = EMPTY_FIELD;

            addTaskInputValidation(onPrefixInput, byPrefixInput, fromPrefixInput, toPrefixInput);

            if (isFromToEvent(fromPrefixInput, toPrefixInput)) {
                if ((!isMatchedInput(fromPrefixInput.trim(), "\\d+"))) {
                    String[] splitedFromPrefixInput = fromPrefixInput.split("\\s+");
                    try {
                        startDate = processInputToDateForm(splitedFromPrefixInput);
                        startTime = splitedFromPrefixInput[1];

                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        startTime = "0000";

                    }
                    excessInputCheck(splitedFromPrefixInput);
                }

                validToPrefixInputCheck(toPrefixInput);

                String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                try {
                    endDate = processInputToDateForm(splitedToPrefixInput);
                    endTime = splitedToPrefixInput[1];

                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    endTime = "2359";
                }
                excessInputCheck(splitedToPrefixInput);

            }

            /*
             * To parse date input if required and throws exceptions if
             * incorrect arguments of date are included
             */

            else if (isOnEvent(onPrefixInput)) {
                String[] splited = onPrefixInput.split("\\s+");
                startDate = processInputToDateForm(splited);
                endDate = startDate;
                try {
                    startTime = splited[1];

                    if (isEmptyField(toPrefixInput)) {
                        endTime = DateTimeUtil.includeOneHourBuffer(startTime);
                        if (Integer.parseInt(endTime) < 100) {
                            endDate = DateTimeUtil.getFutureDate(1, "days", endDate);
                        }

                    } else {
                        String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                        endTime = processToPrefixInput(endTime, splitedToPrefixInput);
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    startTime = "0000";

                    if (isEmptyField(toPrefixInput)) {
                        endTime = "2359";
                    } else {
                        String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                        endTime = processToPrefixInput(endTime, splitedToPrefixInput);
                    }
                }
                excessInputCheck(splited);

            }

            /*
             * To parse Input before byPrefix and incorrect arguments of
             * deadline are included
             */

            else if (!isEmptyField(byPrefixInput)) {
                String[] splited = byPrefixInput.split("\\s+");
                endDate = splited[0];
                endDate = processInputToDateForm(splited);
                if (endDate.trim().matches("STARTDATE_VALIDATION_REGEX2")) {
                    endDate = DateTimeUtil.getNewDate(endDate);
                }
                try {
                    endTime = splited[1];

                    if (!DateTimeUtil.isValidTime(endTime)) {
                        throw new IllegalValueException(INVALID_TIME);
                    }

                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    endTime = "2359";
                } catch (NumberFormatException nfe) {
                    return new IncorrectCommand("Invalid input after prefix BY\n"
                            + "Example of Allowed Format: ADD project meeting BY thursday 1400 \n"
                            + "Type HELP for user guide with detailed explanations of all commands");
                }
                excessInputCheck(splited);
            }

            if (isValidEvent(startDate, startTime, endDate, endTime)
                    && !DateTimeUtil.isValidEventTimePeriod(startDate, startTime, endDate, endTime)) {
                throw new IllegalValueException("Invalid input of time, start time has to be earlier than end time");
            }

            return new AddCommand(taskName, startDate, startTime, endDate, endTime,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)));

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return new IncorrectCommand(
                    "Invalid command input!\n" + "Type HELP for user guide with detailed explanations of all commands");
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private void excessInputCheck(String[] splited) {
        try {
            if (!(splited[2].isEmpty())) {
                throw new NoSuchElementException("");
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
        }
    }

    private String processToPrefixInput(String endTime, String[] splitedToPrefixInput) {
        try {
            if (!(splitedToPrefixInput[1].isEmpty())) {
                throw new NoSuchElementException("");
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            endTime = splitedToPrefixInput[0];
        }
        return endTime;
    }

    private boolean isOnEvent(String onPrefixInput) {
        return !isEmptyField(onPrefixInput);
    }

    /**
     * Process input after To Prefix into the appropriate End Date form
     */
    private String processInputToDateForm(String[] splitedToPrefixInput) throws IllegalValueException {
        String endDate;
        if (isMatchedInput(splitedToPrefixInput[0].trim(), STARTDATE_VALIDATION_REGEX2)) {
            endDate = DateTimeUtil.getNewDate(splitedToPrefixInput[0]);
        } else {
            endDate = splitedToPrefixInput[0];

        }
        return endDate;
    }

    /**
     * Check that Input after TO prefix is not in only time format
     */
    private void validToPrefixInputCheck(String toPrefixInput) {
        if (isMatchedInput(toPrefixInput.trim(), "\\d+")) {
            throw new NoSuchElementException("");
        }
    }

    private boolean isMatchedInput(String input, String regex) {
        return input.matches(regex);
    }

    private boolean isFromToEvent(String fromPrefixInput, String toPrefixInput) {
        return (!fromPrefixInput.equals("EMPTY_FIELD")) && (!toPrefixInput.equals("EMPTY_FIELD"));
    }

    private boolean isValidEvent(String startDate, String startTime, String endDate, String endTime) {
        return !isEmptyField(startTime) && !isEmptyField(endTime) && !isEmptyField(startDate) && !isEmptyField(endDate);
    }

    /**
     * Validation check of given {@code String} arguments passed in by user
     * Invalid combinations of arguments added by the user will cause
     * NoSuchElementException to be thrown
     */

    private void addTaskInputValidation(String date, String deadline, String startTime, String endTime) {
        boolean isValidCombination = true;
        if (isNotFloatingTask(date, deadline, startTime, endTime)) {
            if (isInvalidOnPrefixCombination(date, deadline, startTime)) {
                isValidCombination = false;
            }
            if (isInvalidByPrefixCombination(date, deadline, startTime, endTime)) {
                isValidCombination = false;
            }
            if (isInvalidFromPrefixCombination(date, deadline, startTime, endTime)) {
                isValidCombination = false;
            }
            if (isInvalidToPrefixCombination(date, startTime, endTime)) {
                isValidCombination = false;
            }
            if (!isValidCombination) {
                throw new NoSuchElementException("");
            }
        }
    }

    private boolean isInvalidToPrefixCombination(String date, String startTime, String endTime) {
        return !isEmptyField(endTime) && (isEmptyField(date) && isEmptyField(startTime));
    }

    private boolean isInvalidFromPrefixCombination(String date, String deadline, String startTime, String endTime) {
        return (!isEmptyField(startTime) && (isEmptyField(date) && isEmptyField(deadline) && isEmptyField(endTime)))
                || isInvalidOnPrefixCombination(startTime, date, deadline);
    }

    private boolean isInvalidByPrefixCombination(String date, String deadline, String startTime, String endTime) {
        return !isEmptyField(deadline) && (!isEmptyField(date) || !isEmptyField(startTime) || !isEmptyField(endTime));
    }

    private boolean isInvalidOnPrefixCombination(String date, String deadline, String startTime) {
        return !isEmptyField(date) && (!isEmptyField(deadline) || !isEmptyField(startTime));
    }

    private boolean isNotFloatingTask(String date, String deadline, String startTime, String endTime) {
        return isEmptyField(date) || isEmptyField(deadline) || isEmptyField(startTime) || isEmptyField(endTime);
    }

    private boolean isEmptyField(String date) {
        return date.equals(EMPTY_FIELD);
    }

}
