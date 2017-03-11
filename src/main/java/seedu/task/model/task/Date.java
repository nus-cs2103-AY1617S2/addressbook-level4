package seedu.task.model.task;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in KIT. Guarantees: immutable; is valid as declared
 * in {@link #isValidDate(String)}
 */
public class Date {

	// add to user guide
	public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, use help to see allowed formats or try this format: DD-MM-YY hh:mm AM/PM";
	public static final ArrayList<SimpleDateFormat> allowedFormats = new ArrayList<>();
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
		String trimmedDate = date.trim();

		if (!isValidDate(trimmedDate)) {
			throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
		}

		if (!trimmedDate.contains("-") && !trimmedDate.contains("/") && !trimmedDate.matches("[0-9]* [a-zA-Z]{3,}.*")) {
			java.util.Date currentDate = new java.util.Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/y");
			trimmedDate = dateFormat.format(currentDate) + " " + trimmedDate;
		}

		this.value = new java.util.Date(getTime(trimmedDate));
	}

	// date must be valid
	private long getTime(String date) {
		this.format = getDateFormat(date);
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
		allowedFormats.add(new SimpleDateFormat("d-M-y H:m"));
		allowedFormats.add(new SimpleDateFormat("d-M-y HHmm"));
		allowedFormats.add(new SimpleDateFormat("d-M-y h:m a"));
		allowedFormats.add(new SimpleDateFormat("d-M-y h:mma"));
		allowedFormats.add(new SimpleDateFormat("d-M-y ha"));
		allowedFormats.add(new SimpleDateFormat("d-M-y hha"));
		allowedFormats.add(new SimpleDateFormat("d-M-y"));
		allowedFormats.add(new SimpleDateFormat("d/M/y H:m"));
		allowedFormats.add(new SimpleDateFormat("d/M/y HHmm"));
		allowedFormats.add(new SimpleDateFormat("d/M/y h:m a"));
		allowedFormats.add(new SimpleDateFormat("d/M/y h:mma"));
		allowedFormats.add(new SimpleDateFormat("d/M/y ha"));
		allowedFormats.add(new SimpleDateFormat("d/M/y hha"));
		allowedFormats.add(new SimpleDateFormat("d/M/y"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy H:m"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy HHmm"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy h:m a"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy h:mma"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy ha"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy hha"));
        allowedFormats.add(new SimpleDateFormat("dd MMM yy"));
		allowedFormats.add(new SimpleDateFormat("H:m"));
		allowedFormats.add(new SimpleDateFormat("HHmm"));
		allowedFormats.add(new SimpleDateFormat("h:m a"));
		allowedFormats.add(new SimpleDateFormat("h:mma"));
		allowedFormats.add(new SimpleDateFormat("ha"));
		allowedFormats.add(new SimpleDateFormat("hha"));

	}

	private static SimpleDateFormat getDateFormat(String input) {
		for (SimpleDateFormat format : allowedFormats) {
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
		
		if (getDateFormat(input) != null) {
			return true;
		} else {
			return false;
		}		
	}

	@Override
	public String toString() {
		SimpleDateFormat displayFormat = new SimpleDateFormat("d/M/y h:mm a");
		return displayFormat.format(value);
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Date // instanceof handles nulls
						&& this.value.equals(((Date) other).value)); // state
																		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
