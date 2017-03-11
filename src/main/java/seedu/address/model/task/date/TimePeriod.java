package seedu.address.model.task.date;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.date.DateParser.PairResult;

/**
 * Represents a Time Period with beginning and ending dates.
 */
public class TimePeriod implements TaskDate {

    public static final String MESSAGE_TIMEPERIOD_CONSTRAINTS =
        "Allowed format for TimePeriod obj: from [Valid Date] to [Valid Date]";

    public static final String OUTPUT_FORMAT = "from %s to %s";

    private final DateValue beginDate;
    private final DateValue endDate;

    public TimePeriod(String dateString) throws IllegalValueException {
        Optional<PairResult<DateValue, DateValue>> parseResult = DateParser.parseTimePeriodString(dateString);
        if (!parseResult.isPresent()) {
            throw new IllegalValueException(MESSAGE_TIMEPERIOD_CONSTRAINTS);
        }
        beginDate = parseResult.get().first;
        endDate = parseResult.get().second;
    }

    @Override
    public String toString() {
        return String.format(OUTPUT_FORMAT, beginDate.toString(), endDate.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TimePeriod // instanceof handles nulls
                    && this.beginDate.equals(((TimePeriod) other).beginDate) // state check
                    && this.endDate.equals(((TimePeriod) other).endDate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginDate, endDate);
    }
}
