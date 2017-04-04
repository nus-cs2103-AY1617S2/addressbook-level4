// @@author A0139399J
package seedu.doit.model.item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.parser.DateTimeParser;

/**
 * Represents a Item's end time in the item manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime  implements Comparable<EndTime> {

    public static final String NO_END_TIME = null;
    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Item End Time should be in "
        + "MM-DD-YY HH:MM Format or relative date today, tomorrow, next wednesday";
    public static final String ENDTIME_VALIDATION_REGEX = "^([0-9]||0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])"
        + "/([0-9][0-9])?[0-9][0-9] [0-2]\\d:[0-6]\\d$";

    public final String value;
    private final LocalDateTime dateObject;


    /**
     * Gives a NO_END_TIME which represents there is no end time.
     */
    public EndTime() {
        this.value = NO_END_TIME;
        this.dateObject = null;
    }

    /**
     * Validates given endTime.
     *
     * @throws IllegalValueException if given endTime string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        if (endTime == NO_END_TIME) {
            this.value = NO_END_TIME;
            this.dateObject = null;
        } else {
            String trimmedEndTime = endTime.trim();

            dateObject = DateTimeParser.parseDateTime(trimmedEndTime)
                .orElseThrow(() -> new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS));

            String dateInString = formatDate(dateObject);

            if (!isValidEndTime(dateInString)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value = dateInString;
        }
    }

    /**
     * Returns if a given string is a valid item end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    private static String formatDate(LocalDateTime input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        return input.format(formatter);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
            ) || ((other instanceof EndTime // instanceof handles nulls
            ) && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int compareTo(EndTime other) {
        return this.value.compareTo(other.value);
    }

    public LocalDateTime getDateTimeObject() {
        return this.dateObject;
    }

}
