package seedu.taskboss.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.taskboss.model.task.Recurrence.Frequency;

//@@author A0143157J
public class RecurrenceTest {

    //---------------- Tests for isValidRecurrence --------------------------------------
    /*
     * Valid equivalence partitions:
     * - daily/weekly/monthly/yearly/none (case-insensitive)
     * - whitespace is treated as none
     *
     * Invalid equivalence paritions: not a recurrence, multiple recurrences
     */
    @Test
    public void isValidRecurrence() {
        // EP: invalid recurrence
        assertFalse(Recurrence.isValidRecurrence("every two years")); // invalid recurrence
        assertFalse(Recurrence.isValidRecurrence("biweekly")); // invalid recurrence
        assertFalse(Recurrence.isValidRecurrence("random recurrence")); // invalid recurrence
        assertFalse(Recurrence.isValidRecurrence("weekly monthly")); // multiple recurrences

        // EP: valid recurrence
        assertTrue(Recurrence.isValidRecurrence("")); // empty string
        assertTrue(Recurrence.isValidRecurrence("DAilY")); // mixed case
        assertTrue(Recurrence.isValidRecurrence("weekly")); // accepted input
        assertTrue(Recurrence.isValidRecurrence("   yearly    ")); // with trailing whitespace
    }

    //---------------- Tests for different types of recurrence -------------------------------
    // Test for DAILY recurrence
    @Test
    public void dailyRecurrence_successful() throws IllegalArgumentException, IllegalValueException {
        Task sampleTask = initSampleTask();
        sampleTask.setRecurrence(new Recurrence(Frequency.DAILY));

        SimpleDateFormat startSdf = initSimpleDateFormat(sampleTask.getStartDateTime());
        SimpleDateFormat endSdf = initSimpleDateFormat(sampleTask.getEndDateTime());
        DateTime originalStartDate = sampleTask.getStartDateTime();
        DateTime originalEndDate = sampleTask.getEndDateTime();

        sampleTask.getRecurrence().updateTaskDates(sampleTask);

        DateTime updatedStartDate = sampleTask.getStartDateTime();
        DateTime updatedEndDate = sampleTask.getEndDateTime();

        assertEquals(sampleTask.getRecurrence().toString(), Frequency.DAILY.toString());
        assertEquals(addToDate(originalStartDate.getDate(), Frequency.DAILY, startSdf),
                updatedStartDate);
        assertEquals(addToDate(originalEndDate.getDate(), Frequency.DAILY, endSdf),
                updatedEndDate);
    }

    // Test for WEEKLY recurrence
    @Test
    public void weeklyRecurrence_successful() throws IllegalArgumentException, IllegalValueException {
        Task sampleTask = initSampleTask();
        sampleTask.setRecurrence(new Recurrence(Frequency.WEEKLY));

        SimpleDateFormat startSdf = initSimpleDateFormat(sampleTask.getStartDateTime());
        SimpleDateFormat endSdf = initSimpleDateFormat(sampleTask.getEndDateTime());
        DateTime originalStartDate = sampleTask.getStartDateTime();
        DateTime originalEndDate = sampleTask.getEndDateTime();

        sampleTask.getRecurrence().updateTaskDates(sampleTask);

        DateTime updatedStartDate = sampleTask.getStartDateTime();
        DateTime updatedEndDate = sampleTask.getEndDateTime();

        assertEquals(sampleTask.getRecurrence().toString(), Frequency.WEEKLY.toString());
        assertEquals(addToDate(originalStartDate.getDate(), Frequency.WEEKLY, startSdf),
                updatedStartDate);
        assertEquals(addToDate(originalEndDate.getDate(), Frequency.WEEKLY, endSdf),
                updatedEndDate);
    }

