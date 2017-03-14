package seedu.task.model.util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateParserTest {

    @Test
    public void isValidDateString() {
        // invalid date
        assertFalse(DateParser.isValidDateString(""));
        assertFalse(DateParser.isValidDateString("1234/12/12"));
        assertFalse(DateParser.isValidDateString("123412/12 0000"));
        assertFalse(DateParser.isValidDateString("1234/1212 0000"));
        assertFalse(DateParser.isValidDateString("200a/01/01 0000"));
        assertFalse(DateParser.isValidDateString("2000/ba/01 0000"));
        assertFalse(DateParser.isValidDateString("0000"));
        assertFalse(DateParser.isValidDateString("2000/23/01 0000"));
        assertFalse(DateParser.isValidDateString("2000/12/42 0000"));
        assertFalse(DateParser.isValidDateString("2000/12/01 3000"));
        assertFalse(DateParser.isValidDateString("2000/12/01 0160"));
        assertFalse(DateParser.isValidDateString("2000/12/01 20000"));
        assertFalse(DateParser.isValidDateString("2000/12/010 2000"));
        assertFalse(DateParser.isValidDateString("2000/123/10 2000"));
        assertFalse(DateParser.isValidDateString("20010/123/10 2000"));

        // valid date
        assertTrue(DateParser.isValidDateString("2000/01/01 0000"));
        assertTrue(DateParser.isValidDateString("2000/01/01 1200"));
        assertTrue(DateParser.isValidDateString("0000/01/01 1200"));
        assertTrue(DateParser.isValidDateString("2017/03/10 0317"));
    }

    /**
     * Utility function to remove the milliseconds from a time
     * @param time the time to remove the milliseconds from
     * @return the time with the milliseconds set to 0
     */
    private long dropMillis(long time) {
        return time / 1000 * 1000;
    }

    @Test
    public void parse() throws IllegalValueException {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals(dropMillis(cal.getTimeInMillis()), dropMillis(DateParser.parse("2000/01/01 0000").getTimeInMillis()));
    }
}
