package seedu.whatsleft.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    //@@author  A0121668A
    public static final String TIME_FORMAT_CONSTRAINTS = "Time arguments can only be in this format: "
                            + "HHMM format, e.g. 1200";
    public static final String DATE_FORMAT_CONSTRAINTS = "Date arguments can take only 6 digits, "
                            + "and it should be in DDMMYY format (Day-Month-Year), e.g. 060417";
    public static final int YEAR_CONVERSION_INDEX = 2000;

    public static final String DATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";
    //@@author A0124377A
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.
            MEDIUM, FormatStyle.SHORT);

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

    //@@author A0121668A
    /**
     * Parse a String argument into date format.
     * @param dateArg
     * @return time in localTime format
     * @throws DateTimeException
     */
    public static LocalTime parseStringToTime(String timeString) throws DateTimeException {
        //empty start date
        if (timeString == null) throw new DateTimeException(TIME_FORMAT_CONSTRAINTS);
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HHmm"));
    }

    //@@author A0121668A
    /**
     * Parse a String argument into date format.
     * @param dateString
     * @return time in LocalDate format
     * @throws DateTimeException
     */

    public static LocalDate parseStringToDate(String dateString) throws DateTimeException {
        //empty start date
        if (dateString == null) throw new DateTimeException(DATE_FORMAT_CONSTRAINTS);
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyy"));

    }

    //@@author
    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }

    //@@author A0124377A
    /**
     * Returns true if s represents an signed integer e.g. -1, 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isSignedInteger(String s) {
        return s != null && s.matches("^-?\0*[1-9]\\d*$");
    }

    //@@author A0110491U
    /**
     * returns today's date in DDMMYY format in string
     */
    public static String getTodayDateInString() {
        String todaydate = new SimpleDateFormat("ddMMyy").format(new Date());
        return todaydate;
    }
    //@@author

}