    // Test for MONTHLY recurrence
    @Test
    public void monthlyRecurrence_successful() throws IllegalArgumentException, IllegalValueException {
        Task sampleTask = initSampleTask();
        sampleTask.setRecurrence(new Recurrence(Frequency.MONTHLY));

        SimpleDateFormat startSdf = initSimpleDateFormat(sampleTask.getStartDateTime());
        SimpleDateFormat endSdf = initSimpleDateFormat(sampleTask.getEndDateTime());
        DateTime originalStartDate = sampleTask.getStartDateTime();
        DateTime originalEndDate = sampleTask.getEndDateTime();

        sampleTask.getRecurrence().updateTaskDates(sampleTask);

        DateTime updatedStartDate = sampleTask.getStartDateTime();
        DateTime updatedEndDate = sampleTask.getEndDateTime();

        assertEquals(sampleTask.getRecurrence().toString(), Frequency.MONTHLY.toString());
        assertEquals(addToDate(originalStartDate.getDate(), Frequency.MONTHLY, startSdf),
                updatedStartDate);
        assertEquals(addToDate(originalEndDate.getDate(), Frequency.MONTHLY, endSdf),
                updatedEndDate);
    }

    // Test for YEARLY recurrence
    @Test
    public void yearlyRecurrence_successful() throws IllegalArgumentException, IllegalValueException {
        Task sampleTask = initSampleTask();
        sampleTask.setRecurrence(new Recurrence(Frequency.YEARLY));

        SimpleDateFormat startSdf = initSimpleDateFormat(sampleTask.getStartDateTime());
        SimpleDateFormat endSdf = initSimpleDateFormat(sampleTask.getEndDateTime());
        DateTime originalStartDate = sampleTask.getStartDateTime();
        DateTime originalEndDate = sampleTask.getEndDateTime();

        sampleTask.getRecurrence().updateTaskDates(sampleTask);

        DateTime updatedStartDate = sampleTask.getStartDateTime();
        DateTime updatedEndDate = sampleTask.getEndDateTime();

        assertEquals(sampleTask.getRecurrence().toString(), Frequency.YEARLY.toString());
        assertEquals(addToDate(originalStartDate.getDate(), Frequency.YEARLY, startSdf),
                updatedStartDate);
        assertEquals(addToDate(originalEndDate.getDate(), Frequency.YEARLY, endSdf),
                updatedEndDate);
    }

    // Test for NONE recurrence
    @Test
    public void noneRecurrence_successful() throws IllegalArgumentException, IllegalValueException {
        Task sampleTask = initSampleTask();

        DateTime originalStartDate = sampleTask.getStartDateTime();
        DateTime originalEndDate = sampleTask.getEndDateTime();

        sampleTask.getRecurrence().updateTaskDates(sampleTask);

        DateTime updatedStartDate = sampleTask.getStartDateTime();
        DateTime updatedEndDate = sampleTask.getEndDateTime();

        assertEquals(sampleTask.getRecurrence().toString(), Frequency.NONE.toString());
        assertEquals(originalStartDate, updatedStartDate);
        assertEquals(originalEndDate, updatedEndDate);
    }

    //---------------- Helper functions -------------------------------
    private Task initSampleTask() throws DuplicateCategoryException,
        IllegalArgumentException, IllegalValueException {
        return new Task(new Name("Attending wedding"), new PriorityLevel("Yes"),
                new DateTime("Feb 18, 2017"),
                new DateTime("Mar 28, 2017 5pm"),
                new Information("123, Jurong West Ave 6, #08-111"),
                new Recurrence(Frequency.NONE),
                new UniqueCategoryList("Friends"));
    }

    private DateTime addToDate(Date date, Frequency frequency,
            SimpleDateFormat desiredFormat) throws IllegalValueException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (frequency == Frequency.DAILY) {
            calendar.add(Calendar.DATE, 1);
        } else if (frequency == Frequency.WEEKLY) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        } else if (frequency == Frequency.MONTHLY) {
            calendar.add(Calendar.MONTH, 1);
        } else if (frequency == Frequency.YEARLY) {
            calendar.add(Calendar.YEAR, 1);
        }
        String dateInString = desiredFormat.format(calendar.getTime());
        return new DateTime(dateInString);
    }

    private SimpleDateFormat initSimpleDateFormat(DateTime dateTime) {
        if (dateTime.isTimeInferred()) {
            return new SimpleDateFormat("MMM dd, yyyy");
        } else {
            return new SimpleDateFormat("MMM dd, yyyy h:mm aa");
        }
    }
}
