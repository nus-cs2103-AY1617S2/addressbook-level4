package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_FROM;
//import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX1;
import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX2;

import java.util.NoSuchElementException;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;
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

            /*
             * Checks to ensure correct combinations of arguments are added by
             * user when adding tasks to the task manager
             */

            addTaskInputValidation(onPrefixInput, byPrefixInput, fromPrefixInput, toPrefixInput);

            if ((!fromPrefixInput.equals("EMPTY_FIELD")) && (!toPrefixInput.equals("EMPTY_FIELD"))) {
                if ((!fromPrefixInput.equals("EMPTY_FIELD")) && (!(fromPrefixInput.matches("\\d+")))) {
                    String[] splitedFromPrefixInput = fromPrefixInput.split("\\s+");
                    try {
                        if (splitedFromPrefixInput[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            startDate = CurrentDate.getNewDate(splitedFromPrefixInput[0]);
                            // startDate = convertDateForm(startDate);
                        } else {
                            startDate = splitedFromPrefixInput[0];
                        }

                        startTime = splitedFromPrefixInput[1];

                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        startTime = "0000";
                        /*
                         * if (splitedStartTime[0].matches(
                         * STARTDATE_VALIDATION_REGEX2) ) { startTime =
                         * CurrentDate.getNewDate(splitedStartTime[0]); }
                         */
                    }
                }

                if ((!toPrefixInput.equals("EMPTY_FIELD")) && (!toPrefixInput.matches("\\d+"))) {
                    String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                    try {
                        if (splitedToPrefixInput[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            endDate = CurrentDate.getNewDate(splitedToPrefixInput[0]);
                            // endDate = convertDateForm(endDate);
                        } else {
                            endDate = splitedToPrefixInput[0];
                        }
                        endTime = splitedToPrefixInput[1];

                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        endTime = "2359";
                    }
                }
            }

            /*
             * To parse date input if required and throws exceptions if
             * incorrect arguments of date are included
             */

            else if (!(onPrefixInput.equals(EMPTY_FIELD))) {
                String[] splited = onPrefixInput.split("\\s+");
                startDate = splited[0];
                startDate = convertDateForm(startDate);
                endDate = startDate;

                try {
                    startTime = splited[1];

                    if (!isValidTime(startTime)) {
                        throw new IllegalValueException(INVALID_TIME);
                    }

                    if (toPrefixInput.equals(EMPTY_FIELD)) {

                        endTime = Integer.toString(100 + Integer.parseInt(startTime));

                        if (!isValidTime(endTime)) {
                            // endDate = nextDay(due to +1hr > 2400hrs)
                            endTime = Integer.toString(Integer.parseInt(endTime) - 2400);

                            endTime = fourDigitTimeFormat(endTime);

                        }

                    } else {
                        String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                        try {
                            if (!(splitedToPrefixInput[1].isEmpty())) {
                                throw new IllegalValueException("End time for task "
                                        + "should only contain a day (e.g. thursday) "
                                        + "or a date with the format: DD/MM/YY (e.g. 03/03/17))\n"
                                        + "May also include time (e.g. 1400) behind date in some instances\n"
                                        + "Enter HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            endTime = splitedToPrefixInput[0];
                            if (!(endTime.matches("\\d{4}"))) {
                                throw new NoSuchElementException();
                            }
                            if (!isValidTime(endTime)) {
                                throw new IllegalValueException(INVALID_TIME);
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    startTime = "0000";
                    if (toPrefixInput.equals(EMPTY_FIELD)) {
                        endTime = "2359";
                    } else {
                        String[] splitedToPrefixInput = toPrefixInput.split("\\s+");
                        try {
                            if (!(splitedToPrefixInput[1].isEmpty())) {
                                throw new IllegalValueException(
                                        "When using prefix ON and TO, input after prefix TO should only include time (e.g. 1400)\n"
                                                + "Enter HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            endTime = splitedToPrefixInput[0];
                            if (!isValidTime(endTime)) {
                                throw new IllegalValueException(INVALID_TIME);
                            }
                            compareStartEndTime(startTime, endTime);
                        }
                    }
                }
                try {
                    if (!(splited[2].isEmpty())) {
                        throw new NoSuchElementException("");
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                }

            }

            /*
             * To parse deadline input if required and throws exceptions if
             * incorrect arguments of deadline are included
             */

            else if (!(byPrefixInput.equals(EMPTY_FIELD))) {
                String[] splited = byPrefixInput.split("\\s+");
                endDate = splited[0];
                endDate = convertDateForm(endDate);
                try {
                    endTime = splited[1];

                    if (!isValidTime(endTime)) {
                        throw new IllegalValueException(INVALID_TIME);
                    }

                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    endTime = "2359";
                } catch (NumberFormatException nfe) {
                    return new IncorrectCommand("Invalid input after prefix BY\n"
                            + "Example of Allowed Format: ADD project meeting BY thursday 1400 \n"
                            + "Type HELP for user guide with detailed explanations of all commands");
                }
                try {
                    if (!(splited[2].isEmpty())) {
                        throw new NoSuchElementException("");
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                }
            }

            return new AddCommand(taskName, startDate, startTime, endDate, endTime,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)));

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return new IncorrectCommand(
                    "Invalid command input!\nExample of Allowed Format: ADD e-mail John BY thursday 1400\n"
                            + "Type HELP for user guide with detailed explanations of all commands");
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private String fourDigitTimeFormat(String endTime) {
        if (Integer.parseInt(endTime) >= 10) {
            StringBuilder stringBuilderTime = new StringBuilder();

            stringBuilderTime.append("00");
            stringBuilderTime.append(endTime);
            endTime = stringBuilderTime.toString();

        } else {
            StringBuilder stringBuilderTime = new StringBuilder();

            stringBuilderTime.append("000");
            stringBuilderTime.append(endTime);
            endTime = stringBuilderTime.toString();
        }
        return endTime;
    }

    private void compareStartEndTime(String startTime, String endTime) throws IllegalValueException {
        if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
            throw new IllegalValueException("Invalid input of time, start time has to be earlier than end time");
        }
    }

    private String convertDateForm(String date) throws IllegalValueException {
        if (date.matches(STARTDATE_VALIDATION_REGEX2)) {
            date = CurrentDate.getNewDate(date);
        }
        return date;
    }

    private boolean isValidTime(String value) {
        return (value.matches("\\d+") && (Integer.parseInt(value) < 2400) && (Integer.parseInt(value) >= 0)
                && (((Integer.parseInt(value)) % 100) < 60));
    }

    private void addTaskInputValidation(String date, String deadline, String startTime, String endTime) {
        if (date.equals(EMPTY_FIELD) || deadline.equals(EMPTY_FIELD) || startTime.equals(EMPTY_FIELD)
                || endTime.equals(EMPTY_FIELD)) {
            if (!(date.equals(EMPTY_FIELD)) && (!(deadline.equals(EMPTY_FIELD)) || !(startTime.equals(EMPTY_FIELD)))) {
                throw new NoSuchElementException("");
            }
            if (!(deadline.equals(EMPTY_FIELD)) && (!(date.equals(EMPTY_FIELD)) || !(startTime.equals(EMPTY_FIELD))
                    || !(endTime.equals(EMPTY_FIELD)))) {
                throw new NoSuchElementException("");
            }
            if ((!(startTime.equals(EMPTY_FIELD))
                    && (date.equals(EMPTY_FIELD) && deadline.equals(EMPTY_FIELD) && endTime.equals(EMPTY_FIELD)))
                    || (!(startTime.equals(EMPTY_FIELD))
                            && (!(date.equals(EMPTY_FIELD)) || !(deadline.equals(EMPTY_FIELD))))) {
                throw new NoSuchElementException("");
            }
            if (!(endTime.equals(EMPTY_FIELD)) && (date.equals(EMPTY_FIELD) && startTime.equals(EMPTY_FIELD))) {
                throw new NoSuchElementException("");
            }
        }
    }

}
