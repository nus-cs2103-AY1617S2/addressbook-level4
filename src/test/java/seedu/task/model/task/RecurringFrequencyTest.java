//@@author A0164212U
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RecurringFrequencyTest {

    @Test
    public void isValidFrequency() {
        // invalid frequency
        assertFalse(RecurringFrequency.isValidFrequency("")); // empty string
        assertFalse(RecurringFrequency.isValidFrequency(" ")); // spaces only
        assertFalse(RecurringFrequency.isValidFrequency("2malpha")); // valid priority followed by alpha characters
        assertFalse(RecurringFrequency.isValidFrequency("2h alpha"));
        // valid priority followed by alpha char separated by space
        assertFalse(RecurringFrequency.isValidFrequency(".1h")); // decimal hour
        assertFalse(RecurringFrequency.isValidFrequency(".1d")); // decimal day
        assertFalse(RecurringFrequency.isValidFrequency(".1m")); // decimal month

        // valid frequency
        assertTrue(RecurringFrequency.isValidFrequency("1h")); // one digit hour
        assertTrue(RecurringFrequency.isValidFrequency("10h")); // two digit hour
        assertTrue(RecurringFrequency.isValidFrequency("1d")); // one digit day
        assertTrue(RecurringFrequency.isValidFrequency("10d")); // two digit day
        assertTrue(RecurringFrequency.isValidFrequency("1m")); // one digit month
        assertTrue(RecurringFrequency.isValidFrequency("10m")); // two digit month
    }

}
