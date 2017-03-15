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

    @Test
    public void parse() throws IllegalValueException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals(
                cal.getTimeInMillis(),
                DateParser.parse("2000/01/01 0000").getTimeInMillis());
    }

    @Test
    public void toStringTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals("2000/01/01 0000", DateParser.toString(cal));
        cal.set(1990, 05, 22, 23, 54, 0);
        assertEquals("1990/06/22 2354", DateParser.toString(cal));
    }
}
