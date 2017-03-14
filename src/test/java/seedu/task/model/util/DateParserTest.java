package seedu.task.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
        assertTrue(DateParser.isValidDateString("2000/01/01 1200"));
        assertTrue(DateParser.isValidDateString("0000/01/01 1200"));
        assertTrue(DateParser.isValidDateString("2017/03/10 0317"));
    }
}
