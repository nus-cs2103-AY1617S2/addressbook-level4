//@@author A0163744B
package seedu.task.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateParserTest {

    @Test
    public void isValidDateString() {
        // invalid date
        assertFalse(DateParser.isValidDateString(""));
        assertFalse(DateParser.isValidDateString("12/12/1234"));
        assertFalse(DateParser.isValidDateString("12/123412 0000"));
        assertFalse(DateParser.isValidDateString("1234/1212 0000"));
        assertFalse(DateParser.isValidDateString("/01/01200a 0000"));
        assertFalse(DateParser.isValidDateString("/ba/012000 0000"));
        assertFalse(DateParser.isValidDateString("0000"));
        assertFalse(DateParser.isValidDateString("01/23/2000 0000"));
        assertFalse(DateParser.isValidDateString("42/12/2000 0000"));
        assertFalse(DateParser.isValidDateString("01/12/2000 3000"));
        assertFalse(DateParser.isValidDateString("01/12/2000 0160"));
        assertFalse(DateParser.isValidDateString("01/12/2000 20000"));
        assertFalse(DateParser.isValidDateString("010/12/2000 2000"));
        assertFalse(DateParser.isValidDateString("10/123/2000 2000"));
        assertFalse(DateParser.isValidDateString("10/123/20010 2000"));

        assertFalse(DateParser.isValidDateString("a 2000"));
        assertFalse(DateParser.isValidDateString("aaaaaaaaaa 2000"));

        // valid date
        assertTrue(DateParser.isValidDateString("01/01/2000 0000"));
        assertTrue(DateParser.isValidDateString("01/01/2000 1200"));
        assertTrue(DateParser.isValidDateString("01/01/0000 1200"));
        assertTrue(DateParser.isValidDateString("10/03/2017 0317"));
        assertTrue(DateParser.isValidDateString("Mon 0317"));
        assertTrue(DateParser.isValidDateString("Monday 0317"));
        assertTrue(DateParser.isValidDateString("Wednesday 0000"));
        assertTrue(DateParser.isValidDateString("foo 0317"));
    }

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DateParser.isValidDate(-1, 0, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, -1, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 12, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 0, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 32, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 5, 31, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, -1, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 24, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 0, -1));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 0, 60));

        // valid date
        assertTrue(DateParser.isValidDate(0, 0, 1, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 0, 1, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 11, 31, 23, 59));
        assertTrue(DateParser.isValidDate(2017, 0, 31, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 2, 31, 0, 0));
        assertTrue(DateParser.isValidDate(2000, 1, 29, 0, 0));
        assertTrue(DateParser.isValidDate(2008, 8, 8, 8, 0));
        assertTrue(DateParser.isValidDate(2017, 0, 1, 15, 30));
    }

    @Test
    public void parse() throws IllegalValueException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals(
                cal.getTimeInMillis(),
                DateParser.parse("01/01/2000 0000").getTimeInMillis());
    }

    @Test
    public void toStringTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals("01/01/2000 0000", DateParser.toString(cal));
        cal.set(1990, 05, 22, 23, 54, 0);
        assertEquals("22/06/1990 2354", DateParser.toString(cal));
    }
}
