package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX1;
import static seedu.taskmanager.model.task.StartDate.STARTDATE_VALIDATION_REGEX2;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.DateTimeUtil;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.UpdateCommand;
import seedu.taskmanager.logic.commands.UpdateCommand.UpdateTaskDescriptor;
import seedu.taskmanager.model.category.UniqueCategoryList;

//@@author A0142418L
/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser {

    public static final String ALPHABET_ONLY_STRING_REGEX = "[a-zA-Z]+";
    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String INVALID_TIME = "Invalid input for time\nTime must be between 0000 and 2359";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_ON, PREFIX_BY, PREFIX_FROM, PREFIX_TO,
                PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();

        Boolean isUpdateToDeadlineTask = false;

        try {
            Optional<String> taskName = preambleFields.get(1);
            Optional<String> onPrefixInput = argsTokenizer.getValue(PREFIX_ON);
            Optional<String> byPrefixInput = argsTokenizer.getValue(PREFIX_BY);
            Optional<String> fromPrefixInput = argsTokenizer.getValue(PREFIX_FROM);
            Optional<String> toPrefixInput = argsTokenizer.getValue(PREFIX_TO);

            updateTaskInputValidation(onPrefixInput, byPrefixInput, fromPrefixInput, toPrefixInput);

            if (byPrefixInput.isPresent()) {
                isUpdateToDeadlineTask = true;
            }

            String stringStartDate = "";
            String stringStartTime = "";
            String stringEndTime = "";
            String stringEndDate = "";

            /*
             * To validate and assign task details if user only wants to input
             * numbers
             */
            if (isPrefixNumbersOnly(fromPrefixInput) && (DateTimeUtil.isValidTime(fromPrefixInput.get()))) {
                stringStartTime = fromPrefixInput.get();
            } else {
                if (isPrefixNumbersOnly(onPrefixInput) && (DateTimeUtil.isValidTime(onPrefixInput.get()))) {
                    stringStartTime = onPrefixInput.get();
                }
            }
            if (isPrefixNumbersOnly(toPrefixInput) && (DateTimeUtil.isValidTime(toPrefixInput.get()))) {
                stringEndTime = toPrefixInput.get();
            }

            /*
             * To parse user inputs if user wants to update current task to a
             * same day event task
             */
            if (onPrefixInput.isPresent()) {
                if (isValidTimePrefixesArgLen(onPrefixInput)) {
                    String[] splited = onPrefixInput.get().trim().split("\\s+");
                    stringStartDate = splited[0];
                    stringEndDate = splited[0];
                    try {
                        stringStartTime = splited[1];
                        if (!DateTimeUtil.isValidTime(stringStartTime)) {
                            throw new IllegalValueException(INVALID_TIME);
                        }
                        if (!toPrefixInput.isPresent()) {
                            stringEndTime = DateTimeUtil.includeOneHourBuffer(stringStartTime);
                            if (Integer.parseInt(stringEndTime) < 100) {
                                if (stringEndDate.trim().matches(ALPHABET_ONLY_STRING_REGEX)) {
                                    stringEndDate = DateTimeUtil.getFutureDate(1, "days",
                                            DateTimeUtil.getNewDate(stringEndDate));
                                } else {
                                    stringEndDate = DateTimeUtil.getFutureDate(1, "days", stringEndDate);
                                }
                            }
                        } else {
                            String[] splitedEndTime = toPrefixInput.get().split("\\s+");
                            try {
                                if (!(splitedEndTime[1].isEmpty())) {
                                    throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                            + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                            + "Type HELP for user guide with detailed explanations of all commands");
                                }
                            } catch (ArrayIndexOutOfBoundsException aioobe) {
                                stringEndTime = splitedEndTime[0];
                                if (!DateTimeUtil.isValidTime(stringEndTime)) {
                                    throw new IllegalValueException(INVALID_TIME);
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        stringStartTime = "0000";
                        if (("").equals(stringEndTime)) {
                            stringEndTime = "2359";
                        } else {
                            String[] splitedEndTime = toPrefixInput.get().split("\\s+");
                            try {
                                if (!(splitedEndTime[1].isEmpty())) {
                                    throw new IllegalValueException("Incorrect input after TO prefix.\n"
                                            + "Example of Allowed Format: ADD task ON thursday 1200 TO 1400\n"
                                            + "Type HELP for user guide with detailed explanations of all commands");
                                }
                            } catch (ArrayIndexOutOfBoundsException aiobe) {
                                stringEndTime = splitedEndTime[0];
                                if (!DateTimeUtil.isValidTime(stringEndTime)) {
                                    throw new IllegalValueException(INVALID_TIME);
                                }
                            }
                        }
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }

            if (fromPrefixInput.isPresent()) {
                if (fromPrefixInput.get().trim().matches(ALPHABET_ONLY_STRING_REGEX)
                        || fromPrefixInput.get().trim().matches(STARTDATE_VALIDATION_REGEX1)) {
                    stringStartTime = "0000";
                }
                if (fromPrefixInput.get().trim().matches(ALPHABET_ONLY_STRING_REGEX)) {
                    stringStartDate = DateTimeUtil.getNewDate(fromPrefixInput.get());
                }
            }

            if (toPrefixInput.isPresent()) {
                if (toPrefixInput.get().trim().matches(ALPHABET_ONLY_STRING_REGEX)
                        || toPrefixInput.get().trim().matches(STARTDATE_VALIDATION_REGEX1)) {
                    stringEndTime = "2359";
                }
                if (toPrefixInput.get().trim().matches(ALPHABET_ONLY_STRING_REGEX)) {
                    stringEndDate = DateTimeUtil.getNewDate(toPrefixInput.get());
                }
            }

            if ((fromPrefixInput.isPresent()) && (!fromPrefixInput.get().matches("\\d+"))) {
                if (isValidTimePrefixesArgLen(fromPrefixInput)) {
                    String[] splited = fromPrefixInput.get().split("\\s+");
                    try {
                        if (splited[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            splited[0] = DateTimeUtil.getNewDate(splited[0]);
                        }
                        stringStartDate = splited[0];
                        stringStartTime = splited[1];
                        if (!DateTimeUtil.isValidTime(stringStartTime)) {
                            throw new IllegalValueException(INVALID_TIME);
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (splited[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            stringStartDate = DateTimeUtil.getNewDate(splited[0]);
                        }
                    }
                    if (toPrefixInput.isPresent() && toPrefixInput.get().matches("\\d+")) {
                        throw new NoSuchElementException();
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }

            if ((toPrefixInput.isPresent()) && (!toPrefixInput.get().matches("\\d+"))) {
                if (isValidTimePrefixesArgLen(toPrefixInput)) {
                    String[] splited = toPrefixInput.get().split("\\s+");
                    try {
                        if (splited[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            splited[0] = DateTimeUtil.getNewDate(splited[0]);
                        }
                        stringEndDate = splited[0];
                        stringEndTime = splited[1];
                        if (!DateTimeUtil.isValidTime(stringEndTime)) {
                            throw new IllegalValueException(INVALID_TIME);
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (splited[0].matches(STARTDATE_VALIDATION_REGEX2)) {
                            stringEndDate = DateTimeUtil.getNewDate(splited[0]);
                        }
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }

            /*
             * To parse deadline input if required and throws exceptions if
             * incorrect arguments of deadline are included
             */
            if (byPrefixInput.isPresent()) {
                if (isValidTimePrefixesArgLen(byPrefixInput)) {
                    String[] splited = byPrefixInput.get().trim().split("\\s+");
                    stringEndDate = splited[0];
                    try {
                        stringEndTime = splited[1];
                        if (!DateTimeUtil.isValidTime(stringEndTime)) {
                            throw new IllegalValueException(INVALID_TIME);
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        stringEndTime = "2359";
                    } catch (NumberFormatException nfe) {
                        return new IncorrectCommand("Invalid input after prefix BY\n"
                                + "Example of Allowed Format: ADD project meeting BY thursday 1400 \n"
                                + "Type HELP for user guide with detailed explanations of all commands");
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }

            Optional<String> startDate;
            Optional<String> startTime;
            Optional<String> endDate;
            Optional<String> endTime;

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
            if (!("").equals(stringStartDate)) {
                startDate = Optional.of(stringStartDate);
            } else {
                startDate = Optional.of(EMPTY_FIELD);
            }
            if (!("").equals(stringEndDate)) {
                endDate = Optional.of(stringEndDate);
            } else {
                endDate = Optional.of(EMPTY_FIELD);
            }

            if (startDate.isPresent() && startDate.get().matches(STARTDATE_VALIDATION_REGEX2)) {
                startDate = Optional.of(DateTimeUtil.getNewDate(startDate.get()));
            }
            if (endDate.isPresent() && endDate.get().matches(STARTDATE_VALIDATION_REGEX2)) {
                endDate = Optional.of(DateTimeUtil.getNewDate(endDate.get()));
            }

            updateTaskDescriptor.setTaskName(ParserUtil.parseTaskName(taskName));
            updateTaskDescriptor.setStartDate(ParserUtil.parseStartDate(startDate));
            updateTaskDescriptor.setStartTime(ParserUtil.parseStartTime(startTime));
            updateTaskDescriptor.setEndDate(ParserUtil.parseEndDate(endDate));
            updateTaskDescriptor.setEndTime(ParserUtil.parseEndTime(endTime));
            updateTaskDescriptor.setCategories(
                    parseCategoriesForUpdate(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));

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
        return new UpdateCommand(index.get(), updateTaskDescriptor, isUpdateToDeadlineTask);
    }

    /**
     * @param prefixInput
     * @return true if prefixInput is only numbers
     */
    private boolean isPrefixNumbersOnly(Optional<String> prefixInput) {
        return prefixInput.isPresent() && prefixInput.get().matches("\\d+");
    }

    /*
     * Checks to ensure correct combinations of arguments are added by user when
     * updating tasks in the task manager
     */
    private void updateTaskInputValidation(Optional<String> onPrefixInput, Optional<String> byPrefixInput,
            Optional<String> fromPrefixInput, Optional<String> toPrefixInput) throws NoSuchElementException {
        if (!onPrefixInput.isPresent() || !byPrefixInput.isPresent() || !fromPrefixInput.isPresent()
                || !toPrefixInput.isPresent()) {
            if ((onPrefixInput.isPresent()) && ((byPrefixInput.isPresent()) || (fromPrefixInput.isPresent()))) {
                throw new NoSuchElementException();
            }
            if ((byPrefixInput.isPresent())
                    && ((onPrefixInput.isPresent()) || (fromPrefixInput.isPresent()) || (toPrefixInput.isPresent()))) {
                throw new NoSuchElementException();
            }
            if (((fromPrefixInput.isPresent()) && ((onPrefixInput.isPresent()) || (byPrefixInput.isPresent())))) {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueCategoryList> parseCategoriesForUpdate(Collection<String> categories)
            throws IllegalValueException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("") ? Collections.emptySet()
                : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

    private boolean isValidTimePrefixesArgLen(Optional<String> prefixInput) {
        String[] splited = prefixInput.get().trim().split("\\s+");
        return (splited.length <= 2);
    }
}
