package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX2;

//import java.util.Collection;
//import java.util.Collections;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;
//import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.UpdateCommand;
import seedu.taskmanager.logic.commands.UpdateCommand.UpdateTaskDescriptor;

//@@author A0142418L
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UpdateCommandParser {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String INVALID_TIME = "Invalid input for time\nTime must be between 0000 and 2359";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_DEADLINE, PREFIX_STARTTIME,
                PREFIX_ENDTIME/* , PREFIX_CATEGORY */);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
        try {
            Optional<String> taskName = preambleFields.get(1);
            Optional<String> date = argsTokenizer.getValue(PREFIX_DATE);
            Optional<String> deadline = argsTokenizer.getValue(PREFIX_DEADLINE);
            Optional<String> startTime = argsTokenizer.getValue(PREFIX_STARTTIME);
            Optional<String> endTime = argsTokenizer.getValue(PREFIX_ENDTIME);

            /*
             * Checks to ensure correct combinations of arguments are added by
             * user when adding tasks to the task manager
             */

            if (!date.isPresent() || !deadline.isPresent() || !startTime.isPresent() || !endTime.isPresent()) {
                if ((date.isPresent()) && ((deadline.isPresent()) || (startTime.isPresent()))) {
                    throw new NoSuchElementException("");
                }
                if ((deadline.isPresent())
                        && ((date.isPresent()) || (startTime.isPresent()) || (endTime.isPresent()))) {
                    throw new NoSuchElementException("");
                }
                if (((startTime.isPresent()) && (!date.isPresent() && !deadline.isPresent() && !endTime.isPresent()))
                        || ((startTime.isPresent()) && ((date.isPresent()) || (deadline.isPresent())))) {
                    throw new NoSuchElementException("");
                }
                if ((endTime.isPresent()) && (!date.isPresent() && !startTime.isPresent())) {
                    throw new NoSuchElementException("");
                }
            }

            String stringDate = date.orElse("");
            String stringStartTime = startTime.orElse("");
            String stringEndTime = endTime.orElse("");

            /*
             * To parse date input if required and throws exceptions if
             * incorrect arguments of date are included
             */

            if (date.isPresent()) {
                String[] splited = stringDate.split("\\s+");
                stringDate = splited[0];
                try {
                    stringStartTime = splited[1];
                    if (Integer.parseInt(stringStartTime) >= 2400) {
                        throw new IllegalValueException(INVALID_TIME);
                    }
                    if (("").equals(stringEndTime)) {
                        stringEndTime = Integer.toString(100 + Integer.parseInt(splited[1]));
                    } else {
                        String[] splitedEndTime = stringEndTime.split("\\s+");
                        try {
                            if (!(splitedEndTime[1].isEmpty())) {
                                throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                        + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                        + "Type HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aioobe) {
                            stringEndTime = splitedEndTime[0];
                            if (Integer.parseInt(stringEndTime) >= 2400) {
                                throw new IllegalValueException(INVALID_TIME);
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    stringStartTime = "0000";
                    if (("").equals(stringEndTime)) {
                        stringEndTime = "2359";
                    } else {
                        String[] splitedEndTime = stringEndTime.split("\\s+");
                        try {
                            if (!(splitedEndTime[1].isEmpty())) {
                                throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                        + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                        + "Type HELP for user guide with detailed explanations of all commands");
                            }
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            stringEndTime = splitedEndTime[0];
                            if (Integer.parseInt(stringEndTime) >= 2400) {
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
                if (Integer.parseInt(stringStartTime) > Integer.parseInt(stringEndTime)) {
                    throw new IllegalValueException(
                            "Invalid input of time, start time has to be earlier than end time");
                }
            }

            if (stringStartTime.matches("[a-zA-Z]+")) {
                StringBuilder stringBuilderStartTime = new StringBuilder();

                stringBuilderStartTime.append(stringStartTime);
                stringBuilderStartTime.append(" ");
                stringBuilderStartTime.append("0000");

                stringStartTime = stringBuilderStartTime.toString();
            }

            if (stringEndTime.matches("[a-zA-Z]+")) {
                StringBuilder stringBuilderEndTime = new StringBuilder();

                stringBuilderEndTime.append(stringEndTime);
                stringBuilderEndTime.append(" ");
                stringBuilderEndTime.append("2359");

                stringEndTime = stringBuilderEndTime.toString();
            }

            if ((startTime.isPresent()) && (!stringStartTime.matches("\\d+"))) {
                String[] splitedStartTime = stringStartTime.split("\\s+");
                try {
                    if (splitedStartTime[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                        splitedStartTime[0] = CurrentDate.getNewDate(splitedStartTime[0]);
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(splitedStartTime[0]);
                    stringBuilder.append(" ");
                    stringBuilder.append(splitedStartTime[1]);

                    stringStartTime = stringBuilder.toString();
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    if (splitedStartTime[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                        stringStartTime = CurrentDate.getNewDate(splitedStartTime[0]);
                    }
                }
            }

            if ((endTime.isPresent()) && (!stringEndTime.matches("\\d+"))) {
                String[] splitedEndTime = stringEndTime.split("\\s+");
                try {
                    if (splitedEndTime[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                        splitedEndTime[0] = CurrentDate.getNewDate(splitedEndTime[0]);
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(splitedEndTime[0]);
                    stringBuilder.append(" ");
                    stringBuilder.append(splitedEndTime[1]);

                    stringEndTime = stringBuilder.toString();
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    if (splitedEndTime[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                        stringEndTime = CurrentDate.getNewDate(splitedEndTime[0]);
                    }
                }
            }

            /*
             * To parse deadline input if required and throws exceptions if
             * incorrect arguments of deadline are included
             */

            if (deadline.isPresent()) {
                String stringDeadline = deadline.get();
                String[] splited = stringDeadline.trim().split("\\s+");
                stringDate = splited[0];
                try {
                    stringEndTime = splited[1];
                    if (Integer.parseInt(stringEndTime) >= 2400) {
                        throw new IllegalValueException(INVALID_TIME);
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    stringEndTime = "2359";
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

            if (!("").equals(stringStartTime)) {
                startTime = Optional.of(stringStartTime);
            } else {
                startTime = Optional.of(EMPTY_FIELD);
            }
            if (!("").equals(stringEndTime)) {
                endTime = Optional.of(stringEndTime);
            } else {
                endTime = Optional.of(EMPTY_FIELD);
            }
            if (!("").equals(stringDate)) {
                date = Optional.of(stringDate);
            } else {
                date = Optional.of(EMPTY_FIELD);
            }

            if (date.isPresent()) {
                if (date.get().matches(STARTDATE_VALIDATION_REGEX2)) {
                    date = Optional.of(CurrentDate.getNewDate(date.get()));
                }
            }

            updateTaskDescriptor.setTaskName(ParserUtil.parseTaskName(taskName));
            updateTaskDescriptor.setDate(ParserUtil.parseDate(date));
            updateTaskDescriptor.setStartTime(ParserUtil.parseStartTime(startTime));
            updateTaskDescriptor.setEndTime(ParserUtil.parseEndTime(endTime));

            // updateTaskDescriptor.setCategories(parseCategoriesForUpdate(
            // ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return new IncorrectCommand(
                    "Invalid command input!\nExample of Allowed Format: ADD e-mail John BY thursday 1400\n"
                            + "Type HELP for user guide with detailed explanations of all commands");
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand("Invalid input after prefix TO, only input of time is allowed\n"
                    + "Example of Allowed Format: ADD project meeting ON thursday 1400 TO 1800\n"
                    + "Type HELP for user guide with detailed explanations of all commands");
        }

        if (!updateTaskDescriptor.isAnyFieldUpdated()) {
            return new IncorrectCommand(UpdateCommand.MESSAGE_NOT_UPDATED);
        }
        return new UpdateCommand(index.get(), updateTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    /*
     * private Optional<UniqueCategoryList>
     * parseCategoriesForUpdate(Collection<String> categories) throws
     * IllegalValueException { assert categories != null;
     *
     * if (categories.isEmpty()) { return Optional.empty(); } Collection<String>
     * categorySet = categories.size() == 1 && categories.contains("") ?
     * Collections.emptySet() : categories; return
     * Optional.of(ParserUtil.parseCategories(categorySet)); }
     */
}
