package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0140063X
public class DateTest {

    // display format is "M/d/y H:mm a, prettytime format"
    @Test
    public void dateIsCorrectlyCreated() throws IllegalValueException {
        PrettyTime pretty = new PrettyTime();
        PrettyTimeParser prettyParser = new PrettyTimeParser();
        java.util.Date javaDate = new java.util.Date();

        Date testDate = new Date("12/23/99 12:59 pm");
        javaDate = prettyParser.parse("12/23/1999 12:59 PM").get(0);
        assertEquals("12/23/1999 12:59 PM, " + pretty.format(javaDate), testDate.toString());

        testDate = new Date("12-25-36 14:59");
        javaDate = prettyParser.parse("12/25/2036 2:59 PM").get(0);
        assertEquals("12/25/2036 2:59 PM, " + pretty.format(javaDate), testDate.toString());

        testDate = new Date("11/28/1936 23:59");
        javaDate = prettyParser.parse("11/28/1936 11:59 PM").get(0);
        assertEquals("11/28/1936 11:59 PM, " + pretty.format(javaDate), testDate.toString());

        testDate = new Date("1/1/2000 1:23");
        javaDate = prettyParser.parse("1/1/2000 1:23 AM").get(0);
        assertEquals("1/1/2000 1:23 AM, " + pretty.format(javaDate), testDate.toString());

        testDate = new Date("25 Dec 2015 00:00");
        javaDate = prettyParser.parse("25 Dec 2015").get(0);
        assertEquals("12/25/2015 12:00 AM, " + pretty.format(javaDate), testDate.toString());
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

    @Test
    public void dates_Equal_success() throws IllegalValueException {

        Date date1 = new Date("");
        Date date2 = new Date("");
        assertTrue(date1.equals(date2));
    }
    // @@author

    @Test
    public void dates_Equal_Ignores_Time_success() throws IllegalValueException {
        assertTrue(assertDatesEqualIgnoreTime(new Date("Apr 4 2017"), new Date("Apr 4 2017")));
        assertTrue(assertDatesEqualIgnoreTime(new Date("Nov 10 2019"), new Date("Nov 10 2019")));
        assertTrue(assertDatesEqualIgnoreTime(new Date("christmas"), new Date("Dec 25")));
    }

    @Test
    public void dates_Equal_Ignores_Time_failure() throws IllegalValueException {
        assertFalse(assertDatesEqualIgnoreTime(new Date("Apr 4 2018"), new Date("Apr 4 2017")));
        assertFalse(assertDatesEqualIgnoreTime(new Date("Nov 9 2019"), new Date("Nov 10 2019")));
        assertFalse(assertDatesEqualIgnoreTime(new Date("Oct 9 2019"), new Date("Nov 9 2019")));

    }

    private boolean assertDatesEqualIgnoreTime(Date date, Date otherDate) {
        return date.equalsIgnoreTime(otherDate);
    }

    @Test
    public void dates_Equal_Ignores_Minutes_success() throws IllegalValueException {
        assertTrue(assertDatesEqualIgnoreTime(new Date("Apr 4 2017 6am"), new Date("Apr 4 2017 6:30")));
        assertTrue(assertDatesEqualIgnoreTime(new Date("Nov 10 2019 1:25am"), new Date("Nov 10 2019 1am")));
        assertTrue(assertDatesEqualIgnoreTime(new Date("christmas 14:19"), new Date("Dec 25 14")));

    }

    @Test
    public void dates_Equal_Ignores_Minutes_failure() throws IllegalValueException {
        assertFalse(assertDatesEqualIgnoreMinutes(new Date("Apr 4 2017 6pm"), new Date("Apr 4 2017 6am")));
        assertFalse(assertDatesEqualIgnoreMinutes(new Date("Nov 10 2019 1"), new Date("Nov 10 2019 13")));
        assertFalse(assertDatesEqualIgnoreMinutes(new Date("christmas midnight"), new Date("Dec 25 01:00")));

    }

    private boolean assertDatesEqualIgnoreMinutes(Date date, Date otherDate) {
        return date.equalsIgnoreMinutes(otherDate);
    }

    @Test
    public void testEquals_Symmetric() throws IllegalValueException {
        Date x = new Date("today"); // equals and hashCode check name field value
        Date y = new Date("today");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
