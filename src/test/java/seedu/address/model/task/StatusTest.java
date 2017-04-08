//@@author A0144885R
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class StatusTest {

    @Test
    public void isValidStatus() {
        // blank email
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("@@#")); // random

        assertTrue(Status.isValidStatus("Done")); // Correct
        assertTrue(Status.isValidStatus("done")); // Case-insensitiveness test
        assertTrue(Status.isValidStatus("UnDone")); // Correct
    }

    @Test
    public void testStatusToday() {
        Date today = new Date();
        String todayString = " " + today.getDate() + "-"
                                + (today.getMonth() + 1) + "-"
                                + (today.getYear() + 1900);
        String todayBegining = todayString + " 00:00 am";
        String todayEnding = todayString;

        testStatus(todayString, todayBegining, todayEnding, Status.TODAY);
    }

    @Test
    public void testStatusOverdue() {
        Date date = new Date(70, 1, 1);
        String dateString = " " + date.getDate() + "-"
                                + (date.getMonth() + 1) + "-"
                                + (date.getYear() + 1900);
        String dateBegining = dateString + " 11:59 am";
        String dateEnding = dateString + " 00:01 pm";

        testStatus(dateString, dateBegining, dateEnding, Status.OVERDUE);
    }

    @Test
    public void testStatusTomorrow() {
        GregorianCalendar tmr = new GregorianCalendar();
        tmr.add(Calendar.DATE, 1);

        String dateString = " " + tmr.get(Calendar.DATE) + "-"
                                + (tmr.get(Calendar.MONTH) + 1) + "-"
                                + (tmr.get(Calendar.YEAR));
        String dateBegining = dateString + " 11:59 am";
        String dateEnding = dateString + " 00:01 pm";

        testStatus(dateString, dateBegining, dateEnding, Status.TOMORROW);
    }

    @Test
    public void testStatusTimeUnassigned() {
        try {
            Task task = new Task(new Name("YEs"));
            assertEquals(task.getStatus().toString(), Status.FLOATING);
        } catch (IllegalValueException e) {
            // Impossible
            assert false;
        }
    }

    void testStatus(String dateString, String dateBegining, String dateEnding, String expectedStatus) {
        try {
            // TimePoint
            Task task = new Task(new Name("TestTask"), new Deadline(dateString));
            assertEquals(task.getStatus().toString(), expectedStatus);
            assertEquals(task.getStatus().toString(), expectedStatus);

            // TimePeriod
            task = new Task(new Name("TestTask"), new Deadline("from " + dateBegining + " to " + dateEnding));
            assertEquals(task.getStatus().toString(), expectedStatus);

            // TimeUnassigned
        } catch (IllegalValueException e) {
            // Impossible
            assert false;
        }
    }
}
