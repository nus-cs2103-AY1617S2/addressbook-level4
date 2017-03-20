package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
    private static final int DEADLINE_INDEX = 0;
    public static final String TIME_CONSTRAINTS = "Task time should be in the form of DD/MM/YYYY HH:MM, e.g 20/03/2017 4:18 \n"
            + "Or name of the day, e.g Wed 4:18 \n"
            + "Or relative days, e.g tomorrow 4:18 \n"
            + "Notice that no abbreviation is accepted for relatives. e.g tmrw is invalid.";
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        assert word != null : "Word parameter cannot be null";
        assert sentence != null : "Sentence parameter cannot be null";

        String preppedWord = word.trim();
        assert !preppedWord.isEmpty() : "Word parameter cannot be empty";
        assert preppedWord.split("\\s+").length == 1 : "Word parameter should be a single word";

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) return true;
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
    //@@author A0122017Y
    public static LocalDateTime parseStringToTime(String timeArg) throws IllegalValueException {
        //empty start date
        if (timeArg == null) {
            throw new IllegalValueException(TIME_CONSTRAINTS);
        }
        
        PrettyTimeParser timeParser = new PrettyTimeParser();
        List<Date> parsedResult = timeParser.parse(timeArg);
        
        //cannot parse
        if (parsedResult.isEmpty()) {
            throw new IllegalValueException(TIME_CONSTRAINTS);      
        }
        
        //wrap in LocalDateTime class
        return LocalDateTime.ofInstant(parsedResult.get(DEADLINE_INDEX).toInstant(), ZoneId.systemDefault());
    }
}
