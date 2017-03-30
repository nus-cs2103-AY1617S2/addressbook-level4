package seedu.watodo.logic.parser;

import static seedu.watodo.logic.parser.AddCommandParser.EXTRACT_ARGS_REGEX;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_ON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

// @@author A0143076J-unused
//meant to allow user to add events in the format from/friday 1pm to/ 4pm where the endDate is captured as
//friday 4pm rather than current one of today 4pm, and thus throwing an exception warning that end date cannot
//be earlier than startDate. However, decided to do away with this as the format becomes rather messy and
//also code feels messier due to the way natty was implemented. ie. this addition enhancement not very value-adding.
/**
 * Parses out the startDate and endDate, and determines the corresponding
 * taskType of the given task
 */
public class DateTimeParser {

    public enum TaskType {
        FLOAT, DEADLINE, EVENT
    };

    private TaskType type;

    private String dateInText;
    private Date startDate;
    private Date endDate;

    public static final String MESSAGE_INVALID_NUM_DATETIME = "Too many/few dateTime arguments!";

    /**
     * Constructs a DateTimeParser object with both default start and end
     * date-times null
     */
    public DateTimeParser() {
        startDate = null;
        endDate = null;
    }

    /**
     * Determines if the combination of dateTime prefixes are valid, and if so,
     * extracts out the startDate and endDate if they exist
     */
    public void parse(String args) throws IllegalValueException {
        ArgumentTokenizer datesTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON, PREFIX_FROM);
        datesTokenizer.tokenize(args);

        String byPostfix = datesTokenizer.getUniqueValue(PREFIX_BY).orElse(null);
        String onPostfix = datesTokenizer.getUniqueValue(PREFIX_ON).orElse(null);
        String fromPostfix = datesTokenizer.getUniqueValue(PREFIX_FROM).orElse(null);
        checkIfPrefixFormatCorrect(byPostfix, onPostfix, fromPostfix);

        extractDates(byPostfix, onPostfix, fromPostfix);
        type = matchTaskType(byPostfix, onPostfix, fromPostfix);
        assert type != null;
    }

    /**
     * Returns the arg with the dateTime prefixes and dates removed
     */
    public String trimArgsOfDates(String arg) {

        if (type.equals(TaskType.DEADLINE)) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_BY.getPrefix(), dateInText), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), dateInText), " ");
        }
        if (type.equals(TaskType.EVENT)) {
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_FROM.getPrefix(), dateInText), " ");
            arg = arg.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), dateInText), " ");
        }
        return arg.trim();
    }

    /**
     * Checks if the dateTime prefixes entered by the user can in the valid
     * format. There can only be 0 or exactly 1 of any of the prefixes
     *
     * @throws IllegalValueException if formats of the prefixes are not valid
     */
    private void checkIfPrefixFormatCorrect(String byPostfix, String onPostfix, String fromPostfix)
            throws IllegalValueException {

        boolean hasBy = byPostfix != null;
        boolean hasOn = onPostfix != null;
        boolean hasFrom = fromPostfix != null;

        if (!((!hasBy && !hasOn && !hasFrom) || (hasBy && !hasOn && !hasFrom) || (!hasBy && hasOn && !hasFrom)
                || (!hasBy && !hasOn && hasFrom))) {
            throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
        }
    }

    /**
     * Reads and validates the dates following the dateTime prefix and stores it
     * as startDate or endDate accordingly
     *
     * @throws IllegalValueException if the dates format are invalid, or if too
     *             many date/time are given
     */
    private void extractDates(String byPostfix, String onPostfix, String fromPostfix) throws IllegalValueException {

        List<String> postfixArgs = new ArrayList<String>();
        Collections.addAll(postfixArgs, byPostfix, onPostfix, fromPostfix);
        Parser parser = new Parser(); // refers to the Parser class in natty

        for (String arg : postfixArgs) {
            if (arg == null) {
                continue;
            }

            List<DateGroup> dateGroups = parser.parse(arg.trim());

            if (dateGroups.size() == 0 || dateGroups.get(0).getPosition() != 1) {
                throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
            }
            this.dateInText = dateGroups.get(0).getText().trim();

            List<Date> dates = dateGroups.get(0).getDates();

            if (dates.size() > 2) {
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            if (dates.size() == 1) {
                endDate = dates.get(0);
            }
            if (dates.size() == 2) {
                startDate = dates.get(0);
                endDate = dates.get(1);
            }
        }

    }

    /**
     * Determines whether the task added by the user is floating, deadline or
     * event, based on whether it has start and end date/times. Checks that the
     * correct prefix is used to input the corresponding task type.
     *
     * @throws IllegalValueException if the taskType does not match the prefix
     *             used
     */
    private TaskType matchTaskType(String byPostfix, String onPostfix, String fromPostfix)
            throws IllegalValueException {
        if (this.getStartDate() == null && this.getEndDate() == null) {
            assert byPostfix == null && onPostfix == null && fromPostfix == null;
            return TaskType.FLOAT;
        }
        if (this.getStartDate() == null && this.getEndDate() != null) {
            if (fromPostfix != null) { // Exception handling: can only add a deadline with by/ and on/ prefixes
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            return TaskType.DEADLINE;
        }
        if (this.getStartDate() != null && this.getEndDate() != null) {
            if (byPostfix != null) { // Exception handling: can only add an event with from/ or on/ prefixes
                throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
            }
            return TaskType.EVENT;
        }
        return null;
    }

    public TaskType getTaskType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
