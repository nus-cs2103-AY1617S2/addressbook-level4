package seedu.ezdo.model.todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Represents the date associated with tasks.
 */
public abstract class TaskDate {

    public final String value;

    public final SimpleDateFormat userDateFormat = new SimpleDateFormat(USER_DATE_FORMAT);
    public final SimpleDateFormat nattyDateFormat = new SimpleDateFormat(NATTY_DATE_FORMAT);

    public static final String TASKDATE_VALIDATION_REGEX = "^$|([12][0-9]|3[01]|0?[1-9])/(0?[1-9]|1[012])/((?:19|20)\\d\\d)";
    public static final String USER_DATE_FORMAT = "dd/MM/yyyy";
    public static final String NATTY_DATE_FORMAT = "EEE MMM dd hh:mm:ss zzz yyyy";

    public TaskDate(String taskDate) {

	if (isValidTaskDate(taskDate)) {
	    // Format input string to suit Natty dependency
	    String formattedInitialTaskDate = changeToNattyDateFormat(taskDate);

	    // Use Natty dependency to manipulate date input
	    Date parsedTaskDate = parseNatty(formattedInitialTaskDate);

	    // Format parsed date to suit the regex
	    String formattedFinalTaskDate = changeToUserDateFormat(parsedTaskDate);

	    this.value = formattedFinalTaskDate;

	} else {
	    this.value = new String();
	}

    }

    /**
     * Uses Natty dependency (natural language date parser) to manipulate date
     * input in String.
     * 
     * @param date
     * @return Manipulated date input
     */
    public Date parseNatty(String date) {

	// Initialises Natty parser
	Parser parser = new Parser();

	// Parses input String into a list of DateGroups
	List<DateGroup> dateGroupList = parser.parse(date);

	// Retrieves parsed date
	Date parsedDate = dateGroupList.get(0).getDates().get(0);

	return parsedDate;
    }

    /**
     * Changes the initial date format of a string input for Natty.
     * 
     * @param input
     * @return Formatted date in string format for Natty
     */
    public String changeToNattyDateFormat(String input) {

	String output = new String();

	try {
	    Date userDateObject = userDateFormat.parse(input);
	    output = nattyDateFormat.format(userDateObject);
	} catch (ParseException pe) {
	    System.out.println("User input date format to Natty date format: Parse Exception error!");
	}

	return output;
    }

    /**
     * Changes the final date format of a Date input for user.
     * 
     * @param input
     * @return Formatted date in string format for user
     */
    public String changeToUserDateFormat(Date input) {
	return userDateFormat.format(input);
    }

    @Override
    public String toString() {
	return value;
    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof TaskDate // instanceof handles nulls
			&& this.value.equals(((TaskDate) other).value)); // state
									 // check
    }

    @Override
    public int hashCode() {
	return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidTaskDate(String test) {
	return test.matches(TASKDATE_VALIDATION_REGEX);
    }
}
