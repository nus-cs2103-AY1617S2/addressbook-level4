//@@author A0144885R
package seedu.address.model.task.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.util.StringUtil;

/**
 * A Parser for TaskTime class.
 *
 * Strings will be converted to lowercase to make parsing easier
 * Default date will be today instead of 01/01/1970 as in Java implementation.
 */
public class DateParser {

    /* Preposition constants */
    public static final String FROM = "from";

    /* Regex constants */
    public static final String TIMEPERIOD_DELIMINATORS_REGEX = "(from|\\sto\\s)";

    public static final String[] DATE_NUMBERIC_REGEX = {
        "\\d{1,4}:\\d{1,2}:\\d{1,4}",
        "\\d{1,4}/\\d{1,2}/\\d{1,4}",
        "\\d{1,4}.\\d{1,2}.\\d{1,4}",
        "\\d{1,4}-\\d{1,2}-\\d{1,4}"
    };

    public static final String[] DATE_NUMBERIC_FORMATS = {
        "dd.MM.yy", "dd/MM/yy", "dd-MM-yy", "dd:MM:yy",
        "dd.MM.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd:MM:yyyy",
        "yyyy.MM.dd", "yyyy/MM/dd", "yyyy-MM-dd", "yyyy:MM:dd"
    };

    public static String convertDateStringToAmericanFormat(String dateString) {

        for (String regex : DATE_NUMBERIC_REGEX) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dateString);

            // Only attempt once
            if (matcher.find()) {
                String matchedSubstr = matcher.group();

                // Converting
                for (String dateFormat : DATE_NUMBERIC_FORMATS) {
                    DateFormat df = new SimpleDateFormat(dateFormat);
                    df.setLenient(false);
                    try {
                        Date date = df.parse(matchedSubstr);
                        String convertedSubstr = (date.getMonth() + 1) + "/"   // Starting at 0
                                                    + date.getDate() + "/"
                                                    + (date.getYear() + 1900); // Years from 1900
                        return matcher.replaceFirst(convertedSubstr);
                    } catch (ParseException e) {
                        // Do nothing
                    }
                }
            }
        }

        // No conversion made
        return dateString;
    }


    /**
     * Parsers.
     */
    public static Optional<DateValue> parseString(String dateString) {
        // Natty only converts dates in American format.
        dateString = convertDateStringToAmericanFormat(dateString);

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dateString);

        // Accept no more than 1 result
        int count = 0;
        for (DateGroup group : groups) {
            count += group.getDates().size();
        }
        if (count > 1) {
            return Optional.ofNullable(null);
        }

        for (DateGroup group : groups) {
            List<Date> dates = group.getDates();
            for (Date date : dates) {
                if (group.isTimeInferred()) {
                    DateOnly result = new DateOnly(date);
                    return Optional.of((DateValue) result);
                } else {
                    DateTime result = new DateTime(date);
                    return Optional.of((DateValue) result);
                }
            }
        }
        return Optional.ofNullable(null);
    }


    public static Optional<DateValue> parseTimePointString(String dateString) {
        dateString = StringUtil.removeRedundantSpaces(dateString);
        dateString = dateString.toLowerCase();

        return parseString(dateString);
    }


    public static Optional<PairResult<DateValue, DateValue>> parseTimePeriodString(String dateString) {
        dateString = StringUtil.removeRedundantSpaces(dateString);
        dateString = dateString.toLowerCase();

        if (!dateString.startsWith(FROM)) {
            return Optional.ofNullable(null);
        }

        String[] texts = dateString.split(TIMEPERIOD_DELIMINATORS_REGEX);
        // DateString must be splitted into exactly 3 parts (empty, begin date and end date)
        if (texts.length != 3) {
            return Optional.ofNullable(null);
        }
        String beginDateString = texts[1];
        String endDateString = texts[2];

        Optional<DateValue> beginDate = parseTimePointString(beginDateString);
        Optional<DateValue> endDate = parseTimePointString(endDateString);

        if (beginDate.isPresent() && endDate.isPresent()) {
            return Optional.of(new PairResult<DateValue, DateValue>(beginDate.get(), endDate.get()));
        } else {
            return Optional.ofNullable(null);
        }
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
