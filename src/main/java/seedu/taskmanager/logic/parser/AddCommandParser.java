package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.taskmanager.model.task.Date.DATE_VALIDATION_REGEX2;

import java.util.NoSuchElementException;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String INVALID_TIME = "Invalid input for time\nTime must be between 0000 and 2359";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_DEADLINE, PREFIX_STARTTIME,
                PREFIX_ENDTIME/* , PREFIX_CATEGORY */);
        argsTokenizer.tokenize(args);
        try {
            String taskName = argsTokenizer.getPreamble().get();
            String date = argsTokenizer.getValue(PREFIX_DATE).orElse(EMPTY_FIELD);
            String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).orElse(EMPTY_FIELD);
            String startTime = argsTokenizer.getValue(PREFIX_STARTTIME).orElse(EMPTY_FIELD);
            String endTime = argsTokenizer.getValue(PREFIX_ENDTIME).orElse(EMPTY_FIELD);

            /*
             * Checks to ensure correct combinations of arguments are added by
             * user when adding tasks to the task manager
             */

            if (date.equals(EMPTY_FIELD) || deadline.equals(EMPTY_FIELD) || startTime.equals(EMPTY_FIELD)
                    || endTime.equals(EMPTY_FIELD)) {
                if (!(date.equals(EMPTY_FIELD))
                        && (!(deadline.equals(EMPTY_FIELD)) || !(startTime.equals(EMPTY_FIELD)))) {
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

            /*
             * To parse date input if required and throws exceptions if
             * incorrect arguments of date are included
             */

            if (!(date.equals(EMPTY_FIELD))) {
                String[] splited = date.split("\\s+");
                date = splited[0];
                try {
                    startTime = splited[1];
                    if (Integer.parseInt(startTime) >= 2400) {
                        throw new IllegalValueException(INVALID_TIME);
                    }
                    if (endTime.equals(EMPTY_FIELD)) {
                        endTime = Integer.toString(100 + Integer.parseInt(splited[1]));
                    } else {
                        String[] splitedEndTime = endTime.split("\\s+");
                        try {
                            if (!(splitedEndTime[1].isEmpty())) {
                                throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                        + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                        + "Type HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            endTime = splitedEndTime[0];
                            if (Integer.parseInt(endTime) >= 2400) {
                                throw new IllegalValueException(INVALID_TIME);
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    startTime = "0000";
                    if (endTime.equals(EMPTY_FIELD)) {
                        endTime = "2359";
                    } else {
                        String[] splitedEndTime = endTime.split("\\s+");
                        try {
                            if (!(splitedEndTime[1].isEmpty())) {
                                throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                        + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                        + "Type HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            endTime = splitedEndTime[0];
                            if (Integer.parseInt(endTime) >= 2400) {
                                throw new IllegalValueException(INVALID_TIME);
                            }
                        }
                    }
                }
                try {
                    if (!(splited[2].isEmpty())) {
                        throw new NoSuchElementException("");
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                }
                if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                    throw new IllegalValueException(
                            "Invalid input of time, start time has to be earlier than end time");
                }
            }

            /*
             * To parse deadline input if required and throws exceptions if
             * incorrect arguments of deadline are included
             */

            if (!(deadline.equals(EMPTY_FIELD))) {
                String[] splited = deadline.split("\\s+");
                date = splited[0];
                try {
                    endTime = splited[1];
                    if (Integer.parseInt(endTime) >= 2400) {
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

            if (date.matches(DATE_VALIDATION_REGEX2)) {
                date = CurrentDate.getNewDate(date);
            }

            if (startTime.matches("[a-zA-Z]+")) {
                StringBuilder stringBuilderStartTime = new StringBuilder();

                stringBuilderStartTime.append(startTime);
                stringBuilderStartTime.append(" ");
                stringBuilderStartTime.append("0000");

                startTime = stringBuilderStartTime.toString();
            }

            if (endTime.matches("[a-zA-Z]+")) {
                StringBuilder stringBuilderEndTime = new StringBuilder();

                stringBuilderEndTime.append(endTime);
                stringBuilderEndTime.append(" ");
                stringBuilderEndTime.append("2359");

                endTime = stringBuilderEndTime.toString();
            }

            if ((!startTime.equals("EMPTY_FIELD")) && (!startTime.matches("\\d+"))) {
                String[] splitedStartTime = startTime.split("\\s+");
                try {
                    if (splitedStartTime[0].matches(DATE_VALIDATION_REGEX2)) {
                        splitedStartTime[0] = CurrentDate.getNewDate(splitedStartTime[0]);
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(splitedStartTime[0]);
                    stringBuilder.append(" ");
                    stringBuilder.append(splitedStartTime[1]);

                    startTime = stringBuilder.toString();
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    if (splitedStartTime[0].matches(DATE_VALIDATION_REGEX2)) {
                        startTime = CurrentDate.getNewDate(splitedStartTime[0]);
                    }
                }
            }

            if ((!endTime.equals("EMPTY_FIELD")) && (!endTime.matches("\\d+"))) {
                String[] splitedEndTime = endTime.split("\\s+");
                try {
                    if (splitedEndTime[0].matches(DATE_VALIDATION_REGEX2)) {
                        splitedEndTime[0] = CurrentDate.getNewDate(splitedEndTime[0]);
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(splitedEndTime[0]);
                    stringBuilder.append(" ");
                    stringBuilder.append(splitedEndTime[1]);

                    endTime = stringBuilder.toString();
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    if (splitedEndTime[0].matches(DATE_VALIDATION_REGEX2)) {
                        endTime = CurrentDate.getNewDate(splitedEndTime[0]);
                    }
                }
            }

            return new AddCommand(taskName, date, startTime, endTime
            // ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY)
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return new IncorrectCommand(
                    "Invalid command input!\nExample of Allowed Format: ADD e-mail John BY thursday 1400\n"
                            + "Type HELP for user guide with detailed explanations of all commands");
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand("Invalid input after prefix TO, only input of time is allowed\n"
                    + "Example of Allowed Format: ADD project meeting ON thursday 1400 TO 1800\n"
                    + "Type HELP for user guide with detailed explanations of all commands");
        }
    }

}
