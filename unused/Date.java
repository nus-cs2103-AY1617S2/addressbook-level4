package seedu.task.model.task;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0140063X-unused
//this code uses multiple SimpleDateFormat to validate date input.
//this code is Working but decided to use pretty time parser instead which allows natural language parsing.
/**
 * Represents a Task's date in KIT. Guarantees: immutable; is valid as declared
 * in {@link #isValidDate(String)}
 */
public class Date {

    // add to user guide
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, use help to see "
            + "allowed formats or try this format: DD-MM-YY hh:mm AM/PM";
    public static final String DEFAULT_DATE = "DEFAULT_DATE";

    public static final ArrayList<SimpleDateFormat> ALLOWED_FORMATS = new ArrayList<>();
    private final java.util.Date value;
    private static SimpleDateFormat format = new SimpleDateFormat("d/M/y h:m a");

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        if (date.equals(DEFAULT_DATE)) {
            this.value = null;
        } else {
            String trimmedDate = date.trim();

            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }

            if (!trimmedDate.contains("-") && !trimmedDate.contains("/")
                    && !trimmedDate.matches("[0-9]* [a-zA-Z]{3,}.*")) {
                java.util.Date currentDate = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/y");
                trimmedDate = dateFormat.format(currentDate) + " " + trimmedDate;
            }

            this.value = new java.util.Date(getTime(trimmedDate));
        }
    }

    // date must be valid
    private long getTime(String date) {
        Date.format = getDateFormat(date);
        assert (format) != null;
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    // allow both datetime, date only, time only = current date,
    // time assume 24HR unless AM/PM specified
    private static void prepareDateFormats() {
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d-M-y"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("d/M/y"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy hha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("dd MMM yy"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("H:m"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("HHmm"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("h:m a"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("h:mma"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("ha"));
        ALLOWED_FORMATS.add(new SimpleDateFormat("hha"));

    }

    private static SimpleDateFormat getDateFormat(String input) {
        for (SimpleDateFormat format : ALLOWED_FORMATS) {
            try {
                ParsePosition position = new ParsePosition(0);
                format.setLenient(false);
                format.parse(input, position).getTime();

                if (position.getIndex() != input.length()) {
                    throw new ParseException(input, 0);
                } else {
                    return format;
                }
            } catch (ParseException e) {
                // check next
            } catch (NullPointerException e) {
                // check next
            }
        }
        return null;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {
        if (input.trim().isEmpty()) {
            return false;
        }

        prepareDateFormats();

        return (getDateFormat(input) != null);
    }

}
