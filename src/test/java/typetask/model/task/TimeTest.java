package typetask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.model.task.Time;


public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid Time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("phone")); // non-numeric
        assertFalse(Time.isValidTime("90p01")); // alphabets within digits
        assertFalse(Time.isValidTime("92 34")); // spaces within digits
        assertFalse(Time.isValidTime("04:11 am")); //hour start with 0
        assertFalse(Time.isValidTime("13:11 am")); //hour exceed number limit
        assertFalse(Time.isValidTime("10:1 am")); //minute not in double digit
        assertFalse(Time.isValidTime("10:60 am")); //minute exceed number limit
        assertFalse(Time.isValidTime("10 : 60 am")); //spaces in between numbers and colon
        assertFalse(Time.isValidTime("10:60")); //no indication of am/pm
        // valid Time

        assertTrue(Time.isValidTime("11:25am"));
        assertTrue(Time.isValidTime("7:30 am")); // with space
        assertTrue(Time.isValidTime("11:01pm")); //minute start with 0
    }
}
