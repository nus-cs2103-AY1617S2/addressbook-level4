package seedu.address.model.task.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.StringUtil;

/**
 * A Parser for TaskTime class.
 *
 * Strings will be converted to lowercase to make parsing easier
 */
public class DateParser {

    /* Preposition constants */
    public static final String FROM = "from";


    /* Regex constants */
    public static final String TIMEPERIOD_DELIMINATORS_REGEX = "(from|to)";

    /* Date and time format */
    public static final String[] DATE_VALIDATION_FORMAT = {
        "dd-MM-yy",
        "dd/MM/yy",
        "dd-MM-yyyy",
        "dd/MM/yyyy",
        "EEE, MMM dd yyyy",
        "EEE, dd MMM yyyy"
    };

    public static final String[] TIME_VALIDATION_FORMAT = {
        "KK:mm aaa",
        "hh:mm aaa",
        "HH:mm"
    };

    // Separators used to combine date and time
    public static final String[] DATE_TIME_SEPARATORS = {
        ", ",
        " "
    };


    /**
     * Parsers.
     *
     * - parseTimePeriodsString tries to find beginning date and ending date
     *   and parse them separately
     */
    public static Optional<Date> parseString(String dateString, String[] dateFormats) {
        dateString = StringUtil.removeRedundantSpaces(dateString);
        dateString = dateString.toLowerCase();

        for (String dateFormat : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(dateFormat);
                df.setLenient(false);
                return Optional.of(df.parse(dateString));
            } catch (ParseException e) {
                // Do nothing
            }
        }
        return Optional.ofNullable(null);
    }

    public static Optional<DateValue> parseDateOnlyString(String dateString) {
        Optional<Date> parseResult = parseString(dateString, DATE_VALIDATION_FORMAT);
        return Optional.ofNullable((parseResult.isPresent() ?
                                    (DateValue) new DateOnly(parseResult.get()) : null));
    }

    public static Optional<DateValue> parseTimeOnlyString(String dateString) {
        Optional<Date> parseResult = parseString(dateString, TIME_VALIDATION_FORMAT);
        return Optional.ofNullable((parseResult.isPresent() ?
                                    (DateValue) new DateTime(parseResult.get()) : null));
    }

    public static Optional<DateValue> parseDateTimeString(String dateString) {
        Optional<Date> parseResult = parseString(dateString, getAllPossibleDateTimeFormats());
        return Optional.ofNullable((parseResult.isPresent() ?
                                    (DateValue) new DateTime(parseResult.get()) : null));
    }

    public static Optional<PairResult<DateValue, DateValue>> parseTimePeriodString(String dateString) {
        dateString = StringUtil.removeRedundantSpaces(dateString);
        dateString = dateString.toLowerCase();

        if (!dateString.startsWith(FROM)) {
            return Optional.ofNullable(null);
        }

        String[] texts = dateString.split(TIMEPERIOD_DELIMINATORS_REGEX);
        Optional<DateValue> beginDate = parseDateOnlyString(dateString);
        if (!beginDate.isPresent()) {
            beginDate = parseDateTimeString(dateString);
        }
        Optional<DateValue> endDate = parseDateOnlyString(dateString);
        if (!endDate.isPresent()) {
            endDate = parseDateTimeString(dateString);
        }

        if (beginDate.isPresent() && endDate.isPresent()) {
            return Optional.of(new PairResult<DateValue, DateValue>(beginDate.get(), endDate.get()));
        } else {
            return null;
        }
    }


    /**
     * Returns all combinations of date, time and separator
     */
    public static String[] getAllPossibleDateTimeFormats() {
        // Init date formats
        int dateFormatsCount = DATE_VALIDATION_FORMAT.length
                                * DATE_TIME_SEPARATORS.length
                                * TIME_VALIDATION_FORMAT.length;
        String[] dateFormats = new String[dateFormatsCount];

        // Add all combinations of time and date
        int index = 0;
        for (String dateRegex : DATE_VALIDATION_FORMAT) {
            for (String separator : DATE_TIME_SEPARATORS) {
                for (String timeRegex : TIME_VALIDATION_FORMAT) {
                    dateFormats[index++] = dateRegex + separator + timeRegex;
                }
            }
        }
        return dateFormats;
    }

    /**
     * A PairResult class for methods that return 2 objects as result.
     */
    public static class PairResult<T, S> {

        public T first;
        public S second;

        public PairResult(T first, S second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof PairResult // instanceof handles nulls
                        && this.first.equals(((PairResult) other).first) // state check
                        && this.second.equals(((PairResult) other).second));
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

}
