package seedu.jobs.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.experimental.theories.Theories;

import seedu.jobs.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time signature in JOBS. Guarantees: immutable; is valid
 * as declared in {@link #isValidTime(String)}
 */


//@@author A0130979U

public class Time implements Comparable<Time> {


    public static final String MESSAGE_TIME_CONSTRAINT = "Time should always follow the dd/mm/yy hh:mm format";
    public static final String DATE_VALIDATION_REGEX = "^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/([0-9][0-9][0-9][0-9])";
    public static final String TIME_VALIDATION_REGEX = "(0[1-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])";
    public static final String DEFAULT_TIME = "";
    public String value;

    /**
     * Validates given time-values input.
     *
     * @throws IllegalValueException
     *             if the input is invalid
     */
    public Time(Optional<String> startTime) throws IllegalValueException {
        if (!startTime.isPresent()) {
            this.value = DEFAULT_TIME;
        } else {
            if (startTime.get().equals("")) {
                this.value = DEFAULT_TIME;
            }  else if (!isValidTime(startTime.get())) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINT);
            }  else {
                this.value = startTime.get();
            }
        }
    }

    /**
     * Returns true if a given string is in valid time format
     */
    public static boolean isValidTime(String test) {
        String time = extractTime(test);
        String date = extractDate(test);
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu").
                    withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return time.length() > 0;

    }
    
    /**
     * Check if the given time has passed
     */
    public boolean isObsolette() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");
        LocalDateTime thisDate = LocalDateTime.parse(this.value, formatter);
        return thisDate.compareTo(LocalDateTime.now())==1;
    }

    /**
     * Extract date (dd/mm/yyyy) from an input string
     */
    public static String extractDate(String date) {
        Pattern datePattern = Pattern.compile(DATE_VALIDATION_REGEX);
        Matcher dateMatcher = datePattern.matcher(date);
        String value = "";
        while (dateMatcher.find()) {
            value += (dateMatcher.group());
        }
        return value;
    }

    /**
     * extract time (HH:mm) from an input string
     */
    public static String extractTime(String time) {
        Pattern timePattern = Pattern.compile(TIME_VALIDATION_REGEX);
        Matcher timeMatcher = timePattern.matcher(time);
        String value = "";
        while (timeMatcher.find()) {
            value += (timeMatcher.group());
        }
        return value;
    }

    /**
     * Increment the time value by the specified number of days
     *
     */
    public void addDays(int days) {
        if (this.value != DEFAULT_TIME) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            String oldDate = extractDate(this.value);
            String time = extractTime(this.value);
            LocalDate date = LocalDate.parse(oldDate, formatter);
            this.value = date.plusDays(days).format(formatter) + " " + time;
        }
    }

    @Override
    public int compareTo(Time time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");
        LocalDateTime thisDate = LocalDateTime.parse(this.value, formatter);
        LocalDateTime otherDate = LocalDateTime.parse(time.value, formatter);
        return thisDate.compareTo(otherDate);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                        && this.value.equals(((Time) other).value)); // state
                                                                     // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
