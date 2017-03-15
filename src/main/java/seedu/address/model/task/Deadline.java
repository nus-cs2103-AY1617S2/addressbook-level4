package seedu.address.model.task;

import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.dateparser.DateTimeParserManager;
import seedu.address.logic.parser.DateTimeParser;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline can have zero or more characters";
    public static final String DATE_VALIDATION_REGEX = "[a-zA-Z]+";

    /*
     * The deadline must have at least one visible character
     * TODO: change regex once deadline can be translated to a date
     */
    public static final String DEADLINE_VALIDATION_REGEX = ".*";
    private static final DateTimeParser dateParser = new DateTimeParserManager();

    private Date deadline;
    private String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     * @throws IllegalDateTimeValueException if given deadline could be parse to a valid date
     */
    public Deadline(String strDeadline) throws IllegalValueException, IllegalDateTimeValueException {
        assert strDeadline != null;
        if (!isValidDeadline(strDeadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        } else {
            if (!isEmptyDeadline(strDeadline)) {
                try {
                    List<Date> dateList;
<<<<<<< HEAD
                    if (strDeadline.matches(DATE_VALIDATION_REGEX)) {
=======
                    if (strDeadline.matches("[a-zA-Z]+")) {
>>>>>>> V0.2-yesha
                        dateList = dateParser.parse(strDeadline + " 235959").get(0).getDates();
                    } else {
                        dateList = dateParser.parse(strDeadline).get(0).getDates();
                    }

                    if (dateList != null && dateList.size() > 0) {
                        this.deadline = dateList.get(0);
                        this.value = deadline.toString();
                    }
                } catch (Exception e) {
                    throw new IllegalDateTimeValueException();
                }
            } else {
                value = "";
            }
        }
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string has no date.
     */
    public static boolean isEmptyDeadline(String test) {
        return test.equals("");
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isParsableDate(String test) {
        return !dateParser.parse(test).isEmpty();
    }

    public Date getDateTime() {
        return deadline;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.deadline.equals(((Deadline) other).deadline)); // state check
    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }

}
