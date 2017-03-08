package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class DeadlineTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isValidDate() throws IllegalValueException {
        // invalid deadlines
        exception.expect(AssertionError.class);
        new Deadline(null);

        // test equals
        Deadline deadline1 = new Deadline("Tomorrow");
        Deadline deadline2 = new Deadline("Tomorrow");
        Deadline deadline3 = new Deadline("Today"); // a different deadline
        assertTrue("Tomorrow".equals(deadline1.toString()));
        assertTrue(deadline1.equals(deadline2));
        assertTrue(deadline1.equals(deadline1));
        assertFalse(deadline1.equals(null));
        assertFalse(deadline1.equals(deadline3));

        //hashCode
        assertTrue(deadline1.hashCode() == deadline2.hashCode());

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("11-12-2106")); // date
        assertTrue(Deadline.isValidDeadline("Tomorrow"));
        assertTrue(Deadline.isValidDeadline("9pm")); // two characters only
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertTrue(Deadline.isValidDeadline(" ")); // spaces only
    }
}
