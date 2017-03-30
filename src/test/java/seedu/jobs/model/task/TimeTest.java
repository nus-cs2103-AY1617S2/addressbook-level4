package seedu.jobs.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import seedu.jobs.commons.exceptions.IllegalValueException;

public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid Time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("12/12/2016")); // incomplete time declaration
        assertFalse(Time.isValidTime("12/12/2016 12")); // incomplete time declaration
        assertFalse(Time.isValidTime("12/12/2016 12:")); // incomplete time declaration
        assertFalse(Time.isValidTime("9/12/1993 5:2")); //no preceding zero in the minute portion
        assertFalse(Time.isValidTime("9/12/1993 05:00")); //no preceding zero in the day
        assertFalse(Time.isValidTime("09/9/1993 05:00")); //no preceding zero in the month
        assertFalse(Time.isValidTime("31/11/1993 05:00")); //non existent date
        assertFalse(Time.isValidTime("30/11/1993 25:00")); //non existent time

        // valid Time
        assertTrue(Time.isValidTime("09/12/1993 15:00")); //preceding zero in the date

    }

    @Test
    public void timeEquality() throws IllegalValueException {
        //testing for time addition
        Time oldTime = new Time(Optional.of("09/12/1993 12:00"));
        assertEquals(oldTime, new Time(Optional.of("09/12/1993 12:00")));
        assertNotEquals(oldTime, new Time(Optional.of("09/12/1993 16:00")));
        oldTime.addDays(7);
        assertNotEquals(oldTime, new Time(Optional.of("09/12/1993 12:00")));
        assertEquals(oldTime, new Time(Optional.of("16/12/1993 12:00")));
    }
}
