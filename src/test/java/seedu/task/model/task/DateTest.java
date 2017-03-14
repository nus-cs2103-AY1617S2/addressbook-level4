package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.ocpsoft.prettytime.PrettyTime;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateTest {

    //display format is "M/d/y H:mm a, prettytime format"
    @Test
    public void dateIsCorrectlyCreated() throws IllegalValueException {
        PrettyTime pretty = new PrettyTime();
        
        Date testDate = new Date("12/23/99 12:59 pm");
        assertEquals("12/23/1999 12:59 PM, " + pretty.format(new java.util.Date("23/12/1999 12:59 PM")), testDate.toString());

        testDate = new Date("12-25-36 14:59");
        assertEquals("12/25/2036 2:59 PM, " + pretty.format(new java.util.Date("25/12/2036 2:59 PM")), testDate.toString());

        testDate = new Date("12/25/1936 23:59");
        assertEquals("12/25/1936 11:59 PM, " + pretty.format(new java.util.Date("25/12/1936 11:59 PM")), testDate.toString());

        testDate = new Date("1/1/2000 1:23");
        assertEquals("1/1/2000 1:23 AM, " + pretty.format(new java.util.Date("1/1/2000 1:23 AM")), testDate.toString());

        testDate = new Date("25 Dec 2015 00:00");
        assertEquals("12/25/2015 12:00 AM, " + pretty.format(new java.util.Date("25 Dec 2015")), testDate.toString());
    }

    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("25:61")); // wrong time

        // valid dates
        assertTrue(Date.isValidDate("23-12-2017 23:59"));
        assertTrue(Date.isValidDate("23-12-2017 2359"));
        assertTrue(Date.isValidDate("23-12-17 11:59 pM"));
        assertTrue(Date.isValidDate("23-12-17 11Pm"));
        assertTrue(Date.isValidDate("23-12-17"));
        assertTrue(Date.isValidDate("23/12/17 23:59"));
        assertTrue(Date.isValidDate("23/12/17 11:59 Am"));
        assertTrue(Date.isValidDate("23/12/17 9Am"));
        assertTrue(Date.isValidDate("23/12/2017"));
        assertTrue(Date.isValidDate("30 Jun 17 2359"));
        assertTrue(Date.isValidDate("4 July 17 10:09Pm"));
        assertTrue(Date.isValidDate("1 Sep 17 4:09 AM"));
        assertTrue(Date.isValidDate("31 Jul 17"));
        assertTrue(Date.isValidDate("31 January 17"));
        assertTrue(Date.isValidDate("1530"));
        assertTrue(Date.isValidDate("3:30 pm"));
        assertTrue(Date.isValidDate("03:30 pm"));
        assertTrue(Date.isValidDate("3pm"));
    }
}
