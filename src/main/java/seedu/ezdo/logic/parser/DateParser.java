package seedu.ezdo.logic.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

    public String value;

    public static final String USER_DATE_INPUT_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String USER_DATE_OUTPUT_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String NATTY_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public final SimpleDateFormat userInputDateFormat = new SimpleDateFormat(USER_DATE_INPUT_FORMAT);
    public final SimpleDateFormat userOutputDateFormat = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);
    public final SimpleDateFormat nattyDateFormat = new SimpleDateFormat(NATTY_DATE_FORMAT);

    public DateParser(String input) {
        try {
            // Format input string to suit Natty dependency
            String formattedInitialTaskDate = changeToNattyDateFormat(input);
            this.value = nattyManipulation(formattedInitialTaskDate);
        } catch (ParseException pe) {
            this.value = nattyManipulation(input);
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

}
