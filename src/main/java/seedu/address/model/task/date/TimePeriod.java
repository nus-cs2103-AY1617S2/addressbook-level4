//@@author A0144885R
package seedu.address.model.task.date;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
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
        if (beginDate.after(endDate)) {
            throw new IllegalValueException(MESSAGE_TIMEPERIOD_CONSTRAINTS);
        }
    }

    /* Floating taskDate contains no value */
    public boolean isFloating() {
        return false;
    }

    public boolean hasPassed() {
        return endDate.before(DateUtil.getToday());
    }

    public boolean isHappeningToday() {
        DateValue today = DateUtil.getToday();
        return DateUtil.haveIntersection(beginDate.getBeginning(), endDate.getEnding(),
                                DateUtil.getBeginOfDay(today), DateUtil.getEndOfDay(today));
    }

    public boolean isHappeningTomorrow() {
        DateValue tmr = DateUtil.getTomorrow();
        return DateUtil.haveIntersection(beginDate.getBeginning(), endDate.getEnding(),
                                DateUtil.getBeginOfDay(tmr), DateUtil.getEndOfDay(tmr));
    }

    public DateValue getBeginning() {
        return beginDate.getBeginning();
    }

    public DateValue getEnding() {
        return endDate.getEnding();
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
