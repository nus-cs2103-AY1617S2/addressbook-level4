package seedu.address.model.task.date;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Time Period with beginning and ending dates.
 */
public class TimePoint implements TaskDate {

    public static final String MESSAGE_TIMEPOINT_CONSTRAINTS =
        "Allowed format for TimePoint obj: [preposition] [Valid Date]";

    public static final String OUTPUT_FORMAT = "%s";

    private final DateValue date;

    public TimePoint(String dateString) throws IllegalValueException {
        Optional<DateValue> parseResult = DateParser.parseTimePointString(dateString);
        if (!parseResult.isPresent()) {
            throw new IllegalValueException(MESSAGE_TIMEPOINT_CONSTRAINTS);
        }
        date = parseResult.get();
    }

    @Override
    public String toString() {
        return String.format(OUTPUT_FORMAT, date.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TimePoint // instanceof handles nulls
                    && this.date.equals(((TimePoint) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
