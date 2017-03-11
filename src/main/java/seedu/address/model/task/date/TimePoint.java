package seedu.address.model.task.date;

import java.util.Date;
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
        Optional<DateValue> parseResult;

        parseResult = DateParser.parseDateOnlyString(dateString);
        if (parseResult.isPresent()) {
            date = parseResult.get();
            return;
        }

        parseResult = DateParser.parseTimeOnlyString(dateString);
        if (parseResult.isPresent()) {
            date = ((DateValue) new DateTime(new Date(), parseResult.get().getValue()));
            return;
        }

        parseResult = DateParser.parseDateTimeString(dateString);
        if (parseResult.isPresent()) {
            date = parseResult.get();
            return;
        }

        throw new IllegalValueException(MESSAGE_TIMEPOINT_CONSTRAINTS);
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
