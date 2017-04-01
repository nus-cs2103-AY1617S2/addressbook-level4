//@@author A0144885R
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.date.DateOnly;
import seedu.address.model.task.date.DateTime;
import seedu.address.model.task.date.TimePeriod;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // Invalid Deadline
        assertFalse(Deadline.isValidDeadline("")); // Empty string
        assertFalse(Deadline.isValidDeadline(" ")); // Spaces only
        assertFalse(Deadline.isValidDeadline("^")); // Random string
        assertFalse(Deadline.isValidDeadline("from 10:00 am to Thu, March 9 2017, 10:00 pm")); // Full format in word

        // Valid deadline
        assertTrue(Deadline.isValidDeadline("28/02/1996")); // Seperated by slash
        assertTrue(Deadline.isValidDeadline("28/02/19")); // Seperated by slash
        assertTrue(Deadline.isValidDeadline("28-02-1996")); // Seperated by dash
        assertTrue(Deadline.isValidDeadline("28-02-96")); // Seperated by dash
        assertTrue(Deadline.isValidDeadline("Thu, March 9 2017")); // Word format with short day
        assertTrue(Deadline.isValidDeadline("Thursday, March 9 2017")); // Word format
        assertTrue(Deadline.isValidDeadline("Thu, March 9 2017 23:59")); // Full format in word
        assertTrue(Deadline.isValidDeadline("Thu, March 9 2017, 10:00 pm")); // Full format in word
        assertTrue(Deadline.isValidDeadline("from Thu, March 9 2017, 10:00 pm to 10:00 am")); // Full format in word
    }

    @Test
    public void testTimeOnly() {
        try {
            Deadline date = new Deadline("0:23 am");
            Date expectedDate = new Date();
            expectedDate.setHours(0);
            expectedDate.setMinutes(23);
            String expectedOutput = new SimpleDateFormat(DateTime.READABLE_DATETIME_OUTPUT_FORMAT)
                                            .format(expectedDate);

            assertEquals(date.toString(), expectedOutput);
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDateOnly() {
        try {
            Deadline date = new Deadline("09/03/2017");
            Date expectedDate = new Date();
            expectedDate.setDate(9);
            expectedDate.setMonth(2);
            expectedDate.setYear(117);
            String expectedOutput = new SimpleDateFormat(DateOnly.READABLE_DATEONLY_OUTPUT_FORMAT)
                                            .format(expectedDate);

            assertEquals(date.toString(), expectedOutput);
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDateTime() {
        try {
            Deadline date = new Deadline("Thursday, March 9 2017, 10:20 am");
            Date expectedDate = new Date();
            expectedDate.setDate(9);
            expectedDate.setMonth(2);
            expectedDate.setYear(117);
            expectedDate.setHours(10);
            expectedDate.setMinutes(20);
            String expectedOutput = new SimpleDateFormat(DateTime.READABLE_DATETIME_OUTPUT_FORMAT)
                                            .format(expectedDate);

            assertEquals(date.toString(), expectedOutput);
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testTimePeriod() {
        try {
            Deadline date = new Deadline("from Thursday, March 9 2017 to 12:23 pm");
            Date beginDate = new Date();
            beginDate.setDate(9);
            beginDate.setMonth(2);
            beginDate.setYear(117);
            Date endDate = new Date();
            endDate.setHours(12);
            endDate.setMinutes(23);
            String expectedBeginDate = new SimpleDateFormat(DateOnly.READABLE_DATEONLY_OUTPUT_FORMAT)
                                            .format(beginDate);
            String expectedEndDate = new SimpleDateFormat(DateTime.READABLE_DATETIME_OUTPUT_FORMAT)
                                            .format(endDate);

            assertEquals(date.toString(), String.format(TimePeriod.OUTPUT_FORMAT,
                                                        expectedBeginDate,
                                                        expectedEndDate));
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }
}
