package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateTest {

    //display format is "d/M/y H:mm a"
    @Test
    public void dateIsCorrectlyCreated() throws IllegalValueException {
        Date testDate = new Date("23/12/99 12:59 pm");
        assertEquals("23/12/1999 12:59 PM", testDate.toString());

        testDate = new Date("25-12-36 14:59");
        assertEquals("25/12/2036 2:59 PM", testDate.toString());

        testDate = new Date("25/12/1936 23:59");
        assertEquals("25/12/1936 11:59 PM", testDate.toString());

        testDate = new Date("1/1/00 1:23");
        assertEquals("1/1/2000 1:23 AM", testDate.toString());

        testDate = new Date("25 Dec 15");
        assertEquals("25/12/2015 12:00 AM", testDate.toString());

        testDate = new Date("3pm");
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/y");
        assertEquals(dateFormat.format(currentDate) + " 3:00 PM", testDate.toString());
    }

    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("00-00-0000")); // invalid record
        assertFalse(Date.isValidDate("16-16-1993")); // invalid month
        assertFalse(Date.isValidDate("pm09:11")); // wrong order
        assertFalse(Date.isValidDate("31-02-2017 23:59")); // no 31 feb
        assertFalse(Date.isValidDate("23-13-2017 11:59 pM")); //no 13th month
        assertFalse(Date.isValidDate("23-12-17 25:30")); // time wrong
        assertFalse(Date.isValidDate("23/12/99 13:59 pm")); // time wrong
        assertFalse(Date.isValidDate("23/12-2017 11:59 Am")); // mix of both separator
        assertFalse(Date.isValidDate("12/23/2017")); // month and date wrong
        assertFalse(Date.isValidDate("20 Marc"));  // no separator
        assertFalse(Date.isValidDate("23122014"));  // no separator
        assertFalse(Date.isValidDate("12")); // only hour
        assertFalse(Date.isValidDate("1330pM")); // no separator
        assertFalse(Date.isValidDate("25:61")); // wrong time
        assertFalse(Date.isValidDate("13:30 pm")); // wrong time
        assertFalse(Date.isValidDate("13pm")); // wrong time

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
