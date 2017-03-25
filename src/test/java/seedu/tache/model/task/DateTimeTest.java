//@@author A0139925U
package seedu.tache.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.tache.commons.exceptions.IllegalValueException;

public class DateTimeTest {

    @Test(expected = IllegalValueException.class)
    public void testInvalidDateTime() throws IllegalValueException {
        DateTime test = new DateTime("happy");
    }

    @Test
    public void testCorrectDateParsing() throws IllegalValueException {
        DateTime test = new DateTime("02/07/19");
        assertEquals(test.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test.getDateOnly(), "07/02/2019");
        assertEquals(test.getAmericanDateTime(), "02/07/2019 00:00:00");
        assertEquals(test.getTimeOnly(), "00:00:00");
        assertEquals(test.getDateTimeForFullCalendar(), "2019-02-07T00:00:00");
    }

    @Test
    public void testCorrectTimeParsing() throws IllegalValueException {
        DateTime test = new DateTime("3pm");
        assertEquals(test.getTimeOnly(), "15:00:00");
    }

    @Test
    public void testCorrectDateTimeParsing() throws IllegalValueException {
        DateTime test = new DateTime("02/07/19 3pm");
        assertEquals(test.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test.getDateOnly(), "07/02/2019");
        assertEquals(test.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test.getTimeOnly(), "15:00:00");
        assertEquals(test.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");

        DateTime test2 = new DateTime("3pm 02/07/19");
        assertEquals(test2.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test2.getDateOnly(), "07/02/2019");
        assertEquals(test2.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test2.getTimeOnly(), "15:00:00");
        assertEquals(test2.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");

        DateTime test3 = new DateTime("3pm 02-07-19");
        assertEquals(test3.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test3.getDateOnly(), "07/02/2019");
        assertEquals(test3.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test3.getTimeOnly(), "15:00:00");
        assertEquals(test3.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");
    }
}
