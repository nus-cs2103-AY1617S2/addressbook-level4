package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Date;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(Date.isValidDate("peter*")); // contains non-alphanumeric characters
        assertFalse(Date.isValidDate("testing")); // meaningless alphabets
        
        // valid date
        assertTrue(Date.isValidDate("tomorrow")); // relative dates(relative to current date)
        assertTrue(Date.isValidDate("3 weeks ago")); // relative dates(relative to current date)
        assertTrue(Date.isValidDate("Apr 3")); // informal date
        assertTrue(Date.isValidDate("2017/05/07")); // formal date
        assertTrue(Date.isValidDate("tmr")); // informal date
        assertTrue(Date.isValidDate("12:59")); // only time
        assertTrue(Date.isValidDate("123456")); // time alternatives(will result in today 12:34:56)
        assertTrue(Date.isValidDate("in 5 minutes")); // relative time
        assertTrue(Date.isValidDate("Apr 3 12:00")); // date and time
    }
}
