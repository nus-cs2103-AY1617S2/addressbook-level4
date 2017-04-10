//@@author A0138907W
package seedu.ezdo.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ezdo.logic.parser.DateParser.USER_DATE_OUTPUT_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Tests the behaviour of the date parser
 */
public class DateParserTest {

    private DateParser dateParser;

    /**
     * Checks if dates in natural language are parsed correctly
     * @throws Exception
     */
    @Test
    public void checkNaturalParsing() throws Exception {
        DateFormat expectedFormat = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(Calendar.HOUR_OF_DAY, 14);
        c.set(Calendar.MINUTE, 0);

        dateParser = new DateParser("today 2pm");
        dt = c.getTime();
        String expectedValue = expectedFormat.format(dt);
        assertTrue(expectedValue.equals(dateParser.value));

        dateParser = new DateParser("tomorrow 2pm");
        c.add(Calendar.DATE, 1); // add 1 day to get tomorrow's date
        dt = c.getTime();
        expectedValue = expectedFormat.format(dt);
        assertTrue(expectedValue.equals(dateParser.value));
    }

    /**
     * Checks if dates formatted in DD/MM/YYYY are parsed correctly
     */
    @Test
    public void checkFormatParsing() throws Exception {
        assertDateParseSuccess("31/12/2017 11:00", "31/12/2017 11:00");
        assertDateParseSuccess("1/1/2016 12:00", "01/01/2016 12:00");
        assertDateParseSuccess("15/01/2016 00:00", "15/01/2016 00:00");
    }

    private void assertDateParseSuccess(String input, String expectedOutput) {
        dateParser = new DateParser(input);
        assertEquals(dateParser.value, expectedOutput);
    }

}
