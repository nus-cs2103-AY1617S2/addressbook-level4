//@@author A0144885R
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

public class DateUtilTest {

    @Test
    public void isDeadlineMatch_DifferentTypes_False() {
        try {
            Deadline floating = new Deadline();
            // Task and event is still comparable
            Deadline task = new Deadline("today");
            Deadline event = new Deadline("from today to tomorrow");

            assertFalse(DateUtil.isDeadlineMatch(floating, task));
            assertFalse(DateUtil.isDeadlineMatch(task, floating));  // Test if params order matters
            assertFalse(DateUtil.isDeadlineMatch(event, floating));
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }

    @Test
    public void isDeadlineMatch_Normal_True() {
        try {
            // Floating
            Deadline floating1 = new Deadline();
            Deadline floating2 = new Deadline();
            assertTrue(DateUtil.isDeadlineMatch(floating1, floating2));

            // Task
            Deadline task1 = new Deadline("the day after today");
            Deadline task2 = new Deadline("tomorrow");
            assertTrue(DateUtil.isDeadlineMatch(task1, task2));

            // Event
            Deadline event1 = new Deadline("from today to tomorrow");
            Deadline event2 = new Deadline("from today to next monday");
            assertTrue(DateUtil.isDeadlineMatch(event1, event2));

            // Task & event
            assertTrue(DateUtil.isDeadlineMatch(event1, task1));
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }

    @Test
    public void isDeadlineMatch_Normal_False() {
        try {
            // Floating cannot be different

            // Task
            Deadline task1 = new Deadline("the day after today");
            Deadline task2 = new Deadline("the day before today");
            assertFalse(DateUtil.isDeadlineMatch(task1, task2));

            // Event
            Deadline event1 = new Deadline("from today to tomorrow");
            Deadline event2 = new Deadline("from 10-3-1997 to 20-3-2007");
            assertFalse(DateUtil.isDeadlineMatch(event1, event2));
        } catch (IllegalValueException e) {
            assertTrue(false);
        }
    }
}
