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

    public String value;

    public static final String TASKDATE_VALIDATION_REGEX = "^$|(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)[0-9]{2} (2[0-3]|[0-1][0-9])[:][0-5][0-9]";
    public static final String USER_DATE_INPUT_FORMAT = "dd/MM/yyyy";
    public static final String USER_DATE_OUTPUT_FORMAT = "dd/MM/yyyy H:mm";
    public static final String NATTY_DATE_FORMAT = "EEE MMM dd hh:mm:ss zzz yyyy";

    public final SimpleDateFormat userInputDateFormat = new SimpleDateFormat(USER_DATE_INPUT_FORMAT);
    public final SimpleDateFormat userOutputDateFormat = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);
    public final SimpleDateFormat nattyDateFormat = new SimpleDateFormat(NATTY_DATE_FORMAT);

    public TaskDate(String taskDate) {
        try {
            // Format input string to suit Natty dependency
            String formattedInitialTaskDate = changeToNattyDateFormat(taskDate);
            this.value = nattyManipulation(formattedInitialTaskDate);
        } catch (ParseException pe) {
            this.value = nattyManipulation(taskDate);
        }
    }

    /**
     * Uses Natty dependency (natural language date parser) to manipulate date
     * input in String.
     */
    private String nattyManipulation(String taskDate) {

        try {
            // Initialises Natty parser
            Parser parser = new Parser();

            // Parses input String into a list of DateGroups
            List<DateGroup> dateGroupList = parser.parse(taskDate);

            // Retrieves parsed date
            Date parsedDate = dateGroupList.get(0).getDates().get(0);

            // Format parsed date to suit the UI
            String formattedFinalTaskDate = userOutputDateFormat.format(parsedDate);

            return formattedFinalTaskDate;

        } catch (IndexOutOfBoundsException e) {
            return taskDate;
        }

    }

    /**
     * Changes the initial date format of a string input for Natty.
     */
    private String changeToNattyDateFormat(String input) throws ParseException {

        String output = new String();
        Date userDateObject = userInputDateFormat.parse(input);
        output = nattyDateFormat.format(userDateObject);

        return output;
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
