package seedu.watodo.logic.parser;

import static seedu.watodo.logic.parser.AddCommandParser.EXTRACT_ARGS_REGEX;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

//@@author A0143076J
/**
 * Parses out the startDate and endDate, if any, in a given String of args
 */
public class DateTimeParser {

    public enum TaskType { FLOAT, DEADLINE, EVENT };
    private TaskType type;

    private String startDate;
    private String endDate;

    public static final String MESSAGE_INVALID_NUM_DATETIME = "Too many/few dateTime arguments!";

    /** Constructs a DateTimeParser object with both default start and end date-times null */
    public DateTimeParser() {
        startDate = null;
        endDate = null;
    }

    /**
     * Determines if the combination of dateTime prefixes are valid, and if so, extracts out the
     * startDate and endDate if they exist
     */
    public void parse(String args) throws IllegalValueException {
        ArgumentTokenizer datesTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON,
                PREFIX_FROM, PREFIX_TO);
        datesTokenizer.tokenize(args);

        type = checkTaskType(datesTokenizer);

        if (type.equals(TaskType.DEADLINE) || type.equals(TaskType.EVENT)) {
            extractDates(datesTokenizer);
        }
    }

    /**
     * Returns the arg with the dateTime prefixes and dates removed
     */
    public String trimArgsOfDates(String arg) {

        if (startDate != null) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_FROM.getPrefix(), startDate), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), startDate), " ");
        }
        if (endDate != null) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_BY.getPrefix(), endDate), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), endDate), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_TO.getPrefix(), endDate), " ");
        }
        return arg.trim();
    }


    /**
     * Checks the type of task(floating, deadline or event) to be added based on
     * the dateTime prefixes entered by the user.
     *
     * @throws IllegalValueException if the combination of dateTime prefixes is not recognized             entered
     */
    private TaskType checkTaskType(ArgumentTokenizer datesTokenizer) throws IllegalValueException {

        boolean hasBy = datesTokenizer.getUniqueValue(PREFIX_BY).isPresent();
        boolean hasOn = datesTokenizer.getUniqueValue(PREFIX_ON).isPresent();
        boolean hasFrom = datesTokenizer.getUniqueValue(PREFIX_FROM).isPresent();
        boolean hasTo = datesTokenizer.getUniqueValue(PREFIX_TO).isPresent();

        if (!hasBy && !hasOn && !hasFrom && !hasTo) {
            return TaskType.FLOAT;
        }
        if ((hasBy && !hasOn && !hasFrom && !hasTo) || (!hasBy && hasOn && !hasFrom && !hasTo)) {
            return TaskType.DEADLINE;
        }
        if ((!hasBy && !hasOn && hasFrom && hasTo) || (!hasBy && hasOn && !hasFrom && hasTo)) {
            return TaskType.EVENT;
        }
        throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
    }

    /**
     * Reads and validates the dates following the dateTime prefix and stores it as startDate
     * or endDate accordingly
     *
     * @throws IllegalValueException if the dates format are invalid
     */
    private void extractDates(ArgumentTokenizer datesTokenizer) throws IllegalValueException {

        List<String> argsWithDate = new ArrayList<String>();
        Collections.addAll(argsWithDate, datesTokenizer.getUniqueValue(PREFIX_BY).orElse(null),
            datesTokenizer.getUniqueValue(PREFIX_ON).orElse(null),
            datesTokenizer.getUniqueValue(PREFIX_FROM).orElse(null),
            datesTokenizer.getUniqueValue(PREFIX_TO).orElse(null));

        Parser parser = new Parser();
        List<String> datesInText = new ArrayList<String>();

        for (String arg : argsWithDate) {
            if (arg != null) {
                List<DateGroup> dateGroups = parser.parse(arg.trim());
                if (dateGroups.size() == 0 || dateGroups.get(0).getPosition() != 1) {
                    throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
                }
                datesInText.add(dateGroups.get(0).getText().trim());
            }
        }

        if (datesInText.size() == 1) {
            endDate = datesInText.get(0);
        }
        if (datesInText.size() == 2) {
            startDate = datesInText.get(0);
            endDate = datesInText.get(1);
        }
    }

    public TaskType getTaskType() {
        return type;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }

}
